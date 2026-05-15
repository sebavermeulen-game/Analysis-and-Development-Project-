package be.howest.adria.infrastructure.persistence.repositories;

import be.howest.adria.application.contracts.repositories.UserRepository;
import be.howest.adria.domain.User;
import be.howest.adria.infrastructure.persistence.shared.utils.JdbcConnection;
import be.howest.adria.infrastructure.shared.utils.LogLauncher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqliteUserRepository implements UserRepository {

    private static final Logger LOGGER = Logger.getLogger(SqliteUserRepository.class.getName());
    private static SqliteUserRepository instance;
    private static final String QRY_BY_ID = """
            SELECT id
            FROM users
            WHERE id = ?;
            """;

    private static final String QRY_DELETE_BY_ID = """
            DELETE FROM users
            WHERE id = ?;
            """;

    private static final String QRY_INSERT_USER = """
            INSERT INTO users (id)
            VALUES (?);
            """;

    private static final String QRY_UPDATE_SUBSCRIPTION = """
            UPDATE users
            SET SubscriptionId = ?
            WHERE id = ?;
            """;

    private final JdbcConnection jdbcConnection;

    private SqliteUserRepository(JdbcConnection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
    }

    public static SqliteUserRepository initialize(JdbcConnection jdbcConnection) {
        if (instance != null)
            return instance;

        instance = new SqliteUserRepository(jdbcConnection);
        return instance;
    }

    public static SqliteUserRepository instance() {
        if (instance == null)
            throw new IllegalStateException("SqliteUserRepository has not been initialized");

        return instance;
    }

    @Override
    public Optional<User> byId(UUID userId) {
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(QRY_BY_ID)) {
            statement.setString(1, userId.toString());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return Optional.of(User.create());
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new LogLauncher("Failed to retrieve user by id", e, LOGGER, Level.SEVERE);
        }
    }

    public boolean userExists(UUID userId) {
        LOGGER.log(Level.INFO, "Checking if user exists for ID: {0}", userId);
        return queryUserById(userId).isPresent();
    }

    private Optional<User> queryUserById(UUID userId) {
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(QRY_BY_ID)) {
            statement.setString(1, userId.toString());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return Optional.of(User.create());
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new LogLauncher("Failed to query user by id", e, LOGGER, Level.SEVERE);
        }
    }

    @Override
    public void save(User user) {
        try (Connection connection = jdbcConnection.getConnection()) {
            // Begin transaction
            connection.setAutoCommit(false);

            // First delete the user
            try (PreparedStatement statement = connection.prepareStatement(QRY_DELETE_BY_ID)) {
                statement.setString(1, user.getId().toString());
                statement.executeUpdate();
            }

            // Then insert the user
            try (PreparedStatement statement = connection.prepareStatement(QRY_INSERT_USER)) {
                statement.setString(1, user.getId().toString());
                statement.executeUpdate();
            }

            // Commit transaction
            connection.commit();
        } catch (SQLException e) {
            throw new LogLauncher("Failed to save user", e, LOGGER, Level.SEVERE);
        }
    }

    public void updateSubscription(UUID userId, int subscriptionId) {
        LOGGER.log(Level.INFO, "Updating subscription for user ID: {0} with subscription ID: {1}", new Object[]{userId, subscriptionId});
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(QRY_UPDATE_SUBSCRIPTION)) {
            statement.setInt(1, subscriptionId);
            statement.setString(2, userId.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new LogLauncher("Failed to update subscription", e, LOGGER, Level.SEVERE);
        }
    }
}