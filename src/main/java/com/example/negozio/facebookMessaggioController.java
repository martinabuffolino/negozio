package com.example.negozio;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class facebookMessaggioController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="backButton"
    private Button backButton;

    @FXML // fx:id="confermaButton"
    private Button confermaButton;

    @FXML // fx:id="commentText"
    private TextField commentText;

    Parent root;

    public facebookMessaggioController() {

    }

    // Gestione backButton per ritornare alla schermata principale
    @FXML
    void handleBackButton() throws IOException {
        root = FXMLLoader.load(HelloApplication.class.getResource("homeOverview.fxml"));
        HelloApplication.getPrimaryStage().setScene(new Scene(root));
    }

    // Gestione confermaButton verifica se il campo commentText è pieno
    @FXML
    void handleInvia(ActionEvent event) {
        if( commentText.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setContentText("Attenzione il campo è obbligatorio");
            alert.showAndWait();
        }
    }
}

