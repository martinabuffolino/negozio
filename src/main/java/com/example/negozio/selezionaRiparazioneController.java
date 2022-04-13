package com.example.negozio;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import application.Cliente;
import application.Ricevuta;
import application.Riparazione;
import application.Smartphone;
import application.Tecnico;
import database.DataBaseNegozioConnect;
import database.RicevutaDB;
import database.RiparazioneDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import state.pattern.StatoInCorso;
import state.pattern.StatoSmartphone;

public class selezionaRiparazioneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="radioSchermo"
    private RadioButton radioSchermo;

    @FXML // fx:id="radioBatteria"
    private RadioButton radioBatteria;

    @FXML // fx:id="radioFotocamera"
    private RadioButton radioFotocamera;

    @FXML // fx:id="radioTouchId"
    private RadioButton radioTouchId;

    @FXML // fx:id="radioJackRicarica"
    private RadioButton radioJackRicarica;

    @FXML // fx:id="radioJackCuffie"
    private RadioButton radioJackCuffie;


    @FXML // fx:id="radioVibrazione"
    private RadioButton radioVibrazione;

    @FXML // fx:id="backButton"
    private Button backButton;

    @FXML // fx:id="avantiButton"
    private Button avantiButton;

    private ToggleGroup rbGroup = new ToggleGroup();

    private Cliente cliente = new Cliente();

    private Smartphone smartphoneDaRiparare = new Smartphone();

    private Riparazione riparazione = new Riparazione();

    private Tecnico tecnico = new Tecnico();

    private Ricevuta ricevuta = new Ricevuta();

    private StatoSmartphone stato = new StatoSmartphone();

    Random rand = new Random();

    Parent root;

    public  selezionaRiparazioneController(){

    }

    public void inizializzaSelezionaRiparazione(Smartphone smartphone, Cliente clienteSmartphone){

        cliente = clienteSmartphone;
        smartphoneDaRiparare = smartphone;

        riparazione.setSerialeSmartphone(smartphoneDaRiparare.getSeriale());
        StatoInCorso corso = new StatoInCorso();
        stato.setStato(corso);
        riparazione.setStatoRiparazione(stato);

    }

    @FXML
    void handleTipoRiparazione(ActionEvent actionEvent) {

        int casuale;

        radioSchermo.setToggleGroup(rbGroup);
        radioBatteria.setToggleGroup(rbGroup);
        radioFotocamera.setToggleGroup(rbGroup);
        radioTouchId.setToggleGroup(rbGroup);
        radioJackRicarica.setToggleGroup(rbGroup);
        radioJackCuffie.setToggleGroup(rbGroup);
        radioVibrazione.setToggleGroup(rbGroup);

        if (radioSchermo.isSelected()) {
            riparazione.setTipoRiparazione("Schermo");
            avantiButton.setDisable(false);

            //Genera prezzo per la ricevuta
            int randomPrice;
            if(checkSmartphoneMarca()){
                randomPrice = getRandomNumberInRange(150,300);
            } else {
                randomPrice = getRandomNumberInRange(50,100);
            }

            ricevuta.setCostoRiparazione(randomPrice);
        }

        if (radioBatteria.isSelected()) {
            riparazione.setTipoRiparazione("Batteria");
            avantiButton.setDisable(false);

            //Genera prezzo per la ricevuta
            int randomPrice;
            if(checkSmartphoneMarca()){
                randomPrice = getRandomNumberInRange(150,250);
            } else {
                randomPrice = getRandomNumberInRange(100,200);
            }
            ricevuta.setCostoRiparazione(randomPrice);
        }

        if (radioFotocamera.isSelected()){
            riparazione.setTipoRiparazione("Fotocamera");
            avantiButton.setDisable(false);

            //Genera prezzo per la ricevuta
            int randomPrice;
            if(checkSmartphoneMarca()){
                randomPrice = getRandomNumberInRange(70,100);
            } else {
                randomPrice = getRandomNumberInRange(10,60);
            }
            ricevuta.setCostoRiparazione(randomPrice);
        }


        if (radioTouchId.isSelected()){
            riparazione.setTipoRiparazione("Touch Id");
            avantiButton.setDisable(false);

            //Genera prezzo per la ricevuta
            int randomPrice;
            if(checkSmartphoneMarca()){
                randomPrice = getRandomNumberInRange(50,70);
            } else {
                randomPrice = getRandomNumberInRange(10,40);
            }

            ricevuta.setCostoRiparazione(randomPrice);
        }

        if (radioJackRicarica.isSelected()){
            riparazione.setTipoRiparazione("Jack Ricarica");
            avantiButton.setDisable(false);

            //Genera prezzo per la ricevuta
            int randomPrice;
            if(checkSmartphoneMarca()){
                randomPrice = getRandomNumberInRange(20,30);
            } else {
                randomPrice = getRandomNumberInRange(5,10);
            }
            ricevuta.setCostoRiparazione(randomPrice);
        }

        if (radioJackCuffie.isSelected()){
            riparazione.setTipoRiparazione("Jack Cuffie");
            avantiButton.setDisable(false);

            //Genera prezzo per la ricevuta
            int randomPrice;
            if(checkSmartphoneMarca()){
                randomPrice = getRandomNumberInRange(20,30);
            } else {
                randomPrice = getRandomNumberInRange(5,10);
            }
            ricevuta.setCostoRiparazione(randomPrice);
        }

        if (radioVibrazione.isSelected()){
            riparazione.setTipoRiparazione("Vibrazione");
            avantiButton.setDisable(false);

            //Genera prezzo per la ricevuta
            int randomPrice;
            if(checkSmartphoneMarca()){
                randomPrice = getRandomNumberInRange(50,100);
            } else {
                randomPrice = getRandomNumberInRange(30,49);
            }
            ricevuta.setCostoRiparazione(randomPrice);
        }
    }

    private static int getRandomNumberInRange(int min, int max) {

        Random r = new Random();
        return r.ints(min, (max + 1)).findFirst().getAsInt();
    }

    private boolean checkSmartphoneMarca() {

        String marca = smartphoneDaRiparare.getMarca();
        if ((marca.equals("Iphone")) | marca.equals("Samsung") ){

            System.out.println("Marca: "+ marca);

            return true;
        }
        return false;
    }

    //  Gestione nextButton per registrare i dati inseriti finora
    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {

        root = FXMLLoader.load(HelloApplication.class.getResource("homeOverview.fxml"));
        HelloApplication.getPrimaryStage().setScene(new Scene(root));

    }

    //  Gestione avantiButton per registrare i dati inseriti finora
    @FXML
    public void handleAvanti(ActionEvent event) throws SQLException {

        System.out.println("Tipo Riparazione: " + riparazione.getTipoRiparazione());
        System.out.println("Codice Riparazione: " + riparazione.getCodRiparazione());
        System.out.println("Tecnico: " + tecnico.getCodice());
        stato.currentStatus();

        System.out.println(riparazione.getStatoRiparazione().toString());

        try {

            RiparazioneDB.insertRiparazione(riparazione.getTipoRiparazione(), riparazione.getStatoRiparazione().currentStatus(),smartphoneDaRiparare.getSeriale(),tecnico.getCodice());

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        } finally {

            RiparazioneDB.selectCodRiparazione(smartphoneDaRiparare.getSeriale());
            int risultatoQuery = DataBaseNegozioConnect.risultatoQuery;
            riparazione.setCodRiparazione(risultatoQuery);

            creaRicevuta();
        }
    }

    // Gestione per creare la ricevuta
    private void creaRicevuta() {

        ricevuta.setSmartphoneControllato(smartphoneDaRiparare);
        ricevuta.setTecnicoSostituzione(tecnico);
        ricevuta.setSostituzioneEffettuata(riparazione);
        ricevuta.setEmailCliente(cliente.getEmail());

        System.out.println(ricevuta.getSmartphoneControllato());
        System.out.println(ricevuta.getTecnicoSostituzione());
        System.out.println(ricevuta.getSostituzioneEffettuata());
        System.out.println(ricevuta.getCostoRiparazione());

        try {
            RicevutaDB.insertRicevuta((int) ricevuta.getCostoRiparazione(), riparazione.getCodRiparazione(), smartphoneDaRiparare.getSeriale(), tecnico.getCodice(), ricevuta.getEmailCliente());
            System.out.println("Ricevuta inserita nel database");
            RicevutaDB.selectCodRicevuta(smartphoneDaRiparare.getSeriale());
            int codiceRicevuta = DataBaseNegozioConnect.risultatoQuery;
            ricevuta.setCodRicevuta(codiceRicevuta);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("riepilogoPrenotazione.fxml"));

                Parent root = (Parent) loader.load();

                prenotazioneRegistrataController riepilogo = loader.getController();
                riepilogo.myFunction(ricevuta,cliente.getCellulare());

                HelloApplication.getPrimaryStage().setScene(new Scene(root));
                HelloApplication.getPrimaryStage().show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
}
