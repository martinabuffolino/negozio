package com.example.negozio;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class homeOverviewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="nuovaRiparazioneRadioButton"
    private RadioButton nuovaRiparazioneRadioButton;

    @FXML // fx:id="controlloRiparazioneRadioButton"
    private RadioButton controlloRiparazioneRadioButton;

    @FXML // fx:id="acquistoRadioButton"
    private RadioButton acquistoRadioButton;

    @FXML // fx:id="tecnicoRadioButton"
    private RadioButton tecnicoRadioButton;

    @FXML // fx:id="sostieniciButton"
    private Button sostieniciButton;

    @FXML // fx:id="modalitaAccesso"
    private ToggleGroup modalitaAccesso;

    @FXML // fx:id="avantiButton"
    private Button avantiButton;

    @FXML
    Parent root;

    public homeOverviewController() {

    }


    @FXML
    void handleSostienici(ActionEvent event) throws IOException {

        root = FXMLLoader.load(HelloApplication.class.getResource("recensioneOverview.fxml"));
        HelloApplication.getPrimaryStage().setScene(new Scene(root));
    }


    @FXML
    void handleAvanti(ActionEvent event) {

        if(nuovaRiparazioneRadioButton.isSelected()) {
            try {
            System.out.println("Crea Nuova Riprazione");
            root = FXMLLoader.load(HelloApplication.class.getResource("nuovaRiparazione.fxml"));
            HelloApplication.getPrimaryStage().setScene(new Scene(root));

            nuovaRiparazioneController riparazione = new nuovaRiparazioneController();
            riparazione.inizializzaNuovaRiparazione();
            HelloApplication.getPrimaryStage().setScene(new Scene(root));
            HelloApplication.getPrimaryStage().show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(controlloRiparazioneRadioButton.isSelected()) {
            System.out.println("Controlla Stato Riparazione");
            controlloStatoRiparazioneController controlloRiparazione = new controlloStatoRiparazioneController();
            controlloRiparazione.inizializzaControlloStatoRiparazione();

        }

        if(acquistoRadioButton.isSelected()) {
            System.out.println("Catalogo Accessori");
            catalogoOverviewController accessori = new catalogoOverviewController();
            accessori.inizializzaCatalogoOverview();
        }

        if(tecnicoRadioButton.isSelected()) {
            System.out.println("Login Tecnico");
            tecnicoOverviewController tecnico = new tecnicoOverviewController();
            tecnico.inizializzaTecnicoOverviwe();
        }
    }
}