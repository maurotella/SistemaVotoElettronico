package data;

import models.Elettore;
import models.Sessione;

public interface ReferendumDAO {

    void votaSi (Sessione S, Elettore E);

    void votaNo (Sessione S, Elettore E);

    void addReferendum (Sessione S, String domanda);

}
