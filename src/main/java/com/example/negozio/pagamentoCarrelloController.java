package com.example.negozio;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import database.DataBaseNegozioConnect;
import factory.pattern.CreditCardFactory;
import factory.pattern.Factory;
import factory.pattern.MetodoPagamentoFactory;
import factory.pattern.PayPalFactory;
import strategy.pattern.Accessori;
import strategy.pattern.Marca;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class pagamentoCarrelloController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="labelConto"
    private Label labelConto;

    @FXML // fx:id="choiceBoxPagamento"
    private ChoiceBox<String> choiceBoxPagamento;

    @FXML // fx:id="nome"
    private TextField nome;

    @FXML // fx:id="cognome"
    private TextField cognome;

    @FXML // fx:id="email"
    private TextField email;

    @FXML // fx:id="password"
    private TextField password;

    @FXML // fx:id="numCarta"
    private TextField numCarta;

    @FXML // fx:id="cvv"
    private TextField cvv;

    @FXML // fx:id="backButton"
    private Button backButton;

    @FXML // fx:id="pagaButton"
    private Button pagaButton;

    Parent root;

    @FXML
    private ObservableList<Accessori> selected;
    private Marca marc;
    private double conto;

    /*
     * Costruttore di classe, che inizializza la variabile "conto" sulla base
     * degli accessori ricevuti in input
     */
    public pagamentoCarrelloController(ObservableList<Accessori> x)
    {
        selected = x;
        conto = 0;
        for(Accessori y : selected)
            conto += y.getPrezzo();
    }

    public void initialize()
    {
        choiceBoxPagamento.getItems().add("PayPal");
        choiceBoxPagamento.getItems().add("Credit Card");
        labelConto.setText("Conto : €"+String.format("%.2f", conto));
    }

    // Gestione tipo di pagamento
    @FXML
    public void handleTipoPagamento() {

        String value = choiceBoxPagamento.getValue();
        if(value.equals("PayPal"))
        {
            nome.setVisible(true);
            cognome.setVisible(true);
            email.setVisible(true);
            password.setVisible(true);
            numCarta.setVisible(false);
            cvv.setVisible(false);
        }
        else
        {
            nome.setVisible(true);
            cognome.setVisible(true);
            email.setVisible(false);
            password.setVisible(false);
            numCarta.setVisible(true);
            cvv.setVisible(true);
        }
    }

    //  Gestione backButton per ritornare alla schermata del carrello
    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {

        root = FXMLLoader.load(HelloApplication.class.getResource("carrello.fxml"));
        HelloApplication.getPrimaryStage().setScene(new Scene(root));

    }

    // Gestione metodo pagamento che va ad eliminare dal DB il numero di accessori selezionati
    @FXML
    public void handlePaga(ActionEvent event)  throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/negozio?user=root&password=");
        /*	Con getConnection il Driver Manager cerca il driver opportuno fra quelli caricati
            URL JDBC ha il formato -> jdbc:subprotocol:subname
            dove il subprotocol specifica il driver e subname specifica il DB vero e proprio
         */
        Statement stmt = connection.createStatement();
        ResultSet result = null;

        try
        {
            Factory factory = null;
            MetodoPagamentoFactory met = null;

            //  Leggi scelta
            String value = (String) choiceBoxPagamento.getValue();

            if(value.equals("PayPal"))
            {
                //  Controllo dati
                if(email.getText().isEmpty() || password.getText().isEmpty())
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERRORE");
                    alert.setContentText("Attenzione tutti i campi sono obbligatori");
                    alert.showAndWait();
                }

                factory = new PayPalFactory(nome.getText(),cognome.getText(),email.getText(),password.getText());
                met = factory.getMetodo();
            }
            else
            {
                //  Controllo dati
                if(numCarta.getText().isEmpty() || cvv.getText().isEmpty())
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERRORE");
                    alert.setContentText("Attenzione tutti i campi sono obbligatori");
                    alert.showAndWait();
                }
                factory = new CreditCardFactory(nome.getText(),cognome.getText(),numCarta.getText(),cvv.getText());
                met = factory.getMetodo();
            }

            //  Aggiorna Database
            if(marc == null)
                for(Accessori x : selected)
                {
                    x.setVenduti( x.getVenduti()+x.getQuantita() );
                    String sql = "UPDATE ACCESSORI SET VENDUTI = " + x.getVenduti() + " WHERE CODICE=" + x.getCodice();
                    DataBaseNegozioConnect.dbExectuteQuery(sql);
                }
            else
                for(int i=0;i<marc.getSize();i++)
                {
                    int id = marc.getAcc(i).getCodice();
                    int q = marc.getAcc(i).getQuantita();
                    String sql = "UPDATE ACCESSORI SET VENDUTI = VENDUTI+(QUANTITA-VENDUTI-" + q +") WHERE codice=" + id;
                    DataBaseNegozioConnect.dbExectuteQuery(sql);
                }

            //  Ritorna al menu principale
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("homeOverview.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }
        catch(NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Scegli il metodo di pagamento");
            alert.showAndWait();
        }
    }
}