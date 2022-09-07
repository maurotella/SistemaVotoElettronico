package models;
/**
 * Classe astratta usata dalle varie classi che tengono conto
 * delle votazioni
 */
public abstract class Votazione {

    // sessione relativa ai voti
    private Sessione sessione;
    // numero di votazioni effetuate dagli elettori
    private int numeroVoti;

    /**
     * Costruttore astratto di un contatore di voti
     *
     * @param s sessione relativa
     */
    public Votazione(Sessione s) {
        if (s==null)
            throw new IllegalArgumentException("Sessione null");
        sessione = s;
        numeroVoti = 0;
    }

    /**
     * Restituisce la sessione relativa ai voti
     *
     * @return la sessione
     */
    public Sessione getSessione () {
        return sessione;
    }

    /**
     * Restituisce il numero di votazioni effetuate
     *
     * @return numero di votazioni
     */
    public int getNumeroVoti () {
        return numeroVoti;
    }

}
