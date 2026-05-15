package be.howest.adria.infrastructure.persistence.repositories;

import be.howest.adria.application.contracts.repositories.CreateUserSubscriptionRepository;
import be.howest.adria.domain.Subscription;
import be.howest.adria.domain.User;
import be.howest.adria.infrastructure.persistence.repositories.mappers.SubscriptionsMapper;
import be.howest.adria.infrastructure.persistence.shared.utils.JdbcConnection;
import be.howest.adria.infrastructure.shared.utils.LogLauncher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqliteUserSubscriptionRepository implements CreateUserSubscriptionRepository {

    private static final Logger LOGGER = Logger.getLogger(SqliteUserSubscriptionRepository.class.getName());
    private static SqliteUserSubscriptionRepository instance;

    private static final String QRY_UPDATE_USER_SUBSCRIPTION = """
            UPDATE users
            SET SubscriptionId = ?
            WHERE id = ?;
            """;

    private static final String QRY_FIND_SUBSCRIPTION_BY_ID = """
            SELECT subscriptionId, subscriptionType, subscriptionPlan, hours, range, intensity,
            extraInformation, requestInformation, subscriptionSupport, price
            FROM subscriptions
            WHERE subscriptionId = ?;
            """;

    private final JdbcConnection jdbcConnection;
    private final SubscriptionsMapper subscriptionsMapper;

    private SqliteUserSubscriptionRepository(JdbcConnection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
        this.subscriptionsMapper = new SubscriptionsMapper();
    }

    public static SqliteUserSubscriptionRepository initialize(JdbcConnection jdbcConnection) {
        if (instance != null)
            return instance;

        instance = new SqliteUserSubscriptionRepository(jdbcConnection);
        return instance;
    }

    public static SqliteUserSubscriptionRepository instance() {
        if (instance == null)
            throw new IllegalStateException("SqliteUserSubscriptionRepository has not been initialized");
        return instance;
    }

    @Override
    public void save(User user) {
        try (Connection connection = jdbcConnection.getConnection()) {
            connection.setAutoCommit(false);

            if (user.getSubscription() != null) {
                int subscriptionId = user.getSubscription().getSubscriptionId();
                LOGGER.log(Level.INFO, "Saving user subscription. User ID: {0}, Subscription ID: {1}", new Object[]{user.getId(), subscriptionId});
                updateUserSubscription(connection, user.getId().toString(), subscriptionId);
            } else {
                LOGGER.log(Level.WARNING, "User does not have a subscription yet");
            }

            connection.commit();
        } catch (SQLException e) {
            throw new LogLauncher(String.format("Failed to save user subscription for user ID: %s", user.getId()), e, LOGGER, Level.SEVERE);
        }
    }

    @Override
    public Subscription findById(int subscriptionId) {
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(QRY_FIND_SUBSCRIPTION_BY_ID)) {
            statement.setInt(1, subscriptionId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return subscriptionsMapper.map(resultSet);
                } else {
                    throw new SQLException("Subscription not found for the given ID");
                }
            }
        } catch (SQLException e) {
            throw new LogLauncher("Failed to find subscription by ID", e, LOGGER, Level.SEVERE);
        }
    }

    private void updateUserSubscription(Connection connection, String userId, int subscriptionId) throws SQLException {
        LOGGER.log(Level.INFO, "Updating user subscription for user ID: {0} with subscription ID: {1}", new Object[]{userId, subscriptionId});
        try (PreparedStatement statement = connection.prepareStatement(QRY_UPDATE_USER_SUBSCRIPTION)) {
            statement.setInt(1, subscriptionId);
            statement.setString(2, userId);
            int rowsUpdated = statement.executeUpdate();
            LOGGER.log(Level.INFO, "Number of rows updated: {0}", rowsUpdated);
        } catch (SQLException e) {
            throw new LogLauncher("Failed to update user subscription", e, LOGGER, Level.SEVERE);
        }
    }
}