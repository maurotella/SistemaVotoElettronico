package models;
/**
 * Classe che tiene i conti sulle votazioni relative a un referendum
 */
public class VotiReferendum extends Voti {

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
    public VotiReferendum (Sessione s, String q) {
        super(s);
        if (q==null || q.length()==0)
            throw new IllegalArgumentException("Quesito mancante");
        quesito = q;
        votiFavorevoli = 0;
        votiSfavorevoli = 0;
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
     * Aggiunge un voto favorevole
     */
    public void addSi () {
        votiFavorevoli++;
    };

    /**
     * Aggiunge un voto sfavorevole
     */
    public void addNo () {
        votiSfavorevoli++;
    };

    @Override
    public String toString () {
        return String.format(
          "Referendum: %s\nsi: %d\nno: %d",
                quesito,
                votiFavorevoli,
                votiSfavorevoli
        );
    }

}
