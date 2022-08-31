package models;

import java.time.LocalDate;

public class Persona {

    private String nome;
    private String cognome;
    // Codice fiscale
    private String CF;
    private String luogoNascita;
    private LocalDate dataNascita;

    /**
     * Costruttore di persona
     *
     * @param n nome
     * @param c cognome
     * @param cf codice fiscale
     * @param ln luogo di nascita
     * @param dn data di nascita
     */
    public Persona (String n, String c, String cf, String ln, LocalDate dn) {
        if (n==null || n.length()==0)
            throw new IllegalArgumentException("Nome vuoto o null");
        if (c==null || c.length()==0)
            throw new IllegalArgumentException("Cognome vuoto o null");
        if (cf==null || cf.length()!=16)
            throw new IllegalArgumentException("Il codice fiscale deve essere di 16 caratteri");
        if (ln==null || ln.length()==0)
            throw new IllegalArgumentException("Luogo di nascita vuoto o null");
        if (dn==null || dn.getYear()>=LocalDate.now().getYear())
            throw new IllegalArgumentException("La data di nascita non puo essere nel futuro");
        nome = n;
        cognome = c;
        CF = cf;
        luogoNascita = ln;
        dataNascita = dn;
    }

    /**
     * restituisce il nominativo della persona
     * (nome cognome)
     *
     * @return il nominativo
     */
    public String getNominativo (){
        return String.format(
                "%s %s",
                nome,
                cognome
        );
    }

    @Override
    public String toString () {
        return String.format(
                "%s %s nato il %s a %s\nCF: %s",
                nome,
                cognome,
                dataNascita.toString(),
                luogoNascita,
                CF
        );
    }

    public static void main (String[] Args) {
        System.out.println(
                new Persona(
                        "Mauro",
                        "Rossi",
                        "TLLMRA99L13H264O",
                        "Rho",
                        LocalDate.of(1999,7,13)
                )
        );
    }

}
