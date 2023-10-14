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
    default List<String> load() {
        Properties prop = new Properties();
        try (FileInputStream file = new FileInputStream("src/main/resources/application.properties")) {
            prop.load(file);
            return List.of(prop.getProperty("db.url"), prop.getProperty("db.name"), prop.getProperty("db.password"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

