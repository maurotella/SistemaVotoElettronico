package controllers;

import data.SessioneDAOImpl;
import data.VotazioneCandidatiDAOImpl;
import data.VotazioneElettoreDAOImpl;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VotoCategoricoController implements VotazioneController {

    Scene genitore;
    ElettoreController genitoreController;
    Sessione S;
    Elettore E;
    HashMap<Partito, ArrayList<Candidato>> lista;
    // Array che associa i candidati con il loro checkBox id (tramite indice)
    Candidato[] candidatiIdCB;
    CheckBox selectedBox;

    @FXML
    private VBox elenco;

    @FXML
    private Label istruzioni;

    @FXML
    private VBox opzioni;

    @FXML
    private Label titolo;

    @FXML
    private Button votaButton;

    void init(Scene g, ElettoreController gc, Sessione s, Elettore e) {
        genitore = g;
        genitoreController = gc;
        titolo.setText(s.getTitolo());
        istruzioni.setText(
                istruzioni.getText().replace(
                        "$",
                        s.isVotazionePartiti() ? "partito" : "candidato"
                )
        );
        S = s;
        E = e;
        lista = SessioneDAOImpl.getInstance().getListaCandidati(s);
        int len = lista.values().stream().flatMap(List::stream).toArray().length;
        candidatiIdCB = new Candidato[len];
        insertChoices();
        insertElenco();
    }

    /**
     * Inserisce la lista dei partiti con i relativi membri
     */
    void insertElenco () {
        for (Partito partito : lista.keySet()){
            Label labelPartito = new Label();
            labelPartito.setFont(Font.font("System", FontWeight.BOLD, 17));
            labelPartito.setText(partito.getNome());
            elenco.getChildren().add(labelPartito);
            List<VotoOrdinaleController.CandidatoStringato> candidatiPartito =
                    lista.get(partito).stream().map(VotoOrdinaleController.CandidatoStringato::new).toList();
            for (VotoOrdinaleController.CandidatoStringato candidato : candidatiPartito) {
                Label labelCandidato = new Label();
                labelCandidato.setText("- "+candidato.toString());
                labelCandidato.setFont(new Font(14));
                elenco.getChildren().add(labelCandidato);
            }
        }
    }

    /**
     * Inserisce tutte le checkBox per selezionare la preferenza
     */
    void insertChoices () {
        if (!S.isVotazionePartiti()) {
            List<VotoOrdinaleController.CandidatoStringato> candidati =
                    lista.values().stream().flatMap(List::stream).map(
                            VotoOrdinaleController.CandidatoStringato::new
                    ).collect(Collectors.toList());
            int i = 0;
            for (VotoOrdinaleController.CandidatoStringato candidato : candidati) {
                CheckBox cb = new CheckBox(candidato.toString());
                cb.setId(String.valueOf(i));
                cb.setPadding(new Insets(0,0,10,0));
                cb.setFont(new Font(15));
                candidatiIdCB[i] = candidato.getCandidato();
                cb.setOnAction(
                        action -> {
                            if (selectedBox!=null)
                                selectedBox.setSelected(false);
                            if (((CheckBox)action.getSource()).isSelected())
                                selectedBox = (CheckBox) action.getSource();
                            else
                                selectedBox = null;
                        }
                );
                opzioni.getChildren().add(cb);
                i++;
            }
        }
    }

    @FXML
    public void vota() {
        Alert a = new Alert(
                Alert.AlertType.CONFIRMATION,
                "",
                new ButtonType("Conferma"),
                new ButtonType("Cambia voto")
        );
        a.setTitle("Conferma");
        a.setHeaderText("Vuoi confermare il seguente voto:");
        a.setContentText( selectedBox!=null ? selectedBox.getText() : "Scheda bianca" );
        Optional<ButtonType> res = a.showAndWait();
        if (res.get().getText().equals("Conferma")) {
            confermaVoto();
        }
    }

    @FXML
    public void confermaVoto() {
        if (selectedBox!=null) { // SCHEDA NON BIANCA
            VotazioneCandidatiDAOImpl.getInstance().votaCandidato(
                    candidatiIdCB[Integer.parseInt(selectedBox.getId())],
                    S,
                    E
            );
        }
        VotazioneElettoreDAOImpl.getInstance().votoElettore(
                new VotazioneElettore(E, S.getId())
        );
        genitoreController.aggiornaSessioni();
        App.getStage().setScene(genitore);
    }

}
