package com.example.negozio;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import application.Cliente;
import application.Smartphone;
import database.ClienteDB;
import database.SmartphoneDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class registrazioneSmartphoneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="marca"
    private TextField marca;

    @FXML // fx:id="backButton"
    private Button backButton;

    @FXML // fx:id="avantiButton"
    private Button avantiButton;

    @FXML // fx:id="colore"
    private TextField colore;

    @FXML // fx:id="seriale"
    private TextField seriale;

    @FXML // fx:id="modello"
    private TextField modello;

    public Cliente clienteSmartphone;

    public Smartphone smartphone = new Smartphone();

    @FXML
    private Parent root;

    public registrazioneSmartphoneController (){

    }

    public void myFunction(Cliente propSmartphone){
        clienteSmartphone = propSmartphone;
        smartphone.setCliente(clienteSmartphone.getNome(), clienteSmartphone.getCognome(), clienteSmartphone.getCF());
    }

    //  Gestione button per ritornare alla schermata principale
    @FXML
    void handleBackButton(ActionEvent actionEvent) throws IOException {

        if(validateFields()) {
            try {
                SmartphoneDB.deleteSmartphone(smartphone.getSeriale());
                ClienteDB.deleteCliente(clienteSmartphone.getCF());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        root = FXMLLoader.load(HelloApplication.class.getResource("homeOverview.fxml"));
        HelloApplication.getPrimaryStage().setScene(new Scene(root));

    }

    // Gestione per registrare i dati del cliente
    @FXML
    public void handleRegistra(ActionEvent actionEvent) {

        if (validateFields()) {
            avantiButton.setDisable(false);

            System.out.println("Codice fiscale cliente: " + smartphone.getCFCliente());
            smartphone.setSmartphone(seriale.getText(), marca.getText(), modello.getText(), colore.getText());

            try {
                System.out.println("Registro lo smarphone nel DB");
                smartphone.getSmartphone();
                SmartphoneDB.insertSmartphone(smartphone.getSeriale(), smartphone.getMarca(), smartphone.getModello(), smartphone.getColore(), smartphone.getCFCliente());
                System.out.println("Smartphone registrato");

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("selezionaRiparazione.fxml"));

                    Parent root = (Parent) loader.load();

                    selezionaRiparazioneController ripara = loader.getController();
                    ripara.inizializzaSelezionaRiparazione(smartphone, clienteSmartphone);

                    HelloApplication.getPrimaryStage().setScene(new Scene(root));
                    HelloApplication.getPrimaryStage().show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                System.out.println("Errore inserimento smartphone nel database");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("Errore inserimento smartphone nel database");
                e.printStackTrace();
            }
        }
    }

    // Gestione controllo parametri inseriti
    private boolean validateFields() {

        if( seriale.getText().isEmpty() | marca.getText().isEmpty() | modello.getText().isEmpty() | colore.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setContentText("Attenzione, tutti i campi sono obbligatori");
            alert.showAndWait();
            return false;
        } else if (seriale.getText().length() >8 | seriale.getText().length() <8) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setContentText("Controlla seriale");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
