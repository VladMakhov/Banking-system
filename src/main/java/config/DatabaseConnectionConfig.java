package config;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.List;
import java.util.Properties;

/*
* interface reads application.properties file and gets database info(url, name, password)
* returns list.of(url, name, password)
* */
public interface DatabaseConnectionConfig {
    default List<String> loadDatabaseProperties() {
        Properties properties = new Properties();
        try (FileInputStream file = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(file);
            return List.of(properties.getProperty("db.url"), properties.getProperty("db.name"), properties.getProperty("db.password"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

