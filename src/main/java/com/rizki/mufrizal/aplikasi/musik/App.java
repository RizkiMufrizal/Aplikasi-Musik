package com.rizki.mufrizal.aplikasi.musik;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Musik.fxml"));

        Scene scene = new Scene(root);

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
