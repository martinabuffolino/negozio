package com.example.negozio;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import application.Tecnico;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import static java.lang.Integer.parseInt;


public class tecnicoOverviewController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="loginButton"
    private Button loginButton;

    @FXML // fx:id="backButton"
    private Button backButton;

    @FXML // fx:id="idTecnico"
    private TextField idTecnico;

    @FXML // fx:id="passwordTecnico"
    private PasswordField passwordTecnico;

    private Parent root;

    public Tecnico selectedTecnico = new Tecnico();

    public tecnicoOverviewController() {

    }

    @FXML
    private void initialize() {

    }

    @FXML
    public void inizializzaTecnicoOverviwe() {

        try {
            FXMLLoader tecnicoLoader = new FXMLLoader(HelloApplication.class.getResource("tecnicoOverview.fxml"));
            VBox tecnicoOverview = (VBox) tecnicoLoader.load();

            HelloApplication.getPrimaryStage().setScene(new Scene(tecnicoOverview));
            HelloApplication.getPrimaryStage().show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void handleLoginTecnico() throws IOException, SQLException {

        if (validateCrendentials()) {

            int selectedCodice = parseInt(idTecnico.getText());

            try {
                loginTecnico(selectedCodice);
                System.out.println("Accesso eseguito come tecnico");

                try {
                    root = FXMLLoader.load(HelloApplication.class.getResource("tabellaStato.fxml"));
                    HelloApplication.getPrimaryStage().setScene(new Scene(root));

                    tabellaStatoController tecnico = new tabellaStatoController();
                    tecnico.myFunction(selectedTecnico);

                    HelloApplication.getPrimaryStage().show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (ClassNotFoundException e) {
                System.out.println("Errore credenziali errate");
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("Il codice inserito non esiste");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERRORE");
                alert.setHeaderText(null);
                alert.setContentText("Codice inserito insistente");
                alert.showAndWait();
            }
        }
    }

    //  Gestione button per ritornare alla schermata principale
    @FXML
    public void handleBackButton() throws IOException {

        root = FXMLLoader.load(HelloApplication.class.getResource("homeOverview.fxml"));
        HelloApplication.getPrimaryStage().setScene(new Scene(root));
    }

    // Gestione login tecnico registrato nel database
    private void loginTecnico(int codiceTecnico) throws Exception {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/negozio", "root", "Programmazione.3");

        Statement stmt = connection.createStatement();

        ResultSet result = null;

        String query = "SELECT * FROM `negozio`.`tecnico`WHERE CODICETECNICO = "+ codiceTecnico +";";


        try {
            System.out.println("Eseguo stmt: "+ query);
            result = stmt.executeQuery(query);

            System.out.println("Query eseguita");

        } catch (SQLException e) {

            System.out.println("Error occured while inserting the data " + e);
            e.printStackTrace();
            throw e;
        } finally {

            if(!result.isBeforeFirst()){
                System.out.println("No Data Found"); //data not exist
                throw new  Exception();
            }

            while (result.next()) {

                System.out.println(result.getString("CODICETECNICO"));
                System.out.println(result.getString("NOMETECNICO"));
                System.out.println(result.getString("COGNOMETECNICO"));

                selectedTecnico.setCodice(parseInt(result.getString("CODICETECNICO")));
                selectedTecnico.setNome(result.getString("NOMETECNICO"));
                selectedTecnico.setCognome(result.getString("COGNOMETECNICO"));

            }

            if (result != null) {
                result.close();
            }

            if (stmt != null) {
                stmt.close();
            }
        }
    }

    //  Gestione controllo credenziali login tecnico
    public boolean validateCrendentials(){

        if (idTecnico.getText().isEmpty() | passwordTecnico.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setHeaderText(null);
            alert.setContentText("Inserisci le credenziali per accedere al tuo account");
            alert.showAndWait();
            return false;
        } else if (idTecnico.getText().length() < 2 | idTecnico.getText().length() > 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE");
            alert.setHeaderText(null);
            alert.setContentText("Errore codice tecnico");
            alert.showAndWait();
            return false;
        } else if(!passwordTecnico.getText().equals("password")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRORE");
            alert.setHeaderText(null);
            alert.setContentText("Errore password");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
