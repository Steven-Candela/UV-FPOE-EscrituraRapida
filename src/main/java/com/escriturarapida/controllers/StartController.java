package com.escriturarapida.controllers;

import com.escriturarapida.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import java.net.URL;

/**
 * Controlador para la pantalla de inicio
 * para dar paso a la pantalla del juego
 */
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

    /**
     * Metodo de inicialización de la escena "start"
     * de la pantalla de inicio
     */
    @FXML
    public void initialize() {
        String fontPath = "/com/escriturarapida/assets/fonts/alagard.ttf";
        URL fontUrl = getClass().getResource(fontPath);

        String fontUrlStr = fontUrl.toExternalForm();

        Font fuenteBoton = cargarFuente(fontUrlStr, 24);
        Font fuenteTitulo = cargarFuente(fontUrlStr, 27);
        Font fuenteDescripcion = cargarFuente(fontUrlStr, 18);
        Font fuenteNombre = cargarFuente(fontUrlStr, 26);

        tituloSeccion.setFont(fuenteTitulo);
        descripcionSeccion.setFont(fuenteDescripcion);
        nombreJugadorSeccion.setFont(fuenteNombre);
        iniciarBoton.setFont(fuenteBoton);

        iniciarBoton.setOnAction(event -> iniciarJuego());
    }

    /**
     * Funcion que reduce el codigo permitiendo
     * aplicar una fuente y tamaño a un objeto
     *
     * @param url Ubicacion de la fuente a aplicar
     * @param size Tamaño que se desea aplicar a la fuente
     */
    private Font cargarFuente(String url, double size) {
        return Font.loadFont(url, size);
    }

    /**
     * Inicia el juego mostrando la interfaz
     * donde comienza el juego
     */
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

