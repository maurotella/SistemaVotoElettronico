package models;

public class Elettore extends Utente {

    //Indica se ha diritto di voto
    private final boolean dirittoVoto;
    //Indica se ha diritto di voto a distanza
    private final boolean dirittoVotoDistanza;

    /**
     * Crea l'elettore con codice fiscale cf,
     * diritto di voto dv e diritto di voto a distanza dvd
     *
     * @param cf codice fiscale
     * @param dv diritto di voto
     * @param dvd diritto di voto a distanza
     */
    public Elettore (String cf, boolean dv, boolean dvd){
        super(cf);
        dirittoVoto = dv;
        dirittoVotoDistanza = dvd;
    }

    /**
     * Indica se l'elettore ha il diritto di voto
     *
     * @return booleano che indica il diritto di voto
     */
    public boolean dirittoVoto() {
        return dirittoVoto;
    }

    /**
     * Indica se l'elettore ha il diritto di voto
     *
     * @return booleano che indica il diritto di voto
     */
    public boolean dirittoVotoDistanza() {
        return dirittoVotoDistanza;
    }

    @Override
    public TipoUtente tipoUtente() {
        return TipoUtente.ELETTORE;
    }

    @Override
    public String toString() {
        return "Elettore{\n" +
                "    CF=" + super.getCF() + "\n" +
                "    dirittoVoto=" + dirittoVoto + "\n" +
                "    dirittoVotoDistanza=" + dirittoVotoDistanza + "\n" +
                '}';
    }
}
