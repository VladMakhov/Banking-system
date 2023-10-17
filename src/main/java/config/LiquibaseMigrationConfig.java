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
public class LiquibaseMigrationConfig {

    public void run() {
        DatabaseConnectionConfig databaseConnectionConfig = new DatabaseConnectionConfig();
        List<String> info = databaseConnectionConfig.loadDatabaseProperties();
        execute(info.get(0), info.get(1), info.get(2));
    }

    public void run(String URL, String USERNAME, String PASSWORD) {
        execute(URL, USERNAME, PASSWORD);
    }


    /*
    * 'databasechangelog' and 'databasechangeloglock' creates in 'utilities' schema
    * and all the rest tables in 'entities' schema
    * */
    private static void execute(String URL, String USERNAME, String PASSWORD) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("""
                    create schema if not exists entities;
                    create schema if not exists utilities;
                    """);
            statement.close();
            Database database = DatabaseFactory
                    .getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName("utilities");
            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml",
                    new ClassLoaderResourceAccessor(),
                    database);
            liquibase.update();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

}
