package controllers;

import data.PersonaDAOImpl;
import data.SessioneDAOImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.*;

import java.util.*;
import java.util.stream.Collectors;

public class VotoOrdinaleController {

    Scene genitore;
    ElettoreController genitoreController;
    Sessione S;
    Elettore E;
    HashMap<Partito, ArrayList<Candidato>> lista;

    static LinkedList<ChoiceBox<CandidatoStringato>> choiceBoxes;

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
                        s.isVotazionePartiti() ? "partiti" : "candidati"
                        )
        );
        S = s;
        E = e;
        lista = SessioneDAOImpl.getInstance().getListaCandidati(s);
        choiceBoxes = new LinkedList<>();
        insertChoice();
        insertElenco();
    }

    /**
     * Inserisce tutte le choiceBox per selezionare l'ordine
     */
    void insertChoice () {
        if (!S.isVotazionePartiti()) {
            List<CandidatoStringato> candidati =
                    lista.values().stream().flatMap(List::stream).map(
                            CandidatoStringato::new
                    ).collect(Collectors.toList());
            int i = 1;
            for (CandidatoStringato ignored : candidati) {
                HBox h = new HBox();
                opzioni.getChildren().add(h);
                h.setAlignment(Pos.CENTER);
                VBox.setVgrow(h, Priority.ALWAYS);
                Label l = new Label();
                h.getChildren().add(l);
                l.setText((i++)+"°");
                l.setFont(new Font(20));
                ChoiceBox<CandidatoStringato> cb = new ChoiceBox<>();
                choiceBoxes.add(cb);
                cb.setMinWidth(150);
                l.setPadding(new Insets(0,10,0,0));
                h.getChildren().add(cb);
                cb.setId(String.valueOf(i));
                cb.setItems(
                        FXCollections.observableList(new ArrayList<>(candidati))
                );
                cb.getSelectionModel().selectedItemProperty().addListener(
                        new CBListener(cb)
                );
            }
        } else {

        }
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
            List<CandidatoStringato> candidatiPartito =
                    lista.get(partito).stream().map(CandidatoStringato::new).toList();
            for (CandidatoStringato candidato : candidatiPartito) {
                Label labelCandidato = new Label();
                labelCandidato.setText("- "+candidato.toString());
                labelCandidato.setFont(new Font(14));
                elenco.getChildren().add(labelCandidato);
            }
        }
    }

    @FXML
    void vota() {
        choiceBoxes.getFirst().getItems().remove(1);
        System.out.println(choiceBoxes.getFirst());
    }
    private static class CandidatoStringato {
        private final Candidato candidato;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CandidatoStringato that = (CandidatoStringato) o;
            return candidato.equals(that.candidato);
        }

        @Override
        public int hashCode() {
            return Objects.hash(candidato);
        }

        public CandidatoStringato(Candidato c) {
            candidato = c;
        }

        public String toString() {
            return PersonaDAOImpl.getInstance().getNominativo(candidato.getPersona());
        }

    }

    /**
     * Listener per i choiceBox che davanti a una nuova scelta,
     * toglie il valore scelto dalle altre choiceBox
     */
    private static class CBListener implements ChangeListener<CandidatoStringato> {
        private final ChoiceBox<CandidatoStringato> CB;
        CBListener (ChoiceBox<CandidatoStringato> cb) {
            CB = cb;
        }
        @Override
        public void changed(ObservableValue<? extends CandidatoStringato> observable, CandidatoStringato oldValue, CandidatoStringato newValue) {
            for (ChoiceBox<CandidatoStringato> cb : choiceBoxes) {
                if (!cb.equals(CB) && cb.getValue()!=null && cb.getValue().equals(newValue)) {
                    cb.setValue(null);
                }
            }
        }
    }

}

