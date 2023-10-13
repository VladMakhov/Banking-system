import conrtoller.Controller;
import util.FlywayMigration;


public class ApplicationStarter {
    public static void main(String[] args) {
        FlywayMigration flywayMigration = new FlywayMigration();
        Controller controller = new Controller();

        flywayMigration.flywayStarter();
        controller.start();
    }
}
