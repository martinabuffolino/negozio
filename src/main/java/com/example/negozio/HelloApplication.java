package com.example.negozio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import database.DataBaseNegozioConnect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class HelloApplication extends Application {

    private static Stage window;

    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("homeOverview.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            /*Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/negozio", "root", "Programmazione.3");

            Statement stmt = connection.createStatement();*/

            DataBaseNegozioConnect.dbConnect();

            window.setTitle("MSM TECH - Negozio Smartphone");
            window.setScene(scene);
            window.show();

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public static Stage getPrimaryStage() {
        return window;
    }

    public static void main(String[] args) {
        launch();

        }
}