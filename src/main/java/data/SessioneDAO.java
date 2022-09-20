package data;

import models.Candidato;
import models.Partito;
import models.Referendum;
import models.Sessione;

import java.util.ArrayList;
import java.util.HashMap;

public interface SessioneDAO {

    /**
     * Dato l'id, restituisce la Sessione
     *
     * @param id l'id
     * @return la sessione con quell'id
     */
    Sessione getSessione(int id);

    /**
     * Dato l'id della sessione,
     * restituisce l'eventuale referendum collegato
     *
     * @param id id della sessione
     * @return il referendum della sessione se di tipo referendum,
     *         null altrimenti
     */
    Referendum getReferendum(int id);

    /**
     * Restituisce una mappa contenente la lista dei partiti
     * i cui membri si candidano alla sessione S
     *
     * @param S la sessione
     * @return la mappa dei partiti-candidati
     */
    HashMap<Partito, ArrayList<Candidato>> getListaCandidati(Sessione S);

    /**
     * Restituisce il numero dei votanti della sessione S
     *
     * @param S la sessione
     * @return il nr. dei votanti
     */
    int nrVotanti(Sessione S);

    /**
     * Restituisce il numero di elettori totali
     *
     * @return il nr. di elettori totali
     */
    int nrElettori();

    /**
     * Verifica che la sessione S sia chiusa o meno
     *
     * @param S la sessione
     * @return true se la sessione è chiusa, false altrimenti
     */
    boolean chiusa(Sessione S);

    /**
     * Verifica se la Sessione S, con scrutinio referendumQuorum,
     * ha raggiunto il quorum
     *
     * @param S la sessione
     * @return true se la sessione S ha come scrutinio rQuorum
     *         e ha raggiunto il quorum, false altrimenti
     */
    boolean quorum(Sessione S);

    /**
     * Restituisce una mappa contenente i risultati del referendum:
     *  true = voti positivi, false = voti negativi
     *
     * @return gli esiti
     */
    HashMap<Boolean, Integer> esitiReferendum(Sessione S);

    /**
     * Restituisce una mappa contenente i risultati della votazione:
     *  true = voti positivi, false = voti negativi
     *  { Partito1 : { Candidato1 : n, Candidato2 : m ... } }
     * @param S la sessione
     * @return gli esiti della sessione
     */
    HashMap<Candidato, Integer> esitiVotoCandidati(Sessione S);

    /**
     * Indica il raggiungimento della maggioranza assoluta in una sessione
     * con tipo di scrutinio Maggioranza_assoluta
     *
     * @param S la sessione
     * @return true se il tipo di scrutinio è Maggioranza_assoluta
     *         e si è raggiunta la magg. assol, false altrimenti
     */
    boolean maggioranza(Sessione S);

}
