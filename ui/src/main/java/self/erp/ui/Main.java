package self.erp.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/self-erp.fxml"));

        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
        LOGGER.log(Level.INFO, "Java Version [ " + System.getProperty("java.version") + " ] with JavaFx version [ "
                + System.getProperty("javafx.version") + " ]");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
