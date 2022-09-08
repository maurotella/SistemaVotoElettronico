package models;

import java.time.LocalDate;

public class SessioneBuilder {
    private int id;
    private String titolo;
    private LocalDate dataApertura;
    private LocalDate dataChiusura;
    private TipoVotazione tipoVotazione;

    private boolean votazionePartiti;
    private TipoScrutinio tipoScrutinio;
    private String gestore;

    private SessioneBuilder (int id) {
        this.id = id;
    }

    public static SessioneBuilder newBuilder (int id) {
        return new SessioneBuilder(id);
    }

    public SessioneBuilder titolo (String t) {
        titolo = t;
        return this;
    }

    public SessioneBuilder dataApertura (LocalDate da) {
        dataApertura = da;
        return this;
    }

    public SessioneBuilder dataChiusura (LocalDate dc) {
        dataChiusura = dc;
        return this;
    }

    public SessioneBuilder tipoVotazione (TipoVotazione t) {
        tipoVotazione = t;
        return this;
    }

    public SessioneBuilder votazionePartiti (boolean vP) {
        votazionePartiti = vP;
        return this;
    }

    public SessioneBuilder tipoScrutinio (TipoScrutinio t) {
        tipoScrutinio = t;
        return this;
    }

    public SessioneBuilder gestore (String g) {
        gestore = g;
        return this;
    }

    public Sessione build() {
        return new Sessione(id, titolo, dataApertura, dataChiusura,
                tipoVotazione, votazionePartiti, tipoScrutinio, gestore);
    }

}
