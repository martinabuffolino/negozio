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
import application.Cliente;
import database.ClienteDB;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class nuovaRiparazioneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="emailCliente"
    private TextField emailCliente;

    @FXML // fx:id="cellCliente"
    private TextField cellCliente;

    @FXML // fx:id="passwordCliente"
    private PasswordField passwordCliente;

    @FXML // fx:id="cfCliente"
    private TextField cfCliente;

    @FXML // fx:id="backButton"
    private Button backButton;

    @FXML // fx:id="avantiButton"
    private Button avantiButton;

    @FXML // fx:id="nomeCliente"
    private TextField nomeCliente;

    @FXML // fx:id="cognomeCliente"
    private TextField cognomeCliente;

    @FXML // fx:id="loginCliente"
    private Label loginCliente;

    public Cliente cliente= new Cliente();

    // verifica dati duplicati
    public static final int MYSQL_DUPLICATE_PK = 1062;

    @FXML
    private Parent root;

    public nuovaRiparazioneController () {

    }

    @FXML
    private void initialize()
    {

    }

    @FXML
    public void inizializzaNuovaRiparazione() {

        try {
            FXMLLoader clientLoader = new FXMLLoader(HelloApplication.class.getResource("nuovaRiparazione.fxml"));
            VBox riparazioneOverview = (VBox) clientLoader.load();

            HelloApplication.getPrimaryStage().setScene(new Scene(riparazioneOverview));
            HelloApplication.getPrimaryStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Gestione login cliente registrato nel database
    @FXML
    public void loginCliente(MouseEvent mouseEvent) {

    // Crea un dialog
    Dialog<Pair<String, String>> dialog = new Dialog<>();
    dialog.setTitle("LOGIN");
    dialog.setHeaderText("Effettua il login e prenota una nuova riparazione");

    // Button
    ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

    // Creazione username e password label
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextField username = new TextField();
    username.setPromptText("Email");
    PasswordField password = new PasswordField();
    password.setPromptText("Password");

    grid.add(new Label("Email:"), 0, 0);
    grid.add(username, 1, 0);
    grid.add(new Label("Password:"), 0, 1);
    grid.add(password, 1, 1);

    // Abilita/Disabilita loginButton se il nome utente viene inserito
    Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
    loginButton.setDisable(true);

    // Do some validation
    username.textProperty().addListener((observable, oldValue, newValue) -> {
        loginButton.setDisable(newValue.trim().isEmpty());
    });

    dialog.getDialogPane().setContent(grid);

    // Request focus on the username field by default
    Platform.runLater(() -> username.requestFocus());

    // Converte il risultato in una coppia nome utente-password quando si fa clic sul pulsante di accesso
    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == loginButtonType) {
            return new Pair<>(username.getText(), password.getText());
        }
        return null;
    });

    Optional<Pair<String, String>> result = dialog.showAndWait();

    result.ifPresent(usernamePassword -> {
        System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());

        if (checkLoginCliente(usernamePassword.getKey(),usernamePassword.getValue())){
            System.out.println("Login eseguito");

            try {

                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/negozio?user=root&password=");
                Statement statement = connection.createStatement();
                ResultSet resultLogin = null;

                String sql = "SELECT * FROM CLIENTE WHERE EMAIL = '" + usernamePassword.getKey() + "';";
                resultLogin = statement.executeQuery(sql);

                if(resultLogin.next()) {

                    cliente.setCF(resultLogin.getString("CODFISCALE"));
                    cliente.setNome(resultLogin.getString("NOME"));
                    cliente.setCognome(resultLogin.getString("COGNOME"));
                    cliente.setEmail(resultLogin.getString("EMAIL"));
                    cliente.setPassword(resultLogin.getString("PASSWORD"));
                    cliente.setCellulare(resultLogin.getString("CELLULARE"));

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("registrazioneSmartphone.fxml"));

                        Parent root = (Parent) loader.load();

                        registrazioneSmartphoneController registra = loader.getController();
                        registra.myFunction(cliente);

                        HelloApplication.getPrimaryStage().setScene(new Scene(root));
                        HelloApplication.getPrimaryStage().show();

                        System.out.println(cliente.getCF());

                    } catch (IOException e) {
                            e.printStackTrace();
                    }
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    });
    }

    //  Gestione controllo credenziali login cliente
    private boolean checkLoginCliente(String username, String password){

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/negozio?user=root&password=");
            Statement statement = connection.createStatement();
            ResultSet result = null;

            String sql = "SELECT PASSWORD FROM CLIENTE WHERE EMAIL = '" + username + "';";
            result = statement.executeQuery(sql);

            if (result.next()) {
                String passwordEmail = result.getString("PASSWORD");
                if (password.equals(passwordEmail)) {
                    System.out.println("Password Esatta");
                } else {
                    System.out.println("Password Errata");
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERRORE");
                    alert.setContentText("Password errata");
                    alert.showAndWait();
                    return false;
                }

            } else {
                System.out.println("Email Errata");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERRORE");
                alert.setContentText("Email Errata");
                alert.showAndWait();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    //  Gestione button per ritornare alla schermata principale
    @FXML
    void handleBackButton() throws IOException {

        root = FXMLLoader.load(HelloApplication.class.getResource("homeOverview.fxml"));
        HelloApplication.getPrimaryStage().setScene(new Scene(root));

    }

    //  Gestione per prenotare una nuova riparazione
    @FXML
    void handleNuovaRiparazione() {

        if (validateFields()) {
            cliente.setCF(cfCliente.getText());
            cliente.setNome(nomeCliente.getText());
            cliente.setCognome(cognomeCliente.getText());
            cliente.setEmail(emailCliente.getText());
            cliente.setPassword(passwordCliente.getText());
            cliente.setCellulare(cellCliente.getText());

            try {
                ClienteDB.insertCliente(cliente.getCF(), cliente.getNome(), cliente.getCognome(), cliente.getEmail(), cliente.getPassword(), cliente.getCellulare());
                System.out.println("Nuovo cliente registrato");

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("registrazioneSmartphone.fxml"));

                    Parent root = (Parent) loader.load();

                    registrazioneSmartphoneController registra = loader.getController();
                    registra.myFunction(cliente);

                    HelloApplication.getPrimaryStage().setScene(new Scene(root));
                    HelloApplication.getPrimaryStage().show();

                    System.out.println(cliente.getCF());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                System.out.println("Errore, dati già presenti");
                if(e.getErrorCode() == MYSQL_DUPLICATE_PK){
                    //duplicate primary key
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("ERRORE");
                    alert.setContentText("Sei già registrato, clicca su 'Login con il mio account'");
                    alert.showAndWait();
                }
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("Errore inserimento dati nel database");
                e.printStackTrace();
            }
        }

    }

    // Gestione controllo parametri inseriti
    private boolean validateFields() {
        if( cfCliente.getText().isEmpty() | nomeCliente.getText().isEmpty() | cognomeCliente.getText().isEmpty()
                | emailCliente.getText().isEmpty() | passwordCliente.getText().isEmpty() | cellCliente.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setContentText("Attenzione tutti i campi sono obbligatori");
            alert.showAndWait();
            return false;
        } else if (cfCliente.getText().length() > 16 | cfCliente.getText().length() < 16) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setContentText("Attenzione controlla che il codice fiscale sia esatto");
            alert.showAndWait();
            return false;
        } else if (!emailCliente.getText().contains("@")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRORE");
            alert.setContentText("Attenzione controlla l'email inserita");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}