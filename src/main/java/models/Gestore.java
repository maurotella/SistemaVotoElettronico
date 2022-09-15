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

    /**
     * @return CF di this
     */

    @Override
    public TipoUtente tipoUtente() {
        return TipoUtente.GESTORE;
    }

    /**
     *
     * @return CF del Gestore (this)
     */
    public String getCF (){return  super.getCF();}

    public String toString() {
        return super.toString()
                .replaceFirst("Utente", "Gestore");
    }

}
