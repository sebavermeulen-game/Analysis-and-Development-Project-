package be.howest.adria.infrastructure.persistence.repositories;

import be.howest.adria.application.contracts.repositories.SubscriptionRepository;
import be.howest.adria.domain.Subscription;
import be.howest.adria.infrastructure.persistence.repositories.mappers.ResultSetMapper;
import be.howest.adria.infrastructure.persistence.shared.utils.JdbcConnection;
import be.howest.adria.infrastructure.shared.utils.LogLauncher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqliteGetAllSubscriptions implements SubscriptionRepository {

    private static final Logger LOGGER = Logger.getLogger(SqliteGetAllSubscriptions.class.getName());
    private static SubscriptionRepository instance;
    private final JdbcConnection jdbcConnection;
    private final ResultSetMapper<Subscription> subscriptionMapper;

    private static final String QRY_SELECT_ALL_SUBSCRIPTIONS = """
           SELECT subscriptionId, subscriptionType, subscriptionPlan, hours, range, intensity, extraInformation,
           requestInformation, subscriptionSupport, price
           FROM subscriptions
           """;
    private static final String QRY_INSERT_SUBSCRIPTION = """
               INSERT INTO subscriptions (subscriptionType, subscriptionPlan, hours, range, intensity, extraInformation,
               requestInformation, subscriptionSupport, price)
               VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
               """;

    private SqliteGetAllSubscriptions(JdbcConnection jdbcConnection, ResultSetMapper<Subscription> subscriptionMapper) {
        this.jdbcConnection = jdbcConnection;
        this.subscriptionMapper = subscriptionMapper;
    }

    public static SubscriptionRepository initialize(JdbcConnection jdbcConnection, ResultSetMapper<Subscription> subscriptionMapper) {
        if (instance == null) {
            instance = new SqliteGetAllSubscriptions(jdbcConnection, subscriptionMapper);
        }
        return instance;
    }

    public static SubscriptionRepository instance() {
        if (instance == null) {
            throw new IllegalStateException("Repository is not initialized");
        }
        return instance;
    }

    @Override
    public List<Subscription> findAll() {
        List<Subscription> subscriptions = new ArrayList<>();
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(QRY_SELECT_ALL_SUBSCRIPTIONS)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                subscriptions.add(subscriptionMapper.map(resultSet));
            }

        } catch (Exception e) {
            throw new LogLauncher("Could not retrieve subscriptions from database", e, LOGGER, Level.SEVERE);
        }
        return subscriptions;
    }

    @Override
    public void saveAll(List<Subscription> subscriptions) {
        try (Connection connection = jdbcConnection.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(QRY_INSERT_SUBSCRIPTION)) {
                for (Subscription subscription : subscriptions) {
                    statement.setString(1, subscription.getSubscriptionType());
                    statement.setString(2, subscription.getSubscriptionPlan());
                    statement.setInt(3, subscription.getHours());
                    statement.setInt(4, subscription.getRange());
                    statement.setString(5, subscription.getIntensity().name());
                    statement.setString(6, subscription.getExtraInformation());
                    statement.setString(7, subscription.getRequestInformation());
                    statement.setString(8, subscription.getSubscriptionSupport());
                    statement.setBigDecimal(9, subscription.getPrice());
                    statement.addBatch();
                }
                statement.executeBatch();
            }
            connection.commit();
        } catch (Exception e) {
            throw new LogLauncher("Could not save subscriptions to database", e, LOGGER, Level.SEVERE);
        }
    }
}