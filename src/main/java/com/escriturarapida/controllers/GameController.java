package com.escriturarapida.controllers;

import com.escriturarapida.Main;
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

    private int tiempoBase = 20;
    private int tiempoRestante = tiempoBase;
    private int vidas = 4;
    private long tiempoInicio;
    private int palabrasAcertadas = 0;
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
        palabrasAcertadas = 0; // Reinicia el contador
        tiempoInicio = System.currentTimeMillis(); // Guarda el tiempo en milisegundos
        actualizarCronometroSeccion();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (tiempoRestante <= 0) {
                timeline.stop();
                finJuego();
            }

            actualizarCronometroSeccion();
            tiempoRestante--;
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
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
                "Sol", "Mar", "Rio", "Pez", "Ave", "Luz", "Pan", "Sal", "Ola", "Fin",
                "Lago", "Nube", "Vino", "Casa", "Piso", "Rojo", "Loco", "Duro", "Lobo", "Miel",
                "Perro", "Gato", "Raton", "Arbol", "Nieve", "Joven", "Flaco", "Vuelo", "Limon", "Pasto",
                "Planta", "Cuerpo", "Dorado", "Espuma", "Ratito", "Puerta", "Nacion", "Ventil", "Nubeo", "Carros",
                "Sombra", "Laguna", "Cabina", "Cadena", "Barrer", "Cuchara", "Palabra", "Hermano", "Ventana", "Piedra",
                "Manzana", "Espejo", "Cortina", "Brillar", "Caminar", "Guitarra", "Estrella", "Relieve", "Montana", "Diamante",
                "Celular", "Bandera", "Balanza", "Magenta", "Pizarra", "Piragua", "Brujula", "Hormiga", "Bocina", "Jirafa",
                "Escuela", "Abogado", "Mensaje", "Insecto", "Paraiso", "Palmera", "Cascada", "Leonado", "Melodia", "Llamado",
                "Arcoiris", "Creacion", "Elefante", "Formato", "Castillo", "Caminata", "Velocidad", "Brillante", "Montañoso",
                "Crocante", "Misterio", "Esmerado", "Miradora", "Diametro", "Historia", "Radiante", "Silueta", "Tormenta", "Carruaje"
        };

        Random palabraRandom = new Random();
        return words[palabraRandom.nextInt(words.length)];
    }

    private void actualizarCronometroSeccion() {
        int minutos = tiempoRestante / 60;
        int segundos = tiempoRestante % 60;
        cronometroSeccion.setText(String.format("%02d:%02d", minutos, segundos));
        cronometroSeccion.setFont(Font.font("Alagard", 30));
    }

    private void actualizarPalabraSeccion() {
        palabraActual = cargarPalabras();
        palabraSeccion.setText(palabraActual);
    }

    private void cargarEntradaTexto() {
        escrituraSeccion.setOnAction(event -> verificarPalabra());
    }

    private void verificarPalabra() {
        String palabraIngresada = escrituraSeccion.getText().trim();

        if (palabraIngresada.equalsIgnoreCase(palabraActual)) {

            cambiarFondoImagen("/com/escriturarapida/assets/images/correct.png");

            palabrasAcertadas++;

            // Cada dos palabras correctas, reducir 2 segundos al tiempo restante
            if (palabrasAcertadas % 2 == 0 && tiempoBase > 2) {
                tiempoBase -= 2;
                tiempoRestante = tiempoBase;
                actualizarCronometroSeccion(); // Refrescar el tiempo en pantalla
            }
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
        } else {
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
        timeline.stop();
        long tiempoFinal = System.currentTimeMillis(); // Detener el tiempo
        long tiempoTranscurrido = (tiempoFinal - tiempoInicio) / 1000;

        try {
            new Main().mostrarFin(tiempoTranscurrido, palabrasAcertadas); // Crear instancia y mostrar pantalla final
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}