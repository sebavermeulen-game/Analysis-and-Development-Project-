package be.howest.adria.infrastructure.persistence.repositories;

import be.howest.adria.application.contracts.repositories.LocationsRepository;
import be.howest.adria.domain.LocationWithOrderData;
import be.howest.adria.infrastructure.persistence.repositories.mappers.LocationWithOrderDataMapper;
import be.howest.adria.infrastructure.persistence.shared.utils.DoubleEndedQueue;
import be.howest.adria.infrastructure.persistence.shared.utils.JdbcConnection;
import be.howest.adria.infrastructure.shared.utils.LogLauncher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqliteGetAllLocationsRepository implements LocationsRepository {
    private static final Logger LOGGER = Logger.getLogger(SqliteGetAllLocationsRepository.class.getName());
    private static LocationsRepository instance;
    private final JdbcConnection jdbcConnection;
    private final LocationWithOrderDataMapper mapper;

    private static final String QRY_SELECT_ALL_LOCATIONS = """
    SELECT latitude, longitude, endTime, intensity, range
    FROM orders
    """;

    private static final String QRY_COUNT_ALL_LOCATIONS = """
    SELECT COUNT(*)
    FROM orders
    """;

    private SqliteGetAllLocationsRepository(JdbcConnection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
        this.mapper = new LocationWithOrderDataMapper();
    }

    @Override
    public Deque<LocationWithOrderData> getAllLocations() {
        DoubleEndedQueue<LocationWithOrderData> locations = new DoubleEndedQueue<>(getRowCount());
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(QRY_SELECT_ALL_LOCATIONS, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {

            statement.setFetchSize(500);
            ResultSet resultSet = statement.executeQuery();
            processResultSet(resultSet, locations);

        } catch (Exception e) {
            handleException(e);
        }
        return locations.getDeque();
    }

    private int getRowCount() {
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(QRY_COUNT_ALL_LOCATIONS);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            handleException(e);
        }
        return 0;
    }

    private void processResultSet(ResultSet resultSet, DoubleEndedQueue<LocationWithOrderData> locations) throws SQLException {
        while (resultSet.next()) {
            LocationWithOrderData location1 = mapper.map(resultSet);
            if (resultSet.next()) {
                LocationWithOrderData location2 = mapper.map(resultSet);
                locations.addBothSides(location1, location2);
            } else {
                locations.getDeque().add(location1);
            }
        }
    }

    private void handleException(Exception e) {
        LOGGER.log(Level.SEVERE, "Could not retrieve locations from database", e);
        throw new LogLauncher("Could not retrieve locations from database", e, LOGGER, Level.SEVERE);
    }

    public static LocationsRepository initialize(JdbcConnection jdbcConnection) {
        if (instance != null)
            return instance;

        instance = new SqliteGetAllLocationsRepository(jdbcConnection);
        return instance;
    }

    public static LocationsRepository instance() {
        if (instance == null)
            throw new IllegalStateException("SqliteGetAllLocationsRepository has not been initialized");

        return instance;
    }
}