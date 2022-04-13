package com.example.negozio;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class twitterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="backButton"
    private Button backButton;

    @FXML // fx:id="confermaButton"
    private Button confermaButton;

    @FXML // fx:id="passwordTwitter"
    private PasswordField passwordTwitter;

    @FXML // fx:id="usernameTwitter"
    private TextField usernameTwitter;

    private Parent root;

    public twitterController() {

    }

    public void inizializzaTwitterController() {
        try {
            FXMLLoader twitterLoader = new FXMLLoader(HelloApplication.class.getResource("twitter.fxml"));
            VBox twitterOverview = (VBox) twitterLoader.load();

            HelloApplication.getPrimaryStage().setScene(new Scene(twitterOverview));
            HelloApplication.getPrimaryStage().show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Gestione backButton per tornare indietro
    @FXML
    void handleBackButton() throws IOException {
        root = FXMLLoader.load(HelloApplication.class.getResource("recensioneOverview.fxml"));
        HelloApplication.getPrimaryStage().setScene(new Scene(root));
    }

    public boolean enableLogin(){
        System.out.println("Login abilitato");
        if (passwordTwitter.getText().isEmpty() | usernameTwitter.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Attenzione tutti i campi sono obbligatori");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    //  Gestione login
    @FXML
    void handleLogin() throws IOException, SQLException  {
        root = FXMLLoader.load(HelloApplication.class.getResource("twitterMessaggio.fxml"));
        HelloApplication.getPrimaryStage().setScene(new Scene(root));
    }
}