package data;

import models.Candidato;
import models.Elettore;
import models.Partito;
import models.Sessione;

import java.util.Map;

public interface VotazioniPartitiDAO {

    void votaPartito(Map<Partito, Integer> C, Sessione S, Elettore E);

}
