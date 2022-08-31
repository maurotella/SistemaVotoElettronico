package models;

import java.util.ArrayList;

public class Gestore {

    //Persona relativa all'elettore
    private final Persona P;
    //Username dell'elettore
    private String username;
    //Sessioni create dal gestore
    private ArrayList<Integer> sessioni;

    public Gestore (Persona p, String u){
        if (p==null)
            throw new IllegalArgumentException("Persona null");
        if (u==null || u.length()==0)
            throw new IllegalArgumentException("Username vuoto o null");
        username = u;
        P = p;
        sessioni = new ArrayList<Integer>();
    }

    /**
     * Resituisce la persona relativa al gestore
     *
     * @return persona
     */
    public Persona getPersona () {
        return P;
    }

    @Override
    public String toString (){
        return String.format(
                "Gestore: %s\n%s",
                username,
                P.getNominativo()
        );
    }

}
