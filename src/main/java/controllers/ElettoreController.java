package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ElettoreController {
    @FXML
    private Label nominativo;

    public void init(String nominativo) {
        this.nominativo.setText(nominativo);
    }

}
