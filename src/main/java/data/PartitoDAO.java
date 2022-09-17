package data;

import models.Candidato;
import models.Partito;

import java.util.ArrayList;
import java.util.HashMap;

public interface PartitoDAO {

    /**
     * Restituisce una mappa con chiavi i partiti e con
     * valore la lista dei candidati di quel partito
     *
     * @return una mappa parito-List<Candidati>
     */
    public HashMap<Partito, ArrayList<Candidato>> getListaPartiti();

}
