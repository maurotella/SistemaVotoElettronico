package example;

import data.ElettoreDAOImpl;
import data.GestoreDAOImpl;
import data.ReferendumDAOImpl;
import data.SessioneDAOImpl;
import models.Sessione;
import models.SessioneBuilder;
import models.TipoScrutinio;
import models.TipoVotazione;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class DbTest {

    private final Sessione sessione =
            SessioneBuilder.newBuilder(34)
            .titolo("Test")
            .gestore("E")
            .dataApertura(LocalDate.now())
            .dataChiusura(LocalDate.of(2022,9,25))
            .tipoVotazione(TipoVotazione.REFERENDUM)
            .tipoScrutinio(TipoScrutinio.REFERENDUM)
            .votazionePartiti(false)
            .build();

    @DisplayName("Test login elettore")
    @Test
    void test1LoginElettore() {
        ElettoreDAOImpl.getInstance().login("E","E");
    }

    @DisplayName("Test login gestore")
    @Test
    void test2LoginGestore() {
        GestoreDAOImpl.getInstance().login("E","G");
    }


    @DisplayName("Test creazione sessione")
    @Test
    void test3CreaSessione() {
        if (!GestoreDAOImpl.getInstance().checkSessione(sessione)) {
            GestoreDAOImpl.getInstance().addSessione(
                    GestoreDAOImpl.getInstance().login("E", "G"),
                    sessione
            );
        }
    }

    @DisplayName("Test chiusura sessione")
    @Test
    void test4ChiudiSessione() {
        GestoreDAOImpl.getInstance().chiudiSessione(sessione);
    }

    @DisplayName("Test creazione referendum")
    @Test
    void test5CreaReferendum() {
        try {
            ReferendumDAOImpl.getInstance().addReferendum(sessione, "domanda");
        } catch (Exception e) {
            System.out.println("Referendum esiste già");
        }
    }

    @DisplayName("Test verifica chiusura sessione")
    @Test
    void test7SessioneChiusa() {
        SessioneDAOImpl.getInstance().chiusa(sessione);
    }

    @DisplayName("Test recupero esiti sessione")
    @Test
    void test8GetEsiti() {
        SessioneDAOImpl.getInstance().esitiReferendum(sessione);
    }

    @DisplayName("Test recupero numeri votanti")
    @Test
    void test9GetNrVotanti() {
        SessioneDAOImpl.getInstance().nrVotanti(sessione);
    }

}
