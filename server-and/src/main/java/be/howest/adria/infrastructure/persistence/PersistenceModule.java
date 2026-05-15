package be.howest.adria.infrastructure.persistence;

import be.howest.adria.infrastructure.persistence.migrations.Migration;
import be.howest.adria.infrastructure.persistence.migrations.Seeder;
import be.howest.adria.infrastructure.persistence.repositories.*;
import be.howest.adria.infrastructure.persistence.repositories.mappers.SubscriptionsMapper;
import be.howest.adria.infrastructure.persistence.shared.utils.JdbcConnection;
import be.howest.adria.infrastructure.shared.utils.Config;

public class PersistenceModule {
  private static Config config;

  public static void init(Config cfg) {
    config = cfg;
    JdbcConnection.initialize(config.readSetting("sqlite.connectionString"));
    buildRepositories();
    migrateDatabase();
    seedDatabase();
  }

  private static void buildRepositories() {
    SqliteGetAllSubscriptions.initialize(JdbcConnection.instance(), new SubscriptionsMapper());
    SqliteUserRepository.initialize(JdbcConnection.instance());
    SqliteOrderRepository.initialize(JdbcConnection.instance());
    SqliteUserSubscriptionRepository.initialize(JdbcConnection.instance());
    SqliteGetAllLocationsRepository.initialize(JdbcConnection.instance());
  }

  private static void migrateDatabase() {
    migrateReadDatabase();
  }

  private static void migrateReadDatabase() {
    String migrationFilePath = config.readSetting("sqlite.migration.file");
    Migration.migrate(migrationFilePath, JdbcConnection.instance());
  }

  private static void seedDatabase() {
    Seeder seeder = new Seeder(
            SqliteGetAllSubscriptions.instance()
    );

    seeder.seed();
  }

  private PersistenceModule() {
    throw new IllegalStateException("Utility class");
  }
}