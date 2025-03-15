/**
 * Nombre del Proyecto: Escritura Rapida
 * Descripción: Mini Proyecto 1 - Universidad del Valle - FPOE
 * Codigo Estudiante: 2415014-2724
 * Correo: brayan.candela@correounivalle.edu.co
 *
 * @author Steven Candela
 * @version 1.0.0
 * @since 03/14/2025 20:17
 */

package com.escriturarapida;

import com.escriturarapida.controllers.EndController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal del juego
 * Controla las escenas del juego
 */
public class Main extends Application {
    private static Stage primaryStage;

    /**
     * Metodo de inicio de la aplicacion JavaFX
     *
     * @param stage El escenario principal de la aplicacion
     * @throws IOException Si ocurre un error al cargar el archivo FXML
     */
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        mostrarInicio();
        primaryStage.setTitle("Escritura Rápida");
        primaryStage.show();
    }

    /**
     * Muestra la pantalla de inicio
     *
     * @throws IOException Si ocurre un error al cargar el archivo FXML
     */
    public static void mostrarInicio() throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/escriturarapida/assets/views/start.fxml"));
        Scene escenaInicio = new Scene(loader.load(), 428, 409);
        primaryStage.setScene(escenaInicio);
    }

    public static void mostrarJuego() throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/escriturarapida/assets/views/game.fxml"));
        Scene escenaJuego = new Scene(loader.load(), 428, 409);
        primaryStage.setScene(escenaJuego);
    }

    /**
     * Cambia la escena actual a la pantalla de Game Over
     *
     * @throws IOException Si ocurre un error al cargar el archivo FXML
     * @param tiempo Variable acumuladora de tiempo transcurrido en milisegundos
     * @param aciertos Variable acumuladora de palabras acertadas
     */
    public static void mostrarFin(long tiempo, int aciertos) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/escriturarapida/assets/views/end.fxml"));
            Parent root = loader.load();

            EndController finController = loader.getController();
            finController.mostrarResultados(tiempo, aciertos);

            Scene escenaFin = new Scene(root, 428, 409);
            primaryStage.setScene(escenaFin);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo principal que lanza la aplicacion
     *
     * @param args Argumentos de la linea de comandos
     */
    public static void main(String[] args) {
        launch(args);
    }
}
