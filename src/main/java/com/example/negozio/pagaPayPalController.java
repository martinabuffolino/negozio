package com.example.negozio;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import database.RicevutaDB;
import database.RiparazioneDB;
import database.SmartphoneDB;
import factory.pattern.PayPal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class pagaPayPalController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="nome"
    private TextField nome;

    @FXML // fx:id="cognome"
    private TextField cognome;

    @FXML // fx:id="passwordPayPal"
    private PasswordField passwordPayPal;

    @FXML // fx:id="emailPayPal"
    private TextField emailPayPal;

    @FXML // fx:id="backButton"
    private Button backButton;

    @FXML // fx:id="confermaButton"
    private Button confermaButton;

    @FXML
    Parent root;

    private double prezzo;
    private String ricevuta;


    public void initPayPal(double importoDaPagare, String codRicevutaDaPagare) {
        ricevuta = codRicevutaDaPagare;
        prezzo = importoDaPagare;
        System.out.println(prezzo);
    }

    // Gestione backButton per tornare indietro
    @FXML
    void handleBackButton(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(HelloApplication.class.getResource("controlloStatoRiparazione.fxml"));
        HelloApplication.getPrimaryStage().setScene(new Scene(root));

    }

    // Gestione controllo paramenti pagamento
    public boolean enablePayment(){
        System.out.println("Pagamento abilitato");
        if (nome.getText().isEmpty() | cognome.getText().isEmpty() | passwordPayPal.getText().isEmpty() | emailPayPal.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Attenzione tutti i campi sono obbligatori");
            alert.showAndWait();
            return false;
        }

        if (!emailPayPal.getText().contains("@")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Attenzione campo email non compilato correttamente");
            alert.showAndWait();
            return false;
        }

        return true;
    }

    // Gestione metodo paga
    @FXML
    void handlePaga(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (enablePayment()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFERMA PAGAMENTO");
            alert.setHeaderText(null);
            alert.setContentText("Confermi di voler procedere al pagamento?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){

                PayPal account = new PayPal(nome.getText(), cognome.getText(),emailPayPal.getText(),passwordPayPal.getText());
                account.paga();

                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/negozio?user=root&password=");
                Statement stmt = connection.createStatement();

                String riparazione = null;
                String smartphone = null;
                String cliente = null;
                try{
                    ResultSet resultRiparazione = null;
                    String sql = "SELECT CODRIPARAZIONE FROM RICEVUTA WHERE CODRICEVUTA = " + ricevuta;
                    System.out.println("Eseguo stmt: " + stmt);
                    resultRiparazione = stmt.executeQuery(sql);
                    System.out.println("Query Eseguita");

                    if (resultRiparazione.next()) {
                        riparazione = resultRiparazione.getString("CODRIPARAZIONE");
                        System.out.println("COD RIPARAZIONE: " + riparazione);
                    }

                    ResultSet resultSmartphone = null;
                    sql = "SELECT SERIALE FROM RIPARAZIONE WHERE CODRIPARAZIONE = " + riparazione ;
                    System.out.println("Eseguo stmt: " + stmt);
                    resultSmartphone= stmt.executeQuery(sql);
                    System.out.println("Seconda Query Eseguita");

                    if(resultSmartphone.next()){
                        smartphone = resultSmartphone.getString("SERIALE");
                        System.out.println("SERIALE: " + smartphone);
                    }

                    ResultSet resultCliente = null;
                    sql = "SELECT CODFISCALE FROM SMARTPHONE WHERE SERIALE = " +smartphone;
                    System.out.println("Eseguo stmt " + stmt);
                    resultCliente = stmt.executeQuery(sql);
                    System.out.println("Terza Query Eseguita");

                    if(resultCliente.next()){
                        cliente = resultCliente.getString("CODICEFISCALE");
                    }

                } catch (SQLException e){
                    e.printStackTrace();
                } finally {

                    RicevutaDB.deleteRicevuta(Integer.parseInt(ricevuta));
                    RiparazioneDB.deleteRiparazione(Integer.parseInt(riparazione));
                    SmartphoneDB.deleteSmartphone(smartphone);

                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("PAGAMENTO RIUSCITO");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Pagamento avvenuto con successo");
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
                System.out.println("Payment canceled");
            }
        }

    }
}