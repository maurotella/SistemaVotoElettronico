package controllers;

import data.ElettoreDAOImpl;
import data.PersonaDAOImpl;
import data.SessioneDAOImpl;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import models.Elettore;
import models.Sessione;
import models.SessioneSemplice;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ElettoreController {

    private Elettore E;
    @FXML
    private ListView<SessioneSemplice> elenco;

    @FXML
    private Label nominativo;

    @FXML
    private Button votaButton;

    public void aggiornaSessioni() {
        List<SessioneSemplice> L = ElettoreDAOImpl.getInstance().getSessioni()
                .stream().filter( S -> ElettoreDAOImpl.getInstance().puoVotare(E,S))
                .map(x -> new SessioneSemplice(x.getId(), x.getTitolo()))
                .collect(Collectors.toList());
        elenco.getItems().clear();
        elenco.getItems().addAll(L);
    }

    public void init(Elettore E) {
        this.E = E;
        this.nominativo.setText(
                PersonaDAOImpl.getInstance().getNominativo(E.getCF())
        );
        aggiornaSessioni();
    }

    @FXML
    void vota() {
        Scene actualScene = App.getStage().getScene();
        Sessione S = SessioneDAOImpl.getInstance().getSessione(elenco.getSelectionModel().getSelectedItem().getId());
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                String.format(
                        "/views/%s.fxml",
                        S.getTipoVotazione().toString().toLowerCase()
                )
        ));
        Stage pStage = App.getStage();
        try {
            pStage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pStage.setTitle("Votazione");
        pStage.setResizable(false);
        switch (S.getTipoVotazione()) {
            case REFERENDUM:
                ((ReferendumController)loader.getController()).init(actualScene,this, S, E);
                break;
        }
    }

}

