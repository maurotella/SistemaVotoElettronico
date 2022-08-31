package models;

public class Elettore {

    //Persona relativa all'elettore
    private Persona P;
    //Username dell'elettore
    private String username;
    //Indica se ha diritto di voto
    private boolean dirittoVoto;
    //Indica se ha diritto di voto a distanza
    private boolean dirittoVotoDistanza;

    public Elettore (Persona p, String u, boolean dv, boolean dvd){
        if (p==null)
            throw new IllegalArgumentException("Persona null");
        if (u==null || u.length()==0)
            throw new IllegalArgumentException("Username vuoto o null");
        username = u;
        P = p;
        dirittoVoto = dv;
        dirittoVotoDistanza = dvd;
    }

    /**
     * Indica se l'elettore ha il diritto di voto
     *
     * @return booleano che indica il diritto di voto
     */
    public boolean dirittoVoto () {
        return dirittoVoto;
    }

    /**
     * Indica se l'elettore ha il diritto di voto
     *
     * @return booleano che indica il diritto di voto
     */
    public boolean dirittoVotoDistanza () {
        return dirittoVotoDistanza;
    }

    @Override
    public String toString (){
        return String.format(
                "Elettore: %s\n%s",
                username,
                P.getNominativo()
        );
    }

}
