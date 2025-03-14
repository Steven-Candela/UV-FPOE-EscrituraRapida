package com.escriturarapida.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.Objects;
import java.util.Random;

public class GameController {

    @FXML
    private ImageView fondoImagen;

    @FXML
    private Label cronometroSeccion;

    @FXML
    private ImageView vidasImagen;

    @FXML
    private Label palabraSeccion;

    @FXML
    private TextField escrituraSeccion;

    private int tiempoRestante = 20;
    private int vidas = 4;
    private Timeline timeline;
    private String palabraActual;

    @FXML
    public void initialize() {
        iniciarJuego();
        cargarFuente();
        cargarEntradaTexto();
        actualizarVidasImagen();
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

    public static String cargarPalabras() {

        String[] words = {
                "Sol", "Mar", "Pan", "Rey", "Gas", "Casa", "Arbol", "Libro", "Gato", "Perro", "Playa", "Mesa", "Cielo",
                "Flores", "Musica", "Ciudad", "Montaña", "Amigos", "Familia", "Trabajo", "Escuela", "Hospital", "Iglesia"
        };

        /*
                "Telefono", "Ventana", "Computadora", "Restaurante", "Biblioteca", "Aeropuerto", "Universidad", "Automovil",
                "Television", "Supermercado", "Diccionario", "Enciclopedia", "Comunicacion", "Investigacion", "Matematicas",
                "Geografia", "Psicologia", "Electrodomestico", "Administracion", "Constitucion", "Responsabilidad", "Independencia",
                "Internacionalizacion", "Descentralizacion", "Contemporaneamente", "Extraordinariamente", "Inconstitucionalidad"
         */

        Random palabraRandom = new Random();
        return words[palabraRandom.nextInt(words.length)];
    }

    private void actualizarPalabraSeccion() {
        palabraActual = cargarPalabras();
        palabraSeccion.setText(palabraActual);
    }

    private void actualizarCronometroSeccion() {
        int minutos = tiempoRestante / 60;
        int segundos = tiempoRestante % 60;
        cronometroSeccion.setText(String.format("%02d:%02d", minutos, segundos));
        cronometroSeccion.setFont(Font.font("Alagard", 30));
    }

    private void cargarEntradaTexto() {
        escrituraSeccion.setOnAction(event -> verificarPalabra());
    }

    private void verificarPalabra() {
        String palabraIngresada = escrituraSeccion.getText().trim();

        if (palabraIngresada.equalsIgnoreCase(palabraActual)) {
            cambiarFondoImagen("/com/escriturarapida/assets/images/correct.png");
        } else {
            cambiarFondoImagen("/com/escriturarapida/assets/images/incorrect.png");
            perderVida();
        }

        Timeline delay = new Timeline(new KeyFrame(Duration.seconds(0.5), event ->
                cambiarFondoImagen("/com/escriturarapida/assets/images/background.png")
        ));
        delay.setCycleCount(1);
        delay.play();

        escrituraSeccion.clear();
        actualizarPalabraSeccion();
    }

    private void perderVida() {
        vidas--;

        if (vidas <= 0) {
            finJuego();
        }
        else {
            actualizarVidasImagen();
        }
    }

    private void actualizarVidasImagen() {
        String rutaImagen = String.format("/com/escriturarapida/assets/images/vidas_%d.png", vidas);
        cambiarVidasImagen(rutaImagen);
    }

    private void cambiarFondoImagen(String rutaImagen) {
        Image nuevaImagen = new Image(Objects.requireNonNull(getClass().getResource(rutaImagen)).toExternalForm());
        fondoImagen.setImage(nuevaImagen);
    }

    private void cambiarVidasImagen(String rutaImagen) {
        Image nuevaImagen = new Image(Objects.requireNonNull(getClass().getResource(rutaImagen)).toExternalForm());
        vidasImagen.setImage(nuevaImagen);
    }

    private void finJuego() {
        cronometroSeccion.setText("00:00");
        palabraSeccion.setText("");

        cambiarVidasImagen("/com/escriturarapida/assets/images/game_over.png");
        timeline.stop();
        escrituraSeccion.setVisible(false);
        palabraSeccion.setVisible(false);
    }
}