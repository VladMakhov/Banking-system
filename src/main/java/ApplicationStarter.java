import conrtoller.Controller;
import util.LiquibaseMigration;


public class ApplicationStarter {
    public static void main(String[] args) {
        LiquibaseMigration liquibaseMigration = new LiquibaseMigration();
        Controller controller = new Controller();

        liquibaseMigration.run();
        controller.start();
    }
}
