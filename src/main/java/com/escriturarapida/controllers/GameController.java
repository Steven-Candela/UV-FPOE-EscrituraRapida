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

/**
 * Controlador del juego
 * Maneja la logica del juego, el tiempo, verifica las palabras que
 * se escribio, y gestiona las vidas del jugador
 */
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

    /**
     * Metodo de inicializacion de la escena "game"
     * Carga los recursos y elementos necesarios para
     * que funcione y tenga buen diseño
     */
    @FXML
    public void initialize() {
        iniciarJuego();
        cargarFuente();
        cargarEntradaTexto();
        actualizarVidasImagen();
    }

    /**
     * Inicia el juego estableciendo la palabra inicial y el tiempo restante
     */
    private void iniciarJuego() {
        actualizarPalabraSeccion();
        palabrasAcertadas = 0;
        tiempoInicio = System.currentTimeMillis();
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

    /**
     * Carga la fuente personalizada del juego
     */
    private void cargarFuente() {
        String fontPath = "/com/escriturarapida/assets/fonts/alagard.ttf";
        InputStream fontStream = getClass().getResourceAsStream(fontPath);

        Font alagardFont = Font.loadFont(fontStream, 30);
        cronometroSeccion.setFont(alagardFont);
    }

    /**
     * Genera una palabra aleatoria de la lista de palabras del juego
     * En la lista solo hay palabras de 3, 4, 5 y 6 caracteres
     *
     * @return Una palabra seleccionada aleatoriamente.
     */
    public static String cargarPalabras() {

        String[] words = {
                "Sol", "Mar", "Rio", "Pez", "Ave", "Luz", "Pan", "Sal", "Ola", "Fin",
                "Lago", "Nube", "Vino", "Casa", "Piso", "Rojo", "Loco", "Duro", "Lobo", "Miel",
                "Perro", "Gato", "Raton", "Arbol", "Nieve", "Joven", "Flaco", "Vuelo", "Limon", "Pasto",
                "Planta", "Cuerpo", "Dorado", "Espuma", "Ratito", "Puerta", "Nacion", "Ventil", "Nubeo", "Carros",
                "Sombra", "Laguna", "Cabina", "Cadena", "Barrer", "Cuchara", "Palabra", "Hermano", "Ventana", "Piedra",
                "Manzana", "Espejo", "Cortina", "Brillar", "Caminar", "Guitarra", "Estrella", "Relieve", "Montaña", "Diamante",
                "Celular", "Bandera", "Balanza", "Magenta", "Pizarra", "Piragua", "Brujula", "Hormiga", "Bocina", "Jirafa",
                "Escuela", "Abogado", "Mensaje", "Insecto", "Paraiso", "Palmera", "Cascada", "Leonado", "Melodia", "Llamado",
                "Arcoiris", "Creacion", "Elefante", "Formato", "Castillo", "Caminata", "Velocidad", "Brillante", "Montañoso",
                "Crocante", "Misterio", "Esmerado", "Miradora", "Diametro", "Historia", "Radiante", "Silueta", "Tormenta", "Carruaje"
        };

        Random palabraRandom = new Random();
        return words[palabraRandom.nextInt(words.length)];
    }

    /**
     * Actualiza el cronometro que se ve en la interfaz
     */
    private void actualizarCronometroSeccion() {
        int minutos = tiempoRestante / 60;
        int segundos = tiempoRestante % 60;
        cronometroSeccion.setText(String.format("%02d:%02d", minutos, segundos));
        cronometroSeccion.setFont(Font.font("Alagard", 30));
    }

    /**
     * Actualiza la palabra mostrada en la interfaz con una nueva palabra aleatoria
     */
    private void actualizarPalabraSeccion() {
        palabraActual = cargarPalabras();
        palabraSeccion.setText(palabraActual);
    }

    /**
     *  Establece el caso de que cuando se presiona <<Enter>> verifica
     *  la palabra que está ingresada en el TextField "escrituraSeccion"
     */
    private void cargarEntradaTexto() {
        escrituraSeccion.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER -> verificarPalabra();
            }
        });
    }

    /**
     * Verifica si la palabra ingresada por el usuario es correcta
     * Notifica al usuario si su respuesta es correcta o incorrecta
     * y dependiendo del caso, reduce las vidas o el tiempo restante
     */
    private void verificarPalabra() {
        String palabraIngresada = escrituraSeccion.getText().trim();

        if (palabraIngresada.equalsIgnoreCase(palabraActual)) {

            cambiarFondoImagen("/com/escriturarapida/assets/images/correct.png");

            palabrasAcertadas++;

            // Cada dos palabras correctas, reducir 2 segundos al tiempo restante
            if (palabrasAcertadas % 2 == 0 && tiempoBase > 2) {
                tiempoBase -= 2;
                tiempoRestante = tiempoBase;
                actualizarCronometroSeccion();
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

    /**
     * Reduce una vida al jugador y verifica si aun le queda mas
     * para continuar
     */
    private void perderVida() {
        vidas--;

        if (vidas <= 0) {
            finJuego();
        } else {
            actualizarVidasImagen();
        }
    }

    /**
     * Actualiza la imagen del eclipse para mostrar las vidas que tiene al jugador
     */
    private void actualizarVidasImagen() {
        String rutaImagen = String.format("/com/escriturarapida/assets/images/vidas_%d.png", vidas);
        cambiarVidasImagen(rutaImagen);
    }

    /**
     * Reduce codigo al momento de verificar la palabra y avisar por medio
     * de imagenes si la respuesta del jugador fue correcta o no
     *
     * @param rutaImagen Ruta de la nueva imagen de fondo
     */
    private void cambiarFondoImagen(String rutaImagen) {
        Image nuevaImagen = new Image(Objects.requireNonNull(getClass().getResource(rutaImagen)).toExternalForm());
        fondoImagen.setImage(nuevaImagen);
    }

    /**
     * Cambia la imagen del eclipse
     *
     * @param rutaImagen Ruta de la nueva imagen de vidas
     */
    private void cambiarVidasImagen(String rutaImagen) {
        Image nuevaImagen = new Image(Objects.requireNonNull(getClass().getResource(rutaImagen)).toExternalForm());
        vidasImagen.setImage(nuevaImagen);
    }

    /**
     * Finaliza el juego, detiene el cronómetro y muestra la pantalla de resultados
     */
    private void finJuego() {
        timeline.stop();
        long tiempoFinal = System.currentTimeMillis();
        long tiempoTranscurrido = (tiempoFinal - tiempoInicio) / 1000;

        try {
            new Main().mostrarFin(tiempoTranscurrido, palabrasAcertadas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}