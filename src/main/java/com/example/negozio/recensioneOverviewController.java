package com.example.negozio;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class recensioneOverviewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="nextButton"
    private Button nextButton;

    @FXML // fx:id="backButton"
    private Button backButton;

    @FXML // fx:id="loginTwitterRadioButton"
    private RadioButton loginTwitterRadioButton;

    @FXML // fx:id="loginFacebookRadioButton"
    private RadioButton loginFacebookRadioButton;

    @FXML
    private ToggleGroup rbGroup = new ToggleGroup();

    @FXML
    Parent root;

    public recensioneOverviewController() {

    }

    @FXML
    private void initialize() {
        loginTwitterRadioButton.setToggleGroup(rbGroup);
        loginFacebookRadioButton.setToggleGroup(rbGroup);
    }

    @FXML
    void handleBackButton(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(HelloApplication.class.getResource("homeOverview.fxml"));
        HelloApplication.getPrimaryStage().setScene(new Scene(root));
    }

    @FXML
    void handleLoginSocial(ActionEvent event) throws IOException {

        if(loginFacebookRadioButton.isSelected()) {
            System.out.println("Crea Nuova Riprazione");
            root = FXMLLoader.load(HelloApplication.class.getResource("facebook.fxml"));
            HelloApplication.getPrimaryStage().setScene(new Scene(root));

            facebookController facebook = new facebookController();
            facebook.inizializzaFacebookController();
        }

        if(loginTwitterRadioButton.isSelected()) {
            System.out.println("Controlla Stato Riparazione");
            root = FXMLLoader.load(HelloApplication.class.getResource("twitter.fxml"));
            HelloApplication.getPrimaryStage().setScene(new Scene(root));

            twitterController twitter = new twitterController();
            twitter.inizializzaTwitterController();

        }
    }

}
