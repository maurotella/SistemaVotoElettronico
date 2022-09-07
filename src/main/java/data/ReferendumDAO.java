package data;

import models.Sessione;

public interface ReferendumDAO {

    void votaSi (Sessione S);

    void votaNo (Sessione S);

}
