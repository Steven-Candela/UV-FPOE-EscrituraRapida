package com.escriturarapida.controllers;

import com.escriturarapida.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.io.InputStream;

public class StartController {

    @FXML
    private Label tituloSeccion;

    @FXML
    private Label descripcionSeccion;

    @FXML
    private TextField nombreJugadorSeccion;

    @FXML
    private Button iniciarBoton;

    private static String jugador; // Para almacenar el nombre del jugador

    @FXML
    public void initialize() {
        String fontPath = "/com/escriturarapida/assets/fonts/alagard.ttf";
        InputStream fontStream = getClass().getResourceAsStream(fontPath);

        if (fontStream == null) {
            System.out.println("Error: No se pudo cargar la fuente. Verifica la ruta.");
            return; // Sale del método si no se encuentra la fuente
        }

        Font fuenteBoton = Font.loadFont(fontStream, 24);
        Font fuenteTitulo = Font.loadFont(getClass().getResource(fontPath).toExternalForm(), 27);
        Font fuenteDescripcion = Font.loadFont(getClass().getResource(fontPath).toExternalForm(), 18);
        Font fuenteNombre = Font.loadFont(getClass().getResource(fontPath).toExternalForm(), 26);

        tituloSeccion.setFont(fuenteTitulo);
        descripcionSeccion.setFont(fuenteDescripcion);
        nombreJugadorSeccion.setFont(fuenteNombre);
        iniciarBoton.setFont(fuenteBoton);
        iniciarBoton.setOnAction(event -> iniciarJuego());
    }

    private void iniciarJuego() {
        jugador = nombreJugadorSeccion.getText().trim();

        if (!jugador.isEmpty()) {
            try {
                Main.mostrarJuego();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            nombreJugadorSeccion.setPromptText("Ingresa tu nombre!");
        }
    }

    public static String getJugador() {
        return jugador;
    }
}

