package controllers;

import data.ElettoreDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import models.Sessione;

import java.util.List;
import java.util.stream.Collectors;

public class ElettoreController {

    @FXML
    private ListView<String> elenco;

    @FXML
    private Label nominativo;

    @FXML
    private Button votaButton;

    public void init(String nominativo) {
        this.nominativo.setText(nominativo);
        List<String> L = ElettoreDAOImpl.getInstance().getSessioni()
                .stream().map(x -> x.getTitolo())
                        .collect(Collectors.toList());
        elenco.getItems().addAll(L);
    }

    @FXML
    void vota(ActionEvent event) {
        System.out.println(event);
    }

}

