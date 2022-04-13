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

public class facebookController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="backButton"
    private Button backButton;

    @FXML // fx:id="confermaButton"
    private Button confermaButton;

    @FXML // fx:id="passwordFacebook"
    private PasswordField passwordFacebook;

    @FXML // fx:id="usernameFacebook"
    private TextField usernameFacebook;

    private Parent root;

    public facebookController() {

    }

    public void inizializzaFacebookController() {
        try {
            FXMLLoader facebookLoader = new FXMLLoader(HelloApplication.class.getResource("facebook.fxml"));
            VBox facebookOverview = (VBox) facebookLoader.load();

            HelloApplication.getPrimaryStage().setScene(new Scene(facebookOverview));
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

    // Gestione controllo credenziali
    public boolean enableLogin(){
        System.out.println("Login abilitato");
        if (passwordFacebook.getText().isEmpty() | usernameFacebook.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setContentText("Attenzione tutti i campi sono obbligatori");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    //  Gestione login
    @FXML
    void handleLogin() throws IOException, SQLException {

        root = FXMLLoader.load(HelloApplication.class.getResource("facebookMessaggio.fxml"));
        HelloApplication.getPrimaryStage().setScene(new Scene(root));

    }
}