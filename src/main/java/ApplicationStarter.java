import config.DatabaseConnectionConfig;
import config.LiquibaseMigrationConfig;
import conrtoller.Controller;


public class ApplicationStarter implements DatabaseConnectionConfig {
    public static void main(String[] args) {
        LiquibaseMigrationConfig config = new LiquibaseMigrationConfig();
        Controller controller = new Controller();
        config.run();
        controller.start();
    }
}
