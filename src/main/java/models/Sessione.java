package models;



import java.time.LocalDate;

public class Sessione extends SessioneSemplice {

    private final LocalDate dataApertura;
    private final LocalDate dataChiusura;
    private final TipoVotazione tipoVotazione;
    private final TipoScrutinio tipoScrutinio;
    private boolean chiusa;
    private final String gestore;

    public Sessione (
            int i,
            String t,
            LocalDate da,
            LocalDate dc,
            TipoVotazione tv,
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
        tipoScrutinio = ts;
        chiusa = false;
        gestore = g;
    };

    /*Funzioni getter della sessione
    _________________________________________________________**/

    /**
     * @return data di apertura della sessione this
     */
    public LocalDate getDataApertura(){return this.dataApertura;}

    /**
     * @return data di chiusura della sessione this
     */
    public LocalDate getDataChiusura(){return this.dataChiusura;}

    /**
     * @return tipo di votazione di this
     */
    public TipoVotazione getTipoVotazione(){return this.tipoVotazione;}

    /**
     * @return tipo di scrutinio di this
     */
    public TipoScrutinio getTipoScrutinio(){ return this.tipoScrutinio;}
    //____________________________________________________________


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
        return "Sessione{\n" +
                "    id=" + getId() + "\n" +
                "    titolo='" + getTitolo() + '\'' + "\n" +
                "    dataApertura=" + dataApertura + "\n" +
                "    dataChiusura=" + dataChiusura + "\n" +
                "    tipoVotazione=" + tipoVotazione + "\n" +
                "    tipoScrutinio=" + tipoScrutinio + "\n" +
                "    chiusa=" + chiusa + "\n" +
                "    gestore=" + gestore + "\n" +
                '}';
    }
}
