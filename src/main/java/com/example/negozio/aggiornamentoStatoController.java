package com.example.negozio;

import application.Tecnico;
import application.Riparazione;
import command.pattern.*;
import database.RiparazioneDB;
import state.pattern.StatoInUscita;
import state.pattern.StatoSmartphone;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
public class aggiornamentoStatoController {

    @FXML
    public ResourceBundle resources;

    @FXML
    public URL location;

    @FXML
    public GridPane grid;

    @FXML
    public Button updateStatusButton;

    @FXML
    public Button backButton;

    @FXML
    public Label codRiparazione;

    @FXML
    public Label tipoRiparazione;

    @FXML
    public Label serialeSmartphone;

    public Riparazione selectedRiparazione = new Riparazione();

    public Tecnico selectedTecnico = new Tecnico();

    public StatoSmartphone stato = new StatoSmartphone();

    public StatoInUscita statouscita = new StatoInUscita();

    //function che va a selezionare i vari parametri
    public void myFunction(Riparazione riparazione, Tecnico tecnico) {

        selectedRiparazione = riparazione;
        selectedTecnico = tecnico;

        codRiparazione.setText(String.valueOf(selectedRiparazione.getCodRiparazione()));
        serialeSmartphone.setText(selectedRiparazione.getSerialeSmartphone());
        tipoRiparazione.setText(selectedRiparazione.getTipoRiparazione());
    }

    //Gestione backButton per tornare indietro
    @FXML
    public void handleBack(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("tabellaStato.fxml"));

            Parent root = (Parent) loader.load();

            tabellaStatoController adminTecnico = loader.getController();
            adminTecnico.myFunction(selectedTecnico);

            HelloApplication.getPrimaryStage().setScene(new Scene(root));
            HelloApplication.getPrimaryStage().show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Gestione updateStatusButton per aggiornare lo stato
    @FXML
    public void handleUpdate(ActionEvent event) {

        System.out.println(selectedRiparazione.getTipoRiparazione());
        stato.setStato(statouscita);
        selectedRiparazione.setStatoRiparazione(stato);

        RiparazioneDB.updateStatoRiparazione(selectedRiparazione.getCodRiparazione(),selectedRiparazione.getStatoRiparazione().currentStatus());

        invioNotificaRip();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminPanel.fxml"));

            Parent root = (Parent) loader.load();

            tabellaStatoController tecnico = loader.getController();
            tecnico.myFunction(selectedTecnico);

            HelloApplication.getPrimaryStage().setScene(new Scene(root));
            HelloApplication.getPrimaryStage().show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void invioNotificaRip() {
        String email = null;
        String cellulare = null;

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/negozio?user=root&password=");
            Statement statement = connection.createStatement();
            ResultSet result = null;

            String query1 = "SELECT EMAIL FROM RICEVUTA WHERE CODRIPARAZIONE = '"+codRiparazione.getText()+"';";
            result = statement.executeQuery(query1);

            if(result.next()){
                email = result.getString("EMAIL");

                String query2 = "SELECT CELLULARE FROM CLIENTE WHERE EMAIL = '"+email+"';";
                result = statement.executeQuery(query2);

                if (result.next()){
                    cellulare = result.getString("CELLULARE");
                    System.out.println(cellulare);
                    if (result.isBeforeFirst()) {
                        System.out.println("Cellulare non presente");
                    }
                }

                System.out.println(email);
                if(result.isBeforeFirst()){
                    System.out.println("Email non presente");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SendRecepit notifica = new SendRecepit();
        Sms SMS = new Sms();
        Email emailCliente = new Email();
        Command notificaSms = new smsCommand(SMS);
        Command notificaEmail = new emailCommand(emailCliente);

        // Invio SMS
        notifica.setCommand(notificaSms);
        notifica.send();


        // Invio Email
        notifica.setCommand(notificaEmail);
        notifica.send();

        // Alert conferma invio notifica
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("NOTIFICA INVIATA");
        alert.showAndWait();
    }
}
