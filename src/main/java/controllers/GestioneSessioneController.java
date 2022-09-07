package controllers;

import data.PersonaDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Gestore;
import models.SessioneSemplice;
import models.TipoScrutinio;
import models.TipoVotazione;

public class GestioneSessioneController {

    private Gestore G;
    private Scene genitore;

    @FXML
    private Button confermaButton;

    @FXML
    private TextField domandaField;

    @FXML
    private Button indietroButton;

    @FXML
    private Label nominativo;

    @FXML
    void confermaClick(ActionEvent event) {

    }

    @FXML
    void indietroClick(ActionEvent event) {
        App.getStage().setScene(genitore);
    }

    @FXML
    void init(Gestore G, Scene genitore, SessioneSemplice s){
        this.G = G;
        this.nominativo.setText(PersonaDAOImpl.getInstance().getNominativo(G.getCF()));
        this.genitore = genitore;
        System.out.println(s.toString());
    }

}
