package data;

import models.Referendum;
import models.Sessione;

public interface SessioneDAO {

    /**
     * Dato l'id, restituisce la Sessione
     *
     * @param id l'id
     * @return la sessione con quell'id
     */
    Sessione getSessione(int id);

    /**
     * Dato l'id della Sessione, restituisce
     * l'eventuale Referendum corrispondente
     *
     * @param id l'id della sessione
     * @return il referendum con quell'id se la sessione
     *         è un referendum, null altrimenti
     */
    Referendum getReferendum(int id);

}
