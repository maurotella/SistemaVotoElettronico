package models;
/**
 * Classe astratta che fornisce le basi di un'utenza
 * che potrà essere Gestore o Elettore
 */
public abstract class Utente {

    private final String CF;

    /**
     * Crea un utente con CF
     *
     * @param CF
     */
    public Utente(String CF) {
        if (CF==null || CF.length()==0)
            throw new IllegalArgumentException("CF null o vuoto");
        this.CF = CF;
    }

    public String getCF() {
        return CF;
    }

    /**
     * Indica il tipo di utenza
     *
     * @return tipo di utenza
     */
    public abstract TipoUtente tipoUtente();

    @Override
    public String toString() {
        return "Utente{\n" +
                "    CF='" + CF + '\'' + "\n" +
                '}';
    }

    public enum TipoUtente {
        ELETTORE,
        GESTORE
    }

}
