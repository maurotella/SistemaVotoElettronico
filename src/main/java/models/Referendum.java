package models;

import data.ReferendumDAOImpl;
import data.VotazioneElettoreDAOImpl;

/**
 * Classe che tiene i conti sulle votazioni relative a un referendum
 */
public class Referendum extends Votazione {

    // quesito referendario
    private String quesito;
    // numero voti favorevoli
    private int votiFavorevoli;
    // numero voti sfavorevoli
    private int votiSfavorevoli;

    /**
     * Costruttore del referendum inizializzato con
     * 0 voti positivi e 0 voti negativi.
     *
     * @param s sessione relativa alla votazione
     * @param q quesito referendum
     */
    public Referendum(Sessione s, String q) {
        super(s);
        if (q==null || q.length()==0)
            throw new IllegalArgumentException("Quesito mancante");
        quesito = q;
        votiFavorevoli = 0;
        votiSfavorevoli = 0;
    }

    /**
     * Costruttore del referendum inizializzato con
     * p voti positivi e n voti negativi.
     *
     * @param s sessione relativa alla votazione
     * @param q quesito referendum
     */
    public Referendum(Sessione s, String q, int p, int n) {
        super(s);
        if (q==null || q.length()==0)
            throw new IllegalArgumentException("Quesito mancante");
        if (p<0 || n<0)
            throw new IllegalArgumentException("Il numero di voti non può essere negativo");
        quesito = q;
        votiFavorevoli = p;
        votiSfavorevoli = n;
    }

    /**
     * Restituisce il quesito referendario
     *
     * @return quesito referendario
     */
    public String getQuesito () {
        return quesito;
    }

    /**
     * Restituisce i voti sfavorevoli
     *
     * @return voti sfavorevoli
     */
    public int votiSfavorevoli () {
        return votiSfavorevoli;
    }

    /**
     * Restituisce i voti favorevoli
     *
     * @return voti favorevoli
     */
    public int votiFavorevoli () {
        return votiFavorevoli;
    }

    /**
     * Aggiunge un voto favorevole espresso
     * dall'elettore E
     *
     * @param E elettore
     * @return il VotoElettore corrispondente
     */
    public VotazioneElettore addSi (Elettore E) {
        votiFavorevoli++;
        ReferendumDAOImpl.getInstance().votaSi(getSessione(),E);
        return creaVotoElettore(E);
    };

    /**
     * Aggiunge un voto sfavorevole espresso
     * dall'elettore E
     *
     * @param E elettore
     * @return il VotoElettore corrispondente
     */
    public VotazioneElettore addNo (Elettore E) {
        votiSfavorevoli++;
        ReferendumDAOImpl.getInstance().votaNo(getSessione(), E);
        return creaVotoElettore(E);
    };

    /**
     * Restituisce la VotazioneElettore senza aggiungere
     * nessun voto al Referendum
     *
     * @param E Elettore
     * @return la VotazioneElettore
     */
    public VotazioneElettore schedaBianca (Elettore E) {
        return creaVotoElettore(E);
    }

    private VotazioneElettore creaVotoElettore(Elettore E) {
        return new VotazioneElettore(
                E,
                getSessione().getId()
        );
    }

    @Override
    public String toString () {
        return String.format(
          "Referendum: %s\nsi: %d\nno: %d",
                quesito,
                votiFavorevoli,
                votiSfavorevoli
        );
    }

    public static enum Risposte {
        SI,
        NO,
        SCHEDA_BIANCA
    }

}