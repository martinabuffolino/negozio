package com.example.negozio;

import database.DataBaseNegozioConnect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {

    private static Stage window;

    @Override
    public void start(Stage primaryStage) throws IOException {


        window = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("homeOverview.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        window.setTitle("MSM - Negozio Smartphone");
        window.setScene(scene);
        window.show();

    }

    public static Stage getPrimaryStage() {
        return window;
    }

    public static void main(String[] args) {
        launch();

        }
}