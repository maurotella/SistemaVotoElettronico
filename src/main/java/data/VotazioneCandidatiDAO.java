package data;

import models.Candidato;
import models.Elettore;
import models.Sessione;

import java.util.Map;

public interface VotazioneCandidatiDAO {

    /**
     * Aggiunge a tutti i candidati messi come chiave di C
     * il numero di voti corrispondente alla chiave, votato
     * dall'elettore E
     *
     * @param C mappa candidati-nr. voti
     * @param S sessione
     * @param E elettore
     */
    void votaCandidato(Map<Candidato, Integer> C, Sessione S, Elettore E);

    /**
     * Aggiunge una votazione candidato con Candidato C e sessione S
     *
     * @param S la sessione
     * @param C il candidato
     */
    void addVotazioneCandidato(Sessione S, Candidato C);

}
