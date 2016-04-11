package com.rizki.mufrizal.aplikasi.musik.controller;

import com.rizki.mufrizal.aplikasi.musik.App;
import com.rizki.mufrizal.aplikasi.musik.model.Musik;
import java.io.File;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

/**
 *
 * @Author Rizki Mufrizal <mufrizalrizki@gmail.com>
 * @Since Apr 10, 2016
 * @Time 5:42:31 PM
 * @Encoding UTF-8
 * @Project Aplikasi-Musik
 * @Package com.rizki.mufrizal.aplikasi.musik
 *
 */
public class MusikController {

    @FXML
    private Slider sliderMusik;

    @FXML
    private Button play;

    @FXML
    private Button pause;

    @FXML
    private Button stop;

    @FXML
    private Button previous;

    @FXML
    private Button next;

    @FXML
    private Button repeat;

    @FXML
    private Label repeatMusik;

    @FXML
    private MenuItem about;

    @FXML
    private MenuItem quit;

    @FXML
    @SuppressWarnings("FieldMayBeFinal")
    private TableView<Musik> tabelMusik = new TableView<>();

    private FileChooser fileChooser;

    private final ObservableList<Musik> musiks = FXCollections.observableArrayList();

    private MediaPlayer mediaPlayer;

    private Boolean repeatBoolean = Boolean.FALSE;

    @SuppressWarnings("FieldMayBeFinal")
    private TableColumn judulLagu = new TableColumn("Judul Lagu");

    @SuppressWarnings("FieldMayBeFinal")
    private TableColumn pathLagu = new TableColumn("Path Lagu");

    @FXML
    public void initialize() {
        judulLagu.setMinWidth(250);
        pathLagu.setMinWidth(400);

        judulLagu.setCellValueFactory(new PropertyValueFactory<>("judulLagu"));
        pathLagu.setCellValueFactory(new PropertyValueFactory<>("pathLagu"));

        tabelMusik.getColumns().addAll(judulLagu, pathLagu);

        pause.setDisable(Boolean.TRUE);
        stop.setDisable(Boolean.TRUE);
        play.setDisable(Boolean.TRUE);
        previous.setDisable(Boolean.TRUE);
        next.setDisable(Boolean.TRUE);
        repeat.setDisable(Boolean.TRUE);

        repeatMusik.setText(String.valueOf(Boolean.FALSE));

        about.setOnAction((ActionEvent event) -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setHeaderText("About Our Project");
            alert.setContentText("Project ini dibuat dalam rangka belajar javafx \n Developer : Rizki Mufrizal <mufrizalrizki@gmail.com>");

            alert.showAndWait();
        });

        quit.setOnAction((ActionEvent event) -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi");
            alert.setHeaderText("Apakah anda ingin keluar ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Platform.exit();
                System.exit(0);
            } else {
                alert.close();
            }
        });
    }

    @FXML
    private void browseMusik(ActionEvent actionEvent) {
        fileChooser = new FileChooser();
        fileChooser.setTitle("pilih musik");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("mp3 file", "*.mp3"));

        List<File> files = fileChooser.showOpenMultipleDialog(new App().getStage());

        if (files != null) {
            files.stream().forEach((file) -> {
                musiks.add(new Musik(file.getName(), file.getPath()));
            });
        }

        tabelMusik.setItems(musiks);
        if (tabelMusik.getItems().size() > 0) {
            play.setDisable(Boolean.FALSE);
            previous.setDisable(Boolean.FALSE);
            next.setDisable(Boolean.FALSE);
        }
    }

    @FXML
    public void playMusik(ActionEvent actionEvent) {
        int selectedIndex = tabelMusik.getSelectionModel().getSelectedIndex();
        if (mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)) {
            mediaPlayer.play();
        } else if (mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            mediaPlayer.stop();
            jalankanMusik(selectedIndex);
        } else {
            jalankanMusik(selectedIndex);
        }

        play.setDisable(Boolean.TRUE);
        stop.setDisable(Boolean.FALSE);
        pause.setDisable(Boolean.FALSE);
        repeat.setDisable(Boolean.FALSE);
    }

    @FXML
    public void stopMusik(ActionEvent actionEvent) {
        if ((mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) || (mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED))) {
            mediaPlayer.stop();
            play.setDisable(Boolean.FALSE);
            stop.setDisable(Boolean.TRUE);
            pause.setDisable(Boolean.TRUE);
        }
    }

    @FXML
    public void pauseMusik(ActionEvent actionEvent) {
        if (mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            mediaPlayer.pause();
            play.setDisable(Boolean.FALSE);
            stop.setDisable(Boolean.FALSE);
            pause.setDisable(Boolean.TRUE);
        }
    }

    @FXML
    public void nextMusik(ActionEvent actionEvent) {
        if ((mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) || (mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)) || (mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.STOPPED))) {
            int selectedIndex = tabelMusik.getSelectionModel().getSelectedIndex();

            if (selectedIndex < musiks.size()) {
                mediaPlayer.stop();
                jalankanMusik(selectedIndex + 1);
                tabelMusik.getSelectionModel().select(selectedIndex + 1);
            }
        }
    }

    @FXML
    public void previousMusik(ActionEvent actionEvent) {
        if ((mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) || (mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)) || (mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.STOPPED))) {
            int selectedIndex = tabelMusik.getSelectionModel().getSelectedIndex();

            if (selectedIndex > 0) {
                mediaPlayer.stop();
                jalankanMusik(selectedIndex - 1);
                tabelMusik.getSelectionModel().select(selectedIndex - 1);
            }

        }
    }

    @FXML
    public void repeatMusik(ActionEvent actionEvent) {
        if ((mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) || (mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)) || (mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.STOPPED))) {
            repeatBoolean = !repeatBoolean;
            repeatMusik.setText(String.valueOf(repeatBoolean));
        }
    }

    private void jalankanMusik(int selectedIndex) {
        File file = new File(tabelMusik.getItems().get(selectedIndex).getPathLagu());
        String path = file.toURI().toASCIIString();

        Media media = new Media(path);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        mediaPlayer.currentTimeProperty().addListener((observableValue, oldDuration, newDuration) -> {
            sliderMusik.setMax(mediaPlayer.getTotalDuration().toSeconds());
            sliderMusik.setValue(mediaPlayer.getCurrentTime().toSeconds());
            if (repeatBoolean) {
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            } else {
                mediaPlayer.setCycleCount(0);
            }
        });
    }

}
