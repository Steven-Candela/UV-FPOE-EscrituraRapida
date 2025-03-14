package com.escriturarapida;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/escriturarapida/assets/views/Game.fxml"));
        Scene escenaJuego = new Scene(loader.load(), 428, 409);
        primaryStage.setScene(escenaJuego);
    }

    public static void mostrarFin() throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("com/escriturarapida/assets/views/end.fxml"));
        Scene escenaFin = new Scene(loader.load(), 428, 409);
        primaryStage.setScene(escenaFin);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
