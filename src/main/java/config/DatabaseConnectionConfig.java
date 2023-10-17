package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/*
* interface reads application.properties file and gets database info(url, name, password)
* returns list.of(url, name, password)
* */
public class DatabaseConnectionConfig {
    public List<String> loadDatabaseProperties() {
        Properties properties = new Properties();
        try (FileInputStream file = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(file);
            List<String> property = List.of(
                    properties.getProperty("datasource.connection.url"),
                    properties.getProperty("datasource.connection.name"),
                    properties.getProperty("datasource.connection.password"));
            System.out.println("PROPERTIES: " + property);
            return property;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

