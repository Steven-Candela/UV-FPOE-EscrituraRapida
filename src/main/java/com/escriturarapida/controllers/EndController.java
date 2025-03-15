package com.escriturarapida.controllers;

import com.escriturarapida.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;

import java.net.URL;

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

    @FXML
    public void initialize() {
        if (datosRecibidos) {
            actualizarUI();
        }

        // Cargar la fuente correctamente
        URL fontUrl = getClass().getResource("/com/escriturarapida/assets/fonts/alagard.ttf");
        if (fontUrl != null) {
            Font fuenteBoton = Font.loadFont(fontUrl.toExternalForm(), 24);
            reiniciarBoton.setFont(fuenteBoton);
        } else {
            System.err.println("Fuente no encontrada: alagard.ttf");
        }


        reiniciarBoton.setOnAction(event -> reinciarJuego());

        // Asegurar que la escena ya está cargada antes de capturar teclas
        Platform.runLater(() -> {
            if (reiniciarBoton.getScene() != null) {
                reiniciarBoton.getScene().setOnKeyPressed(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        event.consume();  // Evita que se presione accidentalmente
                    }
                });
            }
        });
    }


    public void mostrarResultados(long tiempo, int aciertos) {
        this.tiempoGuardado = tiempo;
        this.aciertosGuardados = aciertos;
        this.datosRecibidos = true;

        if (tiempoTranscurridoSeccion != null && palabrasAcertadasSeccion != null) {
            actualizarUI();
        }
    }

    private void actualizarUI() {
        int minutos = (int) (tiempoGuardado / 60);
        int segundos = (int) (tiempoGuardado % 60);

        String tiempoFormateado = String.format("%02d:%02d", minutos, segundos);
        tiempoTranscurridoSeccion.setText("Tiempo transcurrido: " + tiempoFormateado);
        palabrasAcertadasSeccion.setText("Palabras acertadas: " + aciertosGuardados);
    }

    private void reinciarJuego() {
        try {
            Main.mostrarInicio();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
