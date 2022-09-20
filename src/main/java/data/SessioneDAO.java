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


}
