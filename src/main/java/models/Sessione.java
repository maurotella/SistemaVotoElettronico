package models;

import java.time.LocalDate;

public class Sessione {

    private final int id;
    private final String titolo;
    private final LocalDate dataApertura;
    private final LocalDate dataChiusura;
    private final TipoVotazione tipoVotazione;
    private final TipoScrutinio tipoScrutinio;
    private boolean chiusa;
    private final Gestore gestore;

    public Sessione (
            int i,
            String t,
            LocalDate da,
            LocalDate dc,
            TipoVotazione tv,
            TipoScrutinio ts,
            Gestore g
    ) {
        if (t==null || t.length()==0)
            throw new IllegalArgumentException("titolo null o vuoto");
        if (da==null)
            throw new IllegalArgumentException("dataApertura null");
        if (dc==null)
            throw new IllegalArgumentException("dataChiusura null");
        if (dc.compareTo(da)<=0)
            throw new IllegalArgumentException("dataChiusura deve essere successiva a dataChiusura");
        if (tv==null)
            throw new IllegalArgumentException("tipoVotazione null");
        if (ts==null)
            throw new IllegalArgumentException("tipoScrutinio null");
        if (g==null)
            throw new IllegalArgumentException("gestore null");
        if (tv==TipoVotazione.REFERENDUM && !(ts==TipoScrutinio.REFERENDUM || ts==TipoScrutinio.REFERENDUM_QUORUM))
            throw new IllegalArgumentException("Tipo votazione e scrutinio incompatibili");
        if (tv!=TipoVotazione.REFERENDUM && (ts==TipoScrutinio.REFERENDUM || ts==TipoScrutinio.REFERENDUM_QUORUM))
            throw new IllegalArgumentException("Tipo votazione e scrutinio incompatibili");
        id = i;
        titolo = t;
        dataApertura = da;
        dataChiusura = dc;
        tipoVotazione = tv;
        tipoScrutinio = ts;
        chiusa = false;
        gestore = g;
    };

    /**
     * Chiude la sessione di voto
     */
    public void chiudi () {
        chiusa = true;
    }

    /**
     * Indica se la sessione è chiusa o meno
     *
     * @return true se la sessione è chiusa, false altrimenti
     */
    public boolean chiusa () {
        return chiusa;
    }

    @Override
    public String toString() {
        return "Sessione{" + "\n" +
                "  id=" + id + "\n" +
                "  titolo='" + titolo + "\'\n" +
                "  dataApertura=" + dataApertura + "\n" +
                "  dataChiusura=" + dataChiusura + "\n" +
                "  tipoVotazione=" + tipoVotazione + "\n" +
                "  tipoScrutinio=" + tipoScrutinio + "\n" +
                "  chiusa=" + chiusa + "\n" +
                "  gestore=" + gestore.getUsername() + "\n" +
                '}';
    }

}
