package models;

public class Gestore extends Utente {

    /**
     * Crea il gestore con codice fiscale CF e username u
     *
     * @param CF codice fiscale
     * @param u username
     */
    public Gestore (String CF, String u){
        super(CF, u);
    }

    @Override
    public TipoUtente tipoUtente() {
        return TipoUtente.GESTORE;
    }

    public String toString() {
        return super.toString()
                .replaceFirst("Utente", "Gestore");
    }

}
