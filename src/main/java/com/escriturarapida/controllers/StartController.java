package com.escriturarapida.controllers;

import com.escriturarapida.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import java.net.URL;

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
        URL fontUrl = getClass().getResource(fontPath);

        String fontUrlStr = fontUrl.toExternalForm(); // Convierte la URL en String una sola vez

        // Cargar fuentes reutilizando el método
        Font fuenteBoton = cargarFuente(fontUrlStr, 24);
        Font fuenteTitulo = cargarFuente(fontUrlStr, 27);
        Font fuenteDescripcion = cargarFuente(fontUrlStr, 18);
        Font fuenteNombre = cargarFuente(fontUrlStr, 26);

        // Aplicar fuentes a los elementos
        tituloSeccion.setFont(fuenteTitulo);
        descripcionSeccion.setFont(fuenteDescripcion);
        nombreJugadorSeccion.setFont(fuenteNombre);
        iniciarBoton.setFont(fuenteBoton);

        iniciarBoton.setOnAction(event -> iniciarJuego());
    }

    private Font cargarFuente(String url, double size) {
        return Font.loadFont(url, size);
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
}

