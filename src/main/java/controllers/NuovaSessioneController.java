

package controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

/**
 * Controller di gestore_nuovaSessione.fmxl
 */
public class NuovaSessioneController {

    @FXML
    private Button confermaButton;

    @FXML
    private DatePicker dataFine;

    @FXML
    private DatePicker dataInizio;

    @FXML
    private Button indietroButton;

    @FXML
    private Label nominativo;

    @FXML
    private ChoiceBox<?> scrutinioChoicebox;

    @FXML
    private TextField titoloVotazione;

    @FXML
    private ChoiceBox<?> votazioneChoicebox;

    @FXML
    void confermaClick() {

    }

    @FXML
    void indietroClick() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/%gestoreMain.fxml"));
            App.getStage().setScene(new Scene(loader.load()));
            App.getStage().setResizable(false);
            App.getStage().setTitle("Gestore");
        }catch (Exception e){
            throw new RuntimeException("Errore indietroClick()" + e.getMessage());
        }
    }

}
