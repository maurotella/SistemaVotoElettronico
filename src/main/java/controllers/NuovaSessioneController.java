

package controllers;


import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller di gestore_nuovaSessione.fmxl
 */
public class NuovaSessioneController {

    @FXML
    private Button backButton;

    @FXML
    private Button confermaButton;

    @FXML
    private DatePicker dataFine;

    @FXML
    private DatePicker dataInizio;

    @FXML
    private Label nominativo;

    @FXML
    private ChoiceBox<?> scrutinioChoicebox;

    @FXML
    private TextField titoloVotazione;

    @FXML
    private ChoiceBox<?> votazioneChoicebox;

}
