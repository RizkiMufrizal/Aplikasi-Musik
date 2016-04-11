package com.rizki.mufrizal.aplikasi.musik;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @Author Rizki Mufrizal <mufrizalrizki@gmail.com>
 * @Since Apr 10, 2016
 * @Time 5:21:21 PM
 * @Encoding UTF-8
 * @Project Aplikasi-Musik
 * @Package com.rizki.mufrizal.aplikasi.musik
 *
 */
public class App extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {

        LOGGER.debug("load file fxml");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Musik.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/css/style.css");

        this.stage = stage;

        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getStage() {
        return stage;
    }

}
