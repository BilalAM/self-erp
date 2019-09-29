package self.erp.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * This is the main entry point of the Springboot JavaFX application .
 *
 * The bean configuration and application context spring "magic" happens inside the init() step of the JavaFX life cycle
 * where Springboot kicks in and start configuring the beans and all.
 *
 * This implementation has been done by taking
 *
 * <a href="https://github.com/mvpjava/springboot-javafx-tutorial">link</a>
 *
 * as a reference by github user mvpjava (https://github.com/mvpjava)
 */
@SpringBootApplication
@ComponentScan(value = { "self.erp.commons.restful", "self.erp.ui.controllers" })
public class Main extends Application {
    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(Main.class.getName());

    private ConfigurableApplicationContext configurableApplicationContext;
    private Parent root;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        LOGGER.info("Java Version [ " + System.getProperty("java.version") + " ] with JavaFx version [ "
                + System.getProperty("javafx.version") + " ]");
    }

    @Override
    public void init() throws Exception {
        configurableApplicationContext = SpringApplication.run(Main.class);
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/fxmls/visits.fxml"));
        loader.setControllerFactory(configurableApplicationContext::getBean);
        root = loader.load();
    }

    @Override
    public void stop() throws Exception {
        configurableApplicationContext.close();
    }
}
