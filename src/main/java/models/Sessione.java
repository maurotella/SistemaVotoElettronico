package models;



import java.time.LocalDate;

public class Sessione extends SessioneSemplice {

    //@ invariant dataApertura!=null && dataApertura.compareTo(dataChiusura)<=0;
    private final LocalDate dataApertura;
    //@ invariant dataChiusura!=null && dataChiusura.compareTo(dataApertura)>0;
    private final LocalDate dataChiusura;
    //@ invariant tipoVotazione!=null;
    private final TipoVotazione tipoVotazione;
    private final boolean votazionePartiti;
    //@ invariant tipoScrutinio!=null;
    private final TipoScrutinio tipoScrutinio;
    private boolean chiusa;
    //@ invariant gestore!=null && gestore.length()>0;
    private final String gestore;

    public Sessione (
            int i,
            String t,
            LocalDate da,
            LocalDate dc,
            TipoVotazione tv,
            boolean vP,
            TipoScrutinio ts,
            String g
    ) {
        super(i,t);
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
        dataApertura = da;
        dataChiusura = dc;
        tipoVotazione = tv;
        votazionePartiti = vP;
        tipoScrutinio = ts;
        chiusa = false;
        gestore = g;
    };

    public TipoVotazione getTipoVotazione() {
        return tipoVotazione;
    }

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

    public LocalDate getDataApertura() {
        return dataApertura;
    }

    public LocalDate getDataChiusura() {
        return dataChiusura;
    }

    public boolean isVotazionePartiti() {
        return votazionePartiti;
    }

    public TipoScrutinio getTipoScrutinio() {
        return tipoScrutinio;
    }

    public String getGestore() {
        return gestore;
    }

    @Override
    public String toString() {
        return "Sessione{\n" +
                "    id=" + getId() + "\n" +
                "    titolo='" + getTitolo() + '\'' + "\n" +
                "    dataApertura=" + dataApertura + "\n" +
                "    dataChiusura=" + dataChiusura + "\n" +
                "    tipoVotazione=" + tipoVotazione + "\n" +
                "    votazionePartiti=" + votazionePartiti + "\n" +
                "    tipoScrutinio=" + tipoScrutinio + "\n" +
                "    chiusa=" + chiusa + "\n" +
                "    gestore=" + gestore + "\n" +
                '}';
    }

}
