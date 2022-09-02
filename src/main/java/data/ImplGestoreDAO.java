package data;

import models.Gestore;
import models.Sessione;
import java.util.List;

/**
 * Classe che utilizza il pattern SINGLETON
 */
public class ImplGestoreDAO implements GestoreDAO {

    private static ImplGestoreDAO istance = null;

    private ImplGestoreDAO() {};

    /**
     * Classe che implemente il pattern singleton
     *
     * @return l'unica istanza
     */
    public static ImplGestoreDAO getIstance() {
        if (istance==null)
            istance = new ImplGestoreDAO();
        return istance;
    };

    @Override
    public Gestore login(String username, String password) {
        return null;
    }

    @Override
    public List<Sessione> getSessioni(Gestore G) {
        return null;
    }
}
