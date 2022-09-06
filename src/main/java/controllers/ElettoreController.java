package controllers;

import data.ElettoreDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import models.Sessione;
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

    public void init(String nominativo) {
        this.nominativo.setText(nominativo);
        List<SessioneSemplice> L = ElettoreDAOImpl.getInstance().getSessioni()
                .stream().map(x -> new SessioneSemplice(x.getId(), x.getTitolo()))
                        .collect(Collectors.toList());
        elenco.getItems().addAll(L);
    }

    @FXML
    void vota(ActionEvent event) {
        System.out.println(elenco.getSelectionModel().getSelectedItem().getId());
    }

}

