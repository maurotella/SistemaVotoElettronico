package models;

public class Gestore extends Utente {

    /**
     * Crea il gestore con codice fiscale CF
     *
     * @param CF codice fiscale
     */
    public Gestore (String CF){
        super(CF);
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
