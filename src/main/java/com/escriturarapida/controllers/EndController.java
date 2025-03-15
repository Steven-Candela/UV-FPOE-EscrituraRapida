package com.escriturarapida.controllers;

import com.escriturarapida.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;

import java.net.URL;

/**
 * Controlador para la pantalla final del juego "Escritura Rápida".
 * Muestra los resultados del juego y permite reiniciar la partida.
 */
public class EndController {
    @FXML
    private Label tiempoTranscurridoSeccion;

    @FXML
    private Label palabrasAcertadasSeccion;

    @FXML
    private Button reiniciarBoton;

    private long tiempoGuardado;
    private int aciertosGuardados;
    private boolean datosRecibidos = false;

    /**
     * Metodo de inicialización de la escena "end"
     * Configura la interfaz y asigna eventos a los controles.
     */
    @FXML
    public void initialize() {
        if (datosRecibidos) {
            actualizarResultados();
        }

        URL fontUrl = getClass().getResource("/com/escriturarapida/assets/fonts/alagard.ttf");
        Font fuenteBoton = Font.loadFont(fontUrl.toExternalForm(), 24);
        reiniciarBoton.setFont(fuenteBoton);


        reiniciarBoton.setOnAction(event -> reinciarJuego());

        // Evita que el <<Enter>> se presione accidentalmente
        Platform.runLater(() -> {
            if (reiniciarBoton.getScene() != null) {
                reiniciarBoton.getScene().setOnKeyPressed(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        event.consume();
                    }
                });
            }
        });
    }

    /**
     * Muestra los resultados finales del juego en la interfaz.
     *
     * @param tiempo Tiempo transcurrido en segundos.
     * @param aciertos Número de palabras acertadas.
     */
    public void mostrarResultados(long tiempo, int aciertos) {
        this.tiempoGuardado = tiempo;
        this.aciertosGuardados = aciertos;
        this.datosRecibidos = true;

        if (tiempoTranscurridoSeccion != null && palabrasAcertadasSeccion != null) {
            actualizarResultados();
        }
    }

    /**
     * Actualiza la interfaz con los datos de tiempo que estuvo jugando y la
     * cantidad de palabras acertadas
     */
    private void actualizarResultados() {
        int minutos = (int) (tiempoGuardado / 60);
        int segundos = (int) (tiempoGuardado % 60);

        String tiempoFormateado = String.format("%02d:%02d", minutos, segundos);
        tiempoTranscurridoSeccion.setText("Tiempo transcurrido: " + tiempoFormateado);
        palabrasAcertadasSeccion.setText("Palabras acertadas: " + aciertosGuardados);
    }

    /**
     * Reinicia el juego y muestra la pantalla inicial
     */
    private void reinciarJuego() {
        try {
            Main.mostrarInicio();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
