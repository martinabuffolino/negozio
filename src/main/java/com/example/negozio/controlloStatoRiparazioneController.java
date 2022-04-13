package com.example.negozio;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import application.Cliente;
import database.DataBaseNegozioConnect;
import database.RicevutaDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class controlloStatoRiparazioneController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="emailCliente"
    private TextField emailCliente;

    @FXML // fx:id="passwordCliente"
    private PasswordField passwordCliente;

    @FXML // fx:id="codRicevuta"
    private TextField codRicevuta;

    @FXML // fx:id="backButton"
    private Button backButton;

    @FXML // fx:id="avantiButton"
    private Button avantiButton;

    @FXML
    private Parent root;

    Cliente cliente = new Cliente();

    public controlloStatoRiparazioneController () {

    }

    @FXML
    private void initialize() {
    }

    @FXML
    public void inizializzaControlloStatoRiparazione() {

        try {
            FXMLLoader clienteLoader = new FXMLLoader(HelloApplication.class.getResource("controlloStatoRiparazione.fxml"));
            VBox clienteOverview = (VBox) clienteLoader.load();

            HelloApplication.getPrimaryStage().setScene(new Scene(clienteOverview));
            HelloApplication.getPrimaryStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleBackButton(ActionEvent event) throws IOException {

        root = FXMLLoader.load(HelloApplication.class.getResource("homeOverview.fxml"));
        HelloApplication.getPrimaryStage().setScene(new Scene(root));

    }

    @FXML
    void handleAvanti() throws Exception {

        if (!codRicevuta.getText().isEmpty()) {

            if(controlloStatoRiparazione()) {
                try {

                    RicevutaDB.selectRicevuta(codRicevuta.getText(),emailCliente.getText());

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (DataBaseNegozioConnect.risultatoQuery != 0) {
                        //carica la ricevuta con lo stato del veicolo
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("dettaglioRicevuta.fxml"));
                            Parent root = (Parent) loader.load();

                            infoRicevutaController infoRicevuta = loader.getController();
                            infoRicevuta.initRicevuta(Integer.parseInt(codRicevuta.getText()));

                            HelloApplication.getPrimaryStage().setScene(new Scene(root));
                            HelloApplication.getPrimaryStage().show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        //carica pop up errore
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Codice ricevuta errato");
                        alert.showAndWait();
                    }
                }
            }
        } else {
            System.out.println("Inserisci codice ricevuta");
        }
    }

    private boolean controlloStatoRiparazione() throws Exception{
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/negozio?user=root&password=");
            Statement statement = connection.createStatement();
            ResultSet result = null;

            String query = "SELECT PASSWORD FROM CLIENTE WHERE EMAIL = '"+emailCliente.getText()+"';";
            result = statement.executeQuery(query);

            if(result.next()){
                String passwordEmail = result.getString("PASSWORD");
                if(passwordCliente.getText().equals(passwordEmail)) {
                    System.out.println("Password esatta");

                    String newquery = "SELECT EMAIL FROM RICEVUTA WHERE CODRICEVUTA = " + codRicevuta.getText();
                    result = statement.executeQuery(newquery);

                    if(result.next()) {
                        String emailRisultante = result.getString("EMAIL");
                        if (result.isBeforeFirst()) {
                            System.out.println("No Data Found"); //data not exist
                            return false;
                        }
                    }
                } else {
                    System.out.println("Password Errata");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERRORE");
                    alert.setTitle("Password Errata");
                    alert.showAndWait();
                    return false;
                }
            } else {
                System.out.println("Email Errata");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERRORE");
                alert.setTitle("Email Errata");
                alert.showAndWait();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
