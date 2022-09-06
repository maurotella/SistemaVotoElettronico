package data;

import models.Sessione;

public interface SessioneDAO {

    /**
     * Dato l'id, restituisce la Sessione
     *
     * @param id l'id
     * @return la sessione con quell'id
     */
    Sessione getSessione(int id);

}
