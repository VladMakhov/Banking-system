package util;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;

public class LiquibaseMigration implements DatabaseConnection {
    public void run() {
        try (Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD)) {

            Database database = DatabaseFactory
                    .getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml",
                    new ClassLoaderResourceAccessor(),
                    database);

            liquibase.update();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
