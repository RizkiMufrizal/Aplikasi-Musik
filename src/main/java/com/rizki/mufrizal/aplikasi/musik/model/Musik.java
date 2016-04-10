package com.rizki.mufrizal.aplikasi.musik.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @Author Rizki Mufrizal <mufrizalrizki@gmail.com>
 * @Since Apr 10, 2016
 * @Time 6:03:09 PM
 * @Encoding UTF-8
 * @Project Aplikasi-Musik
 * @Package com.rizki.mufrizal.aplikasi.musik.model
 *
 */
public class Musik {

    private final SimpleStringProperty judulLagu;
    private final SimpleStringProperty pathLagu;

    public Musik(String judulLagu, String pathLagu) {
        this.judulLagu = new SimpleStringProperty(judulLagu);
        this.pathLagu = new SimpleStringProperty(pathLagu);
    }

    /**
     * @return the judulLagu
     */
    public String getJudulLagu() {
        return judulLagu.get();
    }

    /**
     * @param judulLagu the judulLagu to set
     */
    public void setJudulLagu(String judulLagu) {
        this.judulLagu.set(judulLagu);
    }

    /**
     * @return the pathLagu
     */
    public String getPathLagu() {
        return pathLagu.get();
    }

    /**
     * @param pathLagu the pathLagu to set
     */
    public void setPathLagu(String pathLagu) {
        this.pathLagu.set(pathLagu);
    }

}
