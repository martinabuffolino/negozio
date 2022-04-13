package com.example.negozio;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import database.RicevutaDB;
import database.RiparazioneDB;
import database.SmartphoneDB;
import factory.pattern.CreditCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class pagaCartaController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="fullNameLabel"
    private TextField fullNameLabel;

    @FXML // fx:id="numeroCarta"
    private TextField numeroCarta;

    @FXML // fx:id="cvvLabel"
    private TextField cvvLabel;

    @FXML // fx:id="meseCarta"
    private ComboBox meseCarta;

    @FXML // fx:id="annoCarta"
    private TextField annoCarta;

    @FXML // fx:id="nextButton"
    private Button nextButton;

    @FXML // fx:id="backButton"
    private Button backButton;

    private double prezzo;
    private String codiceRicevuta;
    private List<String> mesi = new ArrayList<>();
    private List<Integer> anni = new ArrayList<>();

    private String[] months = new DateFormatSymbols().getShortMonths();

    private String dateOfExpire;

    Parent root;

    public void initCartaPaga(double costo, String codRicevuta){
        codiceRicevuta = codRicevuta;
        prezzo = costo;
        System.out.println(prezzo);

        setMesi(mesi);
        meseCarta.getItems().addAll(mesi);
    }

    @FXML
    void handleBackButton(ActionEvent actionEvent) throws SQLException, IOException {

        root = FXMLLoader.load(HelloApplication.class.getResource("controlloStatoRiparazione.fxml"));
        HelloApplication.getPrimaryStage().setScene(new Scene(root));
    }

    // Gestione metodo paga dove il cliente compila tutti i parametri
    @FXML
    void handlePaga(ActionEvent actionEvent) throws SQLException, ClassNotFoundException{

        if(enablePaga()) {
            String nomeCompleto = fullNameLabel.getText();
            String numCarta = (numeroCarta.getText());
            String codiceCVV = (cvvLabel.getText());
            System.out.println("Nome Completo:" + nomeCompleto);
            System.out.println("Numero Carta:" + numCarta);
            System.out.println("Numero CVV:" + codiceCVV);
            dateOfExpire = meseCarta.getSelectionModel().getSelectedItem() + " " + annoCarta.getText();
            System.out.println("Data Scadenza: " + dateOfExpire);

            CreditCard carta = new CreditCard(nomeCompleto,numCarta, codiceCVV, dateOfExpire);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFERMA PAGAMENTO");
            alert.setHeaderText(null);
            alert.setContentText("Confermi di voler procedere al pagamento?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){

                carta.paga();

                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/negozio?user=root&password=");
                Statement stmt = connection.createStatement();

                String riparazione = null;
                String smartphone = null;
                String cliente = null;
                try{
                    ResultSet resultRiparazione = null;
                    String query = "SELECT CODRIPARAZIONE FROM RICEVUTA WHERE CODRICEVUTA = " + codiceRicevuta;
                    System.out.println("Eseguo stmt: " + stmt);
                    resultRiparazione = stmt.executeQuery(query);
                    System.out.println("Query Eseguita");

                    if (resultRiparazione.next()) {
                        riparazione = resultRiparazione.getString("CODRIPARAZIONE");
                        System.out.println("CODICE RIPARAZIONE: " + riparazione);
                    }

                    ResultSet resultSmartphone = null;
                    query = "SELECT SERIALE FROM RIPARAZIONE WHERE CODRIPARAZIONE = " + riparazione ;
                    System.out.println("Eseguo stmt: " + stmt);
                    resultSmartphone = stmt.executeQuery(query);
                    System.out.println("Seconda Query Eseguita");

                    if(resultSmartphone.next()){
                        smartphone = resultSmartphone.getString("SERIALE");
                        System.out.println("SERIALE: " + smartphone);
                    }

                    ResultSet resultCliente = null;
                    query = "SELECT CODFISCALE FROM SMARTPHONE WHERE SERIALE = " +smartphone;
                    System.out.println("Eseguo stmt " + stmt);
                    resultCliente = stmt.executeQuery(query);
                    System.out.println("Terza Query Eseguita");

                    if(resultCliente.next()){
                        cliente = resultCliente.getString("CODICEFISCALE");
                    }

                } catch (SQLException e){
                    e.printStackTrace();
                } finally {

                    RicevutaDB.deleteRicevuta(Integer.parseInt(codiceRicevuta));
                    RiparazioneDB.deleteRiparazione(Integer.parseInt(riparazione));
                    SmartphoneDB.deleteSmartphone(smartphone);

                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("PAGAMENTO RIUSCITO");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Pagamento avvenuto con successo\nGrazie per averci scelto!");
                    alert1.showAndWait();

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeOverview.fxml"));
                        Parent root = (Parent) loader.load();

                        HelloApplication.getPrimaryStage().setScene(new Scene(root));
                        HelloApplication.getPrimaryStage().show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Pagamento cancellato");
            }
        }
    }

    public void setMesi(List <String> monthsList){

        for (int i = 1; i < months.length; i++) {
            String month = months[i];
            monthsList .add(months[i]);
        }
    }

    public boolean enablePaga() {

        if(fullNameLabel.getText().isEmpty() | numeroCarta.getText().isEmpty() | cvvLabel.getText().isEmpty()
                | meseCarta.getSelectionModel().isEmpty() | annoCarta.getText().isEmpty()){
            System.out.println("Tutto Vuoto");
            //pop up campi obblicatori
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ATTENZIONE");
            alert.setHeaderText(null);
            alert.setContentText("Attenzione tutti i campi sono obbligari per il pagamento");
            alert.showAndWait();
            return false;
        }

        if (numeroCarta.getText().length() < 13 | numeroCarta.getText().length() > 16){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ATTENZIONE");
            alert.setHeaderText(null);
            alert.setContentText("Attenzione numero della carta di credito errato");
            alert.showAndWait();
            return false;
        }

        if (cvvLabel.getText().length() < 3 | cvvLabel.getText().length() > 3){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ATTENZIONE");
            alert.setHeaderText(null);
            alert.setContentText("Attenzione CVV errato");
            alert.showAndWait();
            return false;
        }
        Calendar cal = Calendar.getInstance();
        String currentMonth = new SimpleDateFormat("MMM").format(cal.getTime());
        String currentYear = new SimpleDateFormat("YYYY").format(cal.getTime());
        System.out.println(new SimpleDateFormat("MMM").format(cal.getTime()));
        System.out.println(new SimpleDateFormat("YYYY").format(cal.getTime()));
        if (Integer.parseInt(annoCarta.getText()) < Integer.parseInt(currentYear)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ATTENZIONE");
            alert.setHeaderText(null);
            alert.setContentText("Attenzione data di scadenza");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}