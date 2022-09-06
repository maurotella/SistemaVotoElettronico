package controllers;

import data.ElettoreDAOImpl;
import data.PersonaDAOImpl;
import data.SessioneDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import models.Elettore;
import models.SessioneSemplice;

import java.util.List;
import java.util.stream.Collectors;

public class ElettoreController {

    @FXML
    private ListView<SessioneSemplice> elenco;

    @FXML
    private Label nominativo;

    @FXML
    private Button votaButton;

    public void init(Elettore E) {
        this.nominativo.setText(
                PersonaDAOImpl.getInstance().getNominativo(E.getCF())
        );
        List<SessioneSemplice> L = ElettoreDAOImpl.getInstance().getSessioni()
                .stream().map(x -> new SessioneSemplice(x.getId(), x.getTitolo()))
                        .collect(Collectors.toList());
        elenco.getItems().addAll(L);
    }

    @FXML
    void vota(ActionEvent event) {
        System.out.println(SessioneDAOImpl.getInstance().getSessione(
                elenco.getSelectionModel().getSelectedItem().getId()
        ));
    }

}

