package com.example.negozio;

import application.Tecnico;
import application.Riparazione;
import java.net.URL;
import java.io.IOException;
import java.sql.*;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.awt.event.ActionEvent;
import javafx.event.EventHandler;

import static java.lang.Integer.parseInt;

public class tabellaStatoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="smartphoneTab"
    private TableView<tabellaStatoParametri> smartphoneTab;

    @FXML // fx:id="tabCodRip"
    private TableColumn<tabellaStatoParametri, String> tabCodRip;

    @FXML // fx:id="tabStato"
    private TableColumn<tabellaStatoParametri, String> tabStato;

    @FXML // fx:id="tabSmartphone"
    private TableColumn<tabellaStatoParametri, String> tabSmartphone;

    @FXML // fx:id="tabTipo"
    private TableColumn<tabellaStatoParametri, String> tabTipo;

    @FXML // fx:id="updateStatus"
    private Button updateStatus;

    @FXML // fx:id="backButton"
    private Button backButton;

    private Tecnico tecnico = new Tecnico();

    public Riparazione riparazione = new Riparazione();

    public String selectedCodRip;
    public String selectedTipoRip;
    public String selectedStato;
    public String selectedSmartphone;

    Parent root;
    ObservableList<tabellaStatoParametri> obList = FXCollections.observableArrayList();;

    //function che seleziona il codice tecnico
    public void myFunction(Tecnico selectedTecnico) {

        tecnico = selectedTecnico;
        riparazione.setCodTecnico(tecnico.getCodice());
        try {
            stampaTabella(tecnico.getCodice());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void stampaTabella(int codice) throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/negozio?user=root&password=");
        /*	Con getConnection il Driver Manager cerca il driver opportuno fra quelli caricati
            URL JDBC ha il formato -> jdbc:subprotocol:subname
            dove il subprotocol specifica il driver e subname specifica il DB vero e proprio
         */
        Statement stmt = connection.createStatement();
        ResultSet result = null;

        String query = "SELECT * FROM RIPARAZIONE WHERE CODTECNICO = " + codice + ";";
        try {
            System.out.println("Eseguo statement: " + query);
            result = stmt.executeQuery(query);

            System.out.println("Query Eseguita");
        } catch (SQLException e) {
            System.out.println("Si è verificato un errore durante l'inserimento dei dati " + e);
            e.printStackTrace();
            throw e;
        } finally {
            System.out.println("Eseguo");
            if (result != null){
                while (result.next())
                {
                    obList.add(new tabellaStatoParametri(
                            result.getString("NUMRIPARAZIONE"),
                            result.getString("TIPORIPARAZIONE"),
                            result.getString("STATO"),
                            result.getString("SERIALE")));

                    // setCellValueFactory: usato per determinare quale campo all’interno dell’oggetto dovrebbe essere usato per quella colonna
                    tabCodRip.setCellValueFactory(new PropertyValueFactory<>("codRiparazione"));
                    tabTipo.setCellValueFactory(new PropertyValueFactory<>("tipoRiparazione"));
                    tabStato.setCellValueFactory(new PropertyValueFactory<>("statoRiparazione"));
                    tabSmartphone.setCellValueFactory(new PropertyValueFactory<>("seriale"));

                    smartphoneTab.setItems(obList);

                    // MouseEvent: è un evento di input che si verifica quando si fa clic con il mouse
                    smartphoneTab.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            selectedCodRip = smartphoneTab.getSelectionModel().getSelectedItem().getCodRiparazione();
                            selectedTipoRip = smartphoneTab.getSelectionModel().getSelectedItem().getTipoRiparazione();
                            selectedStato = smartphoneTab.getSelectionModel().getSelectedItem().getStatoRiparazione();
                            selectedSmartphone = smartphoneTab.getSelectionModel().getSelectedItem().getSerialeSmartphone();
                        }
                    });
                }
            }
        }
    }

    @FXML	//	Gestion backButton per tornare indietro
    public void handleBackButton() throws SQLException, IOException {

        root = FXMLLoader.load(HelloApplication.class.getResource("tabellaStato.fxml"));
        HelloApplication.getPrimaryStage().setScene(new Scene(root));

    }

    @FXML	//	Gestion updateButton per aggiornare lo stato dello smartphone riparato
    public void handleUpdate(ActionEvent event) throws SQLException{

        System.out.println("Stampo dati riparazione: ");

        riparazione.setCodRiparazione(parseInt(selectedCodRip));
        riparazione.setTipoRiparazione(selectedTipoRip);
        riparazione.setSerialeSmartphone(selectedSmartphone);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("riparaVeicolo.fxml"));

            Parent root = (Parent) loader.load();

            aggiornamentoStatoController warning = loader.getController();
            warning.myFunction(riparazione,tecnico);

            HelloApplication.getPrimaryStage().setScene(new Scene(root));
            HelloApplication.getPrimaryStage().show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}