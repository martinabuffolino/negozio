package com.example.negozio;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import strategy.pattern.Accessori;

/*
   Controller dell'interfaccia grafica "carrello.fxml", che permette di gestire
   la lista di prodotti selezionati
 */

public class carrelloController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="tableView"
    private TableView<Accessori> tableView;

    @FXML // fx:id="colCodice"
    private TableColumn<Accessori, Integer> colCodice;

    @FXML // fx:id="colNome"
    private TableColumn<Accessori, String> colNome;

    @FXML // fx:id="colModello"
    private TableColumn<Accessori, String> colModello;

    @FXML // fx:id="colPrezzo"
    private TableColumn<Accessori, Double> colPrezzo;

    @FXML // fx:id="rimuoviButton"
    private Button rimuoviButton;

    @FXML // fx:id="backButton"
    private Button backButton;

    @FXML // fx:id="avantiButton"
    private Button avantiButton;

    @FXML // fx:id="label_selezione"
    private Label label_selezione;

    @FXML
    private ObservableList<Accessori> data;

    @FXML
    private ObservableList<Accessori> selected;

    public carrelloController(ObservableList<Accessori> selected) {
    }

    public void carrelloController(ObservableList<Accessori> x)
    {
        data = x;
    }

    @FXML
    public void initialize() {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        colCodice.setCellValueFactory(new PropertyValueFactory<Accessori,Integer>("codice"));
        colNome.setCellValueFactory(new PropertyValueFactory<Accessori,String>("nome"));
        colModello.setCellValueFactory(new PropertyValueFactory<Accessori,String>("modello"));
        colPrezzo.setCellValueFactory(new PropertyValueFactory<Accessori,Double>("prezzo"));
        selected = FXCollections.observableArrayList();
        tableView.setItems(data);
    }

    //Metodo che richiama l'interfaccia grafica "catalogoOverview.fxml"
    @FXML
    public void handleBackButton(ActionEvent event) throws Exception{
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("catalogoOverview.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.setResizable(false);
        window.show();
    }


    //Metodo che rimuove elementi dal carrello
    @FXML
    public void handleRemove(ActionEvent event) {
        selected.addAll(tableView.getSelectionModel().getSelectedItems());
        data.removeAll(selected);
        tableView.setItems(data);
    }

    //Metodo che richiama l'interfaccia grafica "Paga.xml"
    @FXML
    public void handleAvanti(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("pagamentoCarrello.fxml"));
        pagamentoCarrelloController instance = new pagamentoCarrelloController(data);
        loader.setController(instance);
        Parent tableView = loader.load();
        Scene tableViewScene = new Scene(tableView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.setResizable(false);
        window.show();
    }
}
