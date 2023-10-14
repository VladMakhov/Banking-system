package config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class LiquibaseMigrationConfig implements DatabaseConnectionConfig {
    public void run() {
        List<String> DatabaseConnection = load();
        try (Connection connection = DriverManager.getConnection(DatabaseConnection.get(0), DatabaseConnection.get(1), DatabaseConnection.get(2))) {

            Database database = DatabaseFactory
                    .getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml",
                    new ClassLoaderResourceAccessor(),
                    database);

            liquibase.update();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
