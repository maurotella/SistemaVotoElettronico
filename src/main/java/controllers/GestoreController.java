package controllers;

import data.GestoreDAOImpl;
import data.PersonaDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import models.Gestore;
import models.Sessione;
import models.SessioneSemplice;

import java.util.List;
import java.util.stream.Collectors;

public class GestoreController {
    @FXML
    private Label nominativo;

    @FXML
    private Button nuovaSessioneButton;

    @FXML
    private ListView<Sessione> sessioniAttiveView;

    @FXML
    private ListView<Sessione> sessioniChiuseView;

    public void  init(Gestore G) {
        this.nominativo.setText(PersonaDAOImpl.getInstance().getNominativo(G.getCF()));
        //devo prendere le sessioni aperte e renderle selezionabili per poi (eventualmente) modificarle
        List<Sessione> sessioniAperte = GestoreDAOImpl.getInstance().getSessioni(G);
        sessioniAttiveView.getItems().addAll(sessioniAperte);
    }

    @FXML
    void nuovaSessioneclick() {
        //cambio scena con gestore_NuovaSessione
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/%gestore_nuovaSessione.fxml"));
            App.getStage().setScene(new Scene(loader.load()));
            App.getStage().setResizable(false);
            App.getStage().setTitle("Gestione Sessione"); //title a cosa serve???
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

}
