package com.example.negozio;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import factory.pattern.Factory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class pagamentoOverviewController {

    Factory metodoScelto;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="radioPayPal"
    private RadioButton radioPayPal;

    @FXML // fx:id="radioCarta"
    private RadioButton radioCarta;

    @FXML // fx:id="backButton"
    private Button backButton;

    @FXML // fx:id="avantiButton"
    private Button avantiButton;

    private ToggleGroup rbGroup = new ToggleGroup();

    @FXML
    Parent root;

    private double importoDaPagare;
    private String codRicevutaDaPagare;


    public void initPagamento(double prezzo, String codRicevuta){
        importoDaPagare = prezzo;
        codRicevutaDaPagare = codRicevuta;
        radioPayPal.setToggleGroup(rbGroup);
        radioCarta.setToggleGroup(rbGroup);
    }

    @FXML
    public void handleTipoPagamento() {

        if(radioPayPal.isSelected()){
            avantiButton.setDisable(false);
        }
        if(radioCarta.isSelected()){
            avantiButton.setDisable(false);
        }

    }

    // Gestione backButton per tornare indietro
    @FXML
    public void handleBackButton(ActionEvent actionEvent)  throws IOException {

        root = FXMLLoader.load(HelloApplication.class.getResource("controlloStatoRiparazione.fxml"));
        HelloApplication.getPrimaryStage().setScene(new Scene(root));

    }

    // Gestione metodo paga in base alla scelta del cliente
    @FXML
    public void handlePaga(ActionEvent actionEvent) {

        if(radioPayPal.isSelected()){

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("pagaPayPal.fxml"));
                Parent root = (Parent) loader.load();

                pagaPayPalController paypal = loader.getController();
                paypal.initPayPal(importoDaPagare,codRicevutaDaPagare);

                HelloApplication.getPrimaryStage().setScene(new Scene(root));
                HelloApplication.getPrimaryStage().show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(radioCarta.isSelected()){

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("pagaCarta.fxml"));
                Parent root = (Parent) loader.load();

                pagaCartaController carta = loader.getController();
                carta.initCartaPaga(importoDaPagare,codRicevutaDaPagare);

                HelloApplication.getPrimaryStage().setScene(new Scene(root));
                HelloApplication.getPrimaryStage().show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
