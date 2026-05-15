package be.howest.adria.infrastructure.persistence.repositories;

import be.howest.adria.application.contracts.repositories.OrderRepository;
import be.howest.adria.domain.Order;
import be.howest.adria.domain.Subscription;
import be.howest.adria.infrastructure.persistence.repositories.mappers.SubscriptionsMapper;
import be.howest.adria.infrastructure.persistence.shared.utils.JdbcConnection;
import be.howest.adria.infrastructure.shared.utils.LogLauncher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqliteOrderRepository implements OrderRepository {

    private static final Logger LOGGER = Logger.getLogger(SqliteOrderRepository.class.getName());
    private static SqliteOrderRepository instance;

    private static final String QRY_FIND_SUBSCRIPTION_BY_USER_ID = """
            SELECT s.*
            FROM subscriptions s
            JOIN users u ON s.subscriptionId = u.SubscriptionId
            WHERE u.id = ?;
            """;

    private static final String QRY_INSERT_ORDER = """
            INSERT INTO orders (latitude, longitude, intensity, startTime, endTime, range, userid)
            VALUES (?, ?, ?, ?, ?, ?, ?);
            """;

    private static final String QRY_FIND_ORDER_ID = """
            SELECT orderId
            FROM orders
            WHERE latitude = ? AND longitude = ? AND intensity = ? AND startTime = ? AND endTime = ? AND range = ? AND userid = ?;
            """;

    private final JdbcConnection jdbcConnection;
    private final SubscriptionsMapper subscriptionsMapper;

    private SqliteOrderRepository(JdbcConnection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
        this.subscriptionsMapper = new SubscriptionsMapper();
    }

    public static SqliteOrderRepository initialize(JdbcConnection jdbcConnection) {
        if (instance != null)
            return instance;

        instance = new SqliteOrderRepository(jdbcConnection);
        return instance;
    }

    public static SqliteOrderRepository instance() {
        if (instance == null)
            throw new IllegalStateException("SqliteOrderRepository has not been initialized");
        return instance;
    }

    @Override
    public Subscription findSubscriptionByUserId(UUID userId) {
        LOGGER.log(Level.INFO, "Executing query to find subscription by user ID: {0}", userId);
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(QRY_FIND_SUBSCRIPTION_BY_USER_ID)) {
            statement.setString(1, userId.toString());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    LOGGER.log(Level.INFO, "Found subscription for user ID: {0}", userId);
                    return subscriptionsMapper.map(resultSet);
                } else {
                    throw new SQLException("Subscription not found for the given user ID");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to find subscription by user ID: {0}", userId);
            throw new IllegalStateException("Failed to find subscription by user ID", e);
        }
    }

    @Override
    public void save(Order order) {
        LOGGER.log(Level.INFO, "Executing query to save order: {0}", order);
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement insertOrderStatement = connection.prepareStatement(QRY_INSERT_ORDER)) {

            setOrderParameters(insertOrderStatement, order);

            int rowsInserted = insertOrderStatement.executeUpdate();
            LOGGER.log(Level.INFO, "Order saved successfully, rows inserted: {0}", rowsInserted);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to save order: {0}", order);
            throw new LogLauncher("Failed to save order", e, LOGGER, Level.SEVERE);        }
    }

    @Override
    public int findOrderId(Order order) {
        LOGGER.log(Level.INFO, "Executing query to find order ID for order: {0}", order);
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(QRY_FIND_ORDER_ID)) {

            setOrderParameters(statement, order);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("orderId");
                } else {
                    throw new SQLException("Order ID not found for the given order details");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to find order ID for order: {0}", order);
            throw new IllegalStateException("Failed to find order ID for order", e);
        }
    }

    private void setOrderParameters(PreparedStatement statement, Order order) throws SQLException {
        statement.setDouble(1, order.getLocation().getLatitude());
        statement.setDouble(2, order.getLocation().getLongitude());
        statement.setInt(3, order.getIntensity());
        statement.setTimestamp(4, java.sql.Timestamp.valueOf(order.getStartTime()));
        statement.setTimestamp(5, java.sql.Timestamp.valueOf(order.getEndTime()));
        statement.setInt(6, order.getRange());
        statement.setString(7, order.getUserId());
    }
}