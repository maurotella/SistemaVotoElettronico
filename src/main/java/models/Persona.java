package models;

import java.time.LocalDate;

public class Persona {

    private final String nome;
    private final String cognome;
    // Codice fiscale
    private final String CF;
    private final char sesso;
    private final String nazionalita;
    private final String luogoNascita;
    private final LocalDate dataNascita;

    /**
     * Crea una persona
     *
     * @param n nome
     * @param c cognome
     * @param cf codice fiscale
     * @param s sesso
     * @param nz nazionalita ISO (3 lettere)
     * @param ln luogo di nascita
     * @param dn data di nascita
     */
    public Persona (String n, String c, String cf, char s, String nz, String ln, LocalDate dn) {
        if (n==null || n.length()==0)
            throw new IllegalArgumentException("Nome vuoto o null");
        if (c==null || c.length()==0)
            throw new IllegalArgumentException("Cognome vuoto o null");
        if (cf==null || cf.length()!=16)
            throw new IllegalArgumentException("Il codice fiscale deve essere di 16 caratteri");
        if (!(s=='M' || s=='F'))
            throw new IllegalArgumentException("Il sesso deve essere M o F");
        if (nz==null || nz.length()==0)
            throw new IllegalArgumentException("La nazionalita è null o vuota");
        if (nz.length()!=3)
            throw new IllegalArgumentException("Scrivere la nazionalita in formato iso (3 lettere)");
        if (ln==null || ln.length()==0)
            throw new IllegalArgumentException("Luogo di nascita vuoto o null");
        if (dn==null || dn.getYear()>=LocalDate.now().getYear())
            throw new IllegalArgumentException("La data di nascita non puo essere nel futuro");
        nome = n;
        cognome = c;
        CF = cf;
        sesso = s;
        nazionalita = nz;
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

    /**
     * Restituisce il codice fiscale
     *
     * @return codice fiscale
     */
    public String getCF() {
        return CF;
    }

    @Override
    public String toString () {
        return String.format(
                "%s %s (%s) nato il %s a %s\nCF: %s",
                nome,
                cognome,
                sesso,
                dataNascita.toString(),
                luogoNascita,
                CF
        );
    }

}
