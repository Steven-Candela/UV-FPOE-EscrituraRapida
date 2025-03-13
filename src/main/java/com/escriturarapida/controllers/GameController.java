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
import java.util.Objects;
import java.util.Random;

public class GameController {

    @FXML
    private Label cronometroSeccion;

    @FXML
    private Label palabraSeccion;

    @FXML
    private ImageView vidasImagen;

    private int tiempoRestante = 5;
    private Timeline timeline;

    @FXML
    public void initialize() {
        iniciarJuego();
        cargarFuente();
    }

    private void iniciarJuego() {

        actualizarPalabraSeccion();
        actualizarCronometroSeccion();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {

            if (tiempoRestante <= 0) {
                timeline.stop();
                finJuego();
            }

            actualizarCronometroSeccion();
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

    public static String cargarPalabras() throws IOException {

        String[] words = {
                "Sol", "Mar", "Pan", "Rey", "Gas", "Casa", "Arbol", "Libro", "Gato", "Perro", "Playa", "Mesa", "Cielo",
                "Flores", "Musica", "Ciudad", "Montaña", "Amigos", "Familia", "Trabajo", "Escuela", "Hospital", "Iglesia"
        };

        /*
        ,
                "Telefono", "Ventana", "Computadora", "Restaurante", "Biblioteca", "Aeropuerto", "Universidad", "Automovil",
                "Television", "Supermercado", "Diccionario", "Enciclopedia", "Comunicacion", "Investigacion", "Matematicas",
                "Geografia", "Psicologia", "Electrodomestico", "Administracion", "Constitucion", "Responsabilidad", "Independencia",
                "Internacionalizacion", "Descentralizacion", "Contemporaneamente", "Extraordinariamente", "Inconstitucionalidad"
         */

        Random palabraRandom = new Random();
        return words[palabraRandom.nextInt(words.length)];
    }

    private void actualizarPalabraSeccion() {
        String word = null;
        try {
            word = cargarPalabras();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        palabraSeccion.setText(word);
    }

    private void actualizarCronometroSeccion() {
        int minutos = tiempoRestante / 60;
        int segundos = tiempoRestante % 60;
        cronometroSeccion.setText(String.format("%02d:%02d", minutos, segundos));
        cronometroSeccion.setFont(Font.font("Alagard", 30));
    }



    private void finJuego() {
        cronometroSeccion.setText("00:00");
        palabraSeccion.setText("");

        Image nuevaImagen = new Image(Objects.requireNonNull(getClass().getResource("/com/escriturarapida/assets/images/game_over.png")).toExternalForm());
        vidasImagen.setImage(nuevaImagen);
    }
}