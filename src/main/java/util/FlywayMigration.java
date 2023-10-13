package util;

import org.flywaydb.core.Flyway;

public class FlywayMigration implements DatabaseConnection{
    public void flywayStarter() {
        Flyway flyway = Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(URL, NAME, PASSWORD)
                .load();
        flyway.migrate();
    }
}
