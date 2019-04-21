package self.erp.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@ComponentScan(value = { "self.erp.commons.restful", "self.erp.ui.controllers" })
public class Main extends Application {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private ConfigurableApplicationContext configurableApplicationContext;
    private Parent root;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        LOGGER.log(Level.INFO, "Java Version [ " + System.getProperty("java.version") + " ] with JavaFx version [ "
                + System.getProperty("javafx.version") + " ]");
    }

    @Override
    public void init() throws Exception {
        configurableApplicationContext = SpringApplication.run(Main.class);
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/fxmls/visits.fxml"));
        loader.setControllerFactory(configurableApplicationContext::getBean);
        root = loader.load();
        for (String beanDefName : configurableApplicationContext.getBeanDefinitionNames()) {
            LOGGER.log(Level.SEVERE, beanDefName);
        }

    }

    @Override
    public void stop() throws Exception {
        configurableApplicationContext.close();
    }
}
