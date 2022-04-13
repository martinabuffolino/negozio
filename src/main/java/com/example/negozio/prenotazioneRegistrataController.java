package com.example.negozio;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import application.Ricevuta;
import database.ClienteDB;
import database.RicevutaDB;
import database.RiparazioneDB;
import database.SmartphoneDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class prenotazioneRegistrataController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="cancelButton"
    private Button cancelButton;

    @FXML // fx:id="homeButton"
    private Button homeButton;

    @FXML // fx:id="grid"
    private GridPane grid;

    @FXML // fx:id="costo"
    private Label costo;

    @FXML // fx:id="tipoRiparazione"
    private Label tipoRiparazione;

    @FXML // fx:id="codiceRicevuta"
    private Label codiceRicevuta;

    @FXML // fx:id="codiceRiparazione"
    private Label codiceRiparazione;

    @FXML
    Parent root;

    private String cellulareCliente;

    private Ricevuta ricevuta = new Ricevuta();

    public void myFunction(Ricevuta ricevutaGenerata, String numeroCliente){

        cellulareCliente = numeroCliente;
        ricevuta = ricevutaGenerata;

        codiceRicevuta.setText(String.valueOf(ricevuta.getCodRicevuta()));
        codiceRiparazione.setText(String.valueOf(ricevuta.getSostituzioneEffettuata().getCodRiparazione()));
        tipoRiparazione.setText(ricevuta.getSostituzioneEffettuata().getTipoRiparazione());
        costo.setText("€ " + ricevuta.getCostoRiparazione());
    }

    //  Gestione button fine per terminare l'operazione e ritornare alla schermata principale
    @FXML
    void handleEnd(ActionEvent event) throws IOException {

        root = FXMLLoader.load(HelloApplication.class.getResource("homeOverview.fxml"));
        HelloApplication.getPrimaryStage().setScene(new Scene(root));

    }

    //  Gestione button indietro per ritornare alla schermata principale ed eliminare i dati inseriti
    @FXML
    public void handleCancelButton(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        RicevutaDB.deleteRicevuta(Integer.parseInt(codiceRicevuta.getText()));
        RiparazioneDB.deleteRiparazione(Integer.parseInt(codiceRiparazione.getText()));
        SmartphoneDB.deleteSmartphone(ricevuta.getSostituzioneEffettuata().getSerialeSmartphone());
        ClienteDB.deleteCliente(ricevuta.getSmartphoneControllato().getCFCliente());

        root = FXMLLoader.load(HelloApplication.class.getResource("homeOverview.fxml"));
        HelloApplication.getPrimaryStage().setScene(new Scene(root));
    }
}
