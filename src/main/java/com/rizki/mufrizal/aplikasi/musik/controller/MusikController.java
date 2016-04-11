package com.rizki.mufrizal.aplikasi.musik.controller;

import com.rizki.mufrizal.aplikasi.musik.App;
import com.rizki.mufrizal.aplikasi.musik.model.Musik;
import java.io.File;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    private MediaPlayer mediaPlayer;
    private Boolean repeat = false;

    @SuppressWarnings("FieldMayBeFinal")
    private TableColumn judulLagu = new TableColumn("Judul Lagu");
    @SuppressWarnings("FieldMayBeFinal")
    private TableColumn pathLagu = new TableColumn("Path Lagu");

    @FXML
    private Slider sliderMusik;

    @FXML
    @SuppressWarnings("FieldMayBeFinal")
    private TableView<Musik> tabelMusik = new TableView<>();

    private FileChooser fileChooser;
    private final ObservableList<Musik> musiks = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        judulLagu.setMinWidth(250);
        pathLagu.setMinWidth(400);

        judulLagu.setCellValueFactory(new PropertyValueFactory<>("judulLagu"));
        pathLagu.setCellValueFactory(new PropertyValueFactory<>("pathLagu"));

        tabelMusik.getColumns().addAll(judulLagu, pathLagu);
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
    }

    @FXML
    public void stopMusik(ActionEvent actionEvent) {
        if ((mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) || (mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED))) {
            mediaPlayer.stop();
        }
    }

    @FXML
    public void pauseMusik(ActionEvent actionEvent) {
        if (mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            mediaPlayer.pause();
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
            repeat = !repeat;
        }
    }

    public void jalankanMusik(int selectedIndex) {
        File file = new File(tabelMusik.getItems().get(selectedIndex).getPathLagu());
        String path = file.toURI().toASCIIString();

        Media media = new Media(path);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        mediaPlayer.currentTimeProperty().addListener((observableValue, oldDuration, newDuration) -> {
            sliderMusik.setMax(mediaPlayer.getTotalDuration().toSeconds());
            sliderMusik.setValue(mediaPlayer.getCurrentTime().toSeconds());
            if (repeat) {
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            } else {
                mediaPlayer.setCycleCount(0);
            }
        });
    }

}
