package com.escriturarapida;

import com.escriturarapida.controllers.EndController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        mostrarInicio();
        primaryStage.setTitle("Escritura Rápida");
        primaryStage.show();
    }

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


    public static void main(String[] args) {
        launch(args);
    }
}
