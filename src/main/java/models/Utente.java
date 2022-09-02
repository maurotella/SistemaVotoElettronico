package models;
/**
 * Classe astratta che fornisce le basi di un'utenza
 * che potrà essere Gestore o Elettore
 */
public abstract class Utente {

    private final String CF;
    private final String username;

    /**
     * Crea un utente con CF e username
     *
     * @param CF
     * @param username
     */
    public Utente(String CF, String username) {
        if (CF==null || CF.length()==0)
            throw new IllegalArgumentException("CF null o vuoto");
        if (username==null || username.length()==0)
            throw new IllegalArgumentException("Username vuoto o null");
        this.CF = CF;
        this.username = username;
    }

    public String getCF() {
        return CF;
    }

    public String getUsername() {
        return username;
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
                "    username='" + username + '\'' + "\n" +
                '}';
    }

    public enum TipoUtente {
        ELETTORE,
        GESTORE
    }

}
