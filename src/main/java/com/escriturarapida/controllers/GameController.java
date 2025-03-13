package com.escriturarapida.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GameController {

    @FXML
    private Label cronometroSeccion;

    @FXML
    private ImageView vidasImage;

    private int tiempoRestante = 5;
    private Timeline timeline;

    @FXML
    public void initialize() {
        iniciarJuego();
        cargarFuente();
    }

    private void iniciarJuego() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            actualizarCronometroSeccion();


            if (tiempoRestante <= 0) {
                timeline.stop();
                finJuego();
            }

            tiempoRestante--;
        }));
        timeline.setCycleCount(timeline.INDEFINITE);
        timeline.play();
    }

    private void cargarFuente() {
        String fontPath = "/com/escriturarapida/assets/fonts/alagard.ttf";
        InputStream fontStream = getClass().getResourceAsStream(fontPath);

        Font alagardFont = Font.loadFont(fontStream, 30);
        cronometroSeccion.setFont(alagardFont);
    }

    private void actualizarCronometroSeccion() {
        int minutos = tiempoRestante / 60;
        int segundos = tiempoRestante % 60;
        cronometroSeccion.setText(String.format("%02d:%02d", minutos, segundos));
        cronometroSeccion.setFont(Font.font("Alagard", 30));
    }

    private void finJuego() {
        cronometroSeccion.setText("00:00");

        Image nuevaImagen = new Image(Objects.requireNonNull(getClass().getResource("/com/escriturarapida/assets/images/game_over.png")).toExternalForm());
        vidasImage.setImage(nuevaImagen);
    }
}