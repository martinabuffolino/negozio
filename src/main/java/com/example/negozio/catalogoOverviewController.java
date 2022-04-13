package com.example.negozio;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import database.DataBaseNegozioConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import strategy.pattern.Accessori;
import strategy.pattern.TabellaCategoria;
import strategy.pattern.TabellaMarca;
import strategy.pattern.TipoTabella;

public class catalogoOverviewController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="checkCategoria"
    private CheckBox checkCateg;

    @FXML // fx:id="checkMarca"
    private CheckBox checkMarca;

    @FXML // fx:id="choiceCateg"
    private ChoiceBox<String> choiceCateg;

    @FXML // fx:id="choiceMarca"
    private ChoiceBox<String> choiceMarca;

    @FXML // fx:id="tableView"
    private TableView<Accessori> tableView;

    @FXML // fx:id="colCodice"
    private TableColumn<Accessori,Integer> colCodice;

    @FXML // fx:id="colNome"
    private TableColumn<Accessori,String> colNome;

    @FXML // fx:id="colModello"
    private TableColumn<Accessori,String> colModello;

    @FXML // fx:id="colPrezzo"
    private TableColumn<Accessori,Double> colPrezzo;

    @FXML // fx:id="labelSceltaCatalogo"
    private Label labelSceltaCatalogo;

    @FXML // fx:id="labelSceltaMarca"
    private Label labelSceltaMarca;

    @FXML // fx:id="labelSceltaCategoria"
    private Label labelSceltaCategoria;

    @FXML // fx:id="labelSelezione"
    private Label labelSelezione;

    @FXML // fx:id="mostraButton"
    private Button mostraButton;

    @FXML // fx:id="buttonAdd"
    private Button buttonAdd;

    @FXML // fx:id="backButton"
    private Button backButton;

    @FXML // fx:id="avantiButton"
    private Button avantiButton;

    @FXML // fx:id="quantita"
    private TextField quantita;

    @FXML
    private ObservableList<Accessori> data;
    @FXML
    private ObservableList<Accessori> selected;

    /*
     * Inizializza tutte le variabili della classe
     */
    void inizializzaCatalogoOverview() {
        try
        {
            tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            colCodice.setCellValueFactory(new PropertyValueFactory<Accessori,Integer>("codice"));
            colNome.setCellValueFactory(new PropertyValueFactory<Accessori,String>("nome"));
            colModello.setCellValueFactory(new PropertyValueFactory<Accessori,String>("modello"));
            colPrezzo.setCellValueFactory(new PropertyValueFactory<Accessori,Double>("prezzo"));
            data = FXCollections.observableArrayList();
            selected = FXCollections.observableArrayList();

            //Riempio la choicebox marca
            String sql="SELECT DISTINCT MARCA from ACCESSORI";
            ResultSet rs=DataBaseNegozioConnect.dbExecute(sql);
            while(rs.next())
                choiceMarca.getItems().add(rs.getString(1));

            //Riempio la choice
            sql = "SELECT DISTINCT CATEG FROM ACCESS_CATEG";
            rs = DataBaseNegozioConnect.dbExecute(sql);
            while(rs.next())
                choiceCateg.getItems().add(rs.getString(1));
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    //  Gestione checkbox
    @FXML
    void handleCheck(ActionEvent event) throws Exception
    {
        if(checkCateg.isSelected())
        {
            if(checkMarca.isSelected())
            {
                checkCateg.setSelected(false);
                checkMarca.setSelected(false);
                labelSceltaCategoria.setVisible(false);
                choiceCateg.setVisible(false);
                labelSceltaMarca.setVisible(false);
                choiceMarca.setVisible(false);
                mostraButton.setVisible(false);
                tableView.setVisible(false);
                quantita.setVisible(false);
                buttonAdd.setVisible(false);
                labelSelezione.setVisible(false);
                avantiButton.setVisible(false);
                selected.clear();

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Devi selezionare solo una scelta.");
                alert.showAndWait();
            }
            labelSceltaCategoria.setVisible(true);
            choiceCateg.setVisible(true);
            mostraButton.setVisible(true);
        }
        else
        {
            labelSceltaCategoria.setVisible(false);
            choiceCateg.setVisible(false);
            mostraButton.setVisible(false);
            tableView.setVisible(false);
            labelSelezione.setVisible(false);
            buttonAdd.setVisible(false);
            quantita.setVisible(false);
            avantiButton.setVisible(false);
        }
        if(checkMarca.isSelected())
        {
            labelSceltaMarca.setVisible(true);
            choiceMarca.setVisible(true);
            mostraButton.setVisible(true);
        }
        else if(!checkCateg.isSelected())
        {
            labelSceltaMarca.setVisible(false);
            choiceMarca.setVisible(false);
            mostraButton.setVisible(false);
            labelSelezione.setVisible(false);
            tableView.setVisible(false);
            buttonAdd.setVisible(false);
            quantita.setVisible(false);
            avantiButton.setVisible(false);
        }
    }

    /* Gestione metodo che rende visibile la tabella con tutti gli accessori importati dal database,
       utilizzando il Pattern Strategy
     */
    @FXML
    void handleShow(ActionEvent event) throws Exception
    {
        data.clear();
        tableView.setVisible(true);
        labelSelezione.setVisible(true);
        buttonAdd.setVisible(true);
        quantita.setVisible(true);
        avantiButton.setVisible(true);

        TipoTabella<Accessori> tab = null;

        if(checkCateg.isSelected())
        {
            String value = checkCateg.getText();
            tab = new TipoTabella<Accessori>(new TabellaCategoria(value));
        }
        else if(checkMarca.isSelected())
        {
            String value = checkMarca.getText();
            tab = new TipoTabella<Accessori>(new TabellaMarca(value));
        }
        tableView.setItems(tab.getElements());
    }

    // Gestione metodo che permette di aggiungere elementi dalla tabella alla lista "selected"
    @FXML
    void handleAdd(ActionEvent event) throws Exception
    {
        try
        {
            int quant = Integer.parseInt(quantita.getText());

            for(Accessori x : tableView.getSelectionModel().getSelectedItems())
            {
                //Controllo quantita
                if(quant > x.getQuantita())
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("La quantita richiesta per uno degli accessori selezionati non è attualmente disponibile");
                    alert.showAndWait();
                }
                x.setQuantita(quant);
                x.setPrezzo( quant*x.getPrezzo() );
            }
            selected.addAll(tableView.getSelectionModel().getSelectedItems());
        }
        catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Inserire quantità");
            alert.showAndWait();
        }
    }

    /*
     * Metodo che richiama l'interfaccia grafica "homeOverview.fxml"
     */
    @FXML
    void handleBackButton(ActionEvent event) throws Exception
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("homeOverview.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.setResizable(false);
        window.show();
    }

    /*
     * Metodo che richiama l'interfaccia grafica "Carrello.fxml"
     * @param event Pressione tasto "Avanti"
     * @throws Exception
     */
    @FXML
    void handleAvanti(ActionEvent event) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("carrello.fxml"));
        carrelloController carrello = new carrelloController(selected);
        loader.setController(carrello);
        Parent tableView = loader.load();
        Scene tableViewScene = new Scene(tableView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.setResizable(false);
        window.show();

    }
}
