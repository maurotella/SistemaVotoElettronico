package controllers;

import data.ElettoreDAOImpl;
import data.GestoreDAOImpl;
import data.PersonaDAOImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import models.Elettore;
import models.Gestore;
import models.Sessione;
import models.SessioneSemplice;

import java.util.List;
import java.util.stream.Collectors;

public class GestoreController {

    private Gestore G ;
    @FXML
    private Label nominativo;

    @FXML
    private Button nuovaSessioneButton;

    @FXML
    private ListView<SessioneSemplice> sessioniAttiveView;

    @FXML
    private ListView<Sessione> sessioniChiuseView;

    public void  init(Gestore G) {
        this.G = G;
        this.nominativo.setText(PersonaDAOImpl.getInstance().getNominativo(G.getCF()));

        sessioniAttiveView.getItems().addAll(
                ElettoreDAOImpl.getInstance().getSessioni().stream()
                        .map(x -> new SessioneSemplice(x.getId(), x.getTitolo()))
                        .collect(Collectors.toList())
        );

    }

    @FXML
    void nuovaSessioneClick() {
        try{
            Scene prev = App.getStage().getScene();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/gestore_nuovaSessione.fxml"));
            App.getStage().setScene(new Scene(loader.load()));
            App.getStage().setResizable(false);
            App.getStage().setTitle("Nuova Sessione di Votazione");
            NuovaSessioneController NEW = loader.getController();
            NEW.init(G, prev); //per riempire le choicebox, passo come argomento anche il gestore così ho il riferimento
            App.getStage().show();
        }catch (Exception e){
            throw new RuntimeException("Errore nuovaSessioneClick():\t" + e.getMessage());
        }

    }

}
