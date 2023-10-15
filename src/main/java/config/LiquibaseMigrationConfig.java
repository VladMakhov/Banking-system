package config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

/*
 * Liquibase's configuration class. Reading properties from application.properties
 * Entities tables stored on private schema
 * */
public class LiquibaseMigrationConfig implements DatabaseConnectionConfig {
    public void run() {
        List<String> DatabaseConnection = loadDatabaseProperties();

        try (Connection connection = DriverManager.getConnection(
                DatabaseConnection.get(0),
                DatabaseConnection.get(1),
                DatabaseConnection.get(2))) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("""
                    create schema if not exists private;
                    create schema if not exists public;
                    """);
            statement.close();
            Database database = DatabaseFactory
                    .getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml",
                    new ClassLoaderResourceAccessor(),
                    database);

            liquibase.update();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

}
