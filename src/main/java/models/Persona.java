package models;

import java.time.LocalDate;

public class Persona {

    //@ invariant nome!=null && nome.length()>0;
    private final String nome;
    //@ invariant cognome!=null && cognome.length()>0;
    private final String cognome;
    // Codice fiscale
    //@ invariant CF!=null && CF.length()>0 && checkCF(nome,cognome,dataNascita,sesso,nazionalita,CF);
    private final String CF;
    //@ invariant sesso=='M' || sesso=='F';
    private final char sesso;
    // nazionalità in formato iso (3 lettere)
    //@ invariant nazionalita!=null && nazionalita.length()==3;
    private final String nazionalita;
    //@ invariant luogoNascita!=null && luogoNascita.length()>0;
    private final String luogoNascita;
    //@ invariant dataNascita!=null && dataNascita.compareTo(LocalDate.now())<0;
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
        if (!checkCF(n,c,dn,s,nz,cf))
            throw new IllegalArgumentException("Il codice fiscale non corrisponde");
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

    /**
     * Controlla che C sia una vocale
     *
     * @param C la lettera da controllare
     * @return true se C è una vocale, false altrimenti
     */
    private static boolean isVocale (char C) {
        char c = Character.toLowerCase(C);
        if (c=='a' || c=='e' || c=='i' || c=='o' || c=='u')
            return true;
        return false;
    }

    /**
     * Restituisce la lettera del CF relativa al mese m
     *
     * @param m mese
     * @return lettera del mese
     */
    private static char meseCF (int m) {
        char[] A = {'A','B','C','D','E','H','L','M','P','R','S','T'};
        return A[m-1];
    }

    /**
     * Controlla la corrispondenza tra i dati della persona
     * e codiceFiscale
     *
     * @param nome
     * @param cognome
     * @param dataNascita
     * @param sesso
     * @param nazione
     * @param codiceFiscale
     * @return true se il CF è corretto, false altrimenti
     */
    private static boolean checkCF (
            String nome, String cognome, LocalDate dataNascita,
            char sesso, String nazione, String codiceFiscale) {

        StringBuilder buffer = new StringBuilder();
        for (int i=0; i<cognome.length() && i<=3 ; i++) {
            if (!isVocale(cognome.charAt(i)))
                buffer.append(Character.toUpperCase(cognome.charAt(i)));
        }
        for (int i=0; buffer.length()<3 && i<cognome.length() ; i++) {
            if (isVocale(cognome.charAt(i)))
                buffer.append(Character.toUpperCase(cognome.charAt(i)));
        }
        while (buffer.length()<3) {
            buffer.append("X");
        }
        String auxNome;
        if (nome.toUpperCase().contains(",")) {
            auxNome = nome.split(",")[0];
        } else {
            auxNome = nome;
        }
        for (int i=0; i<auxNome.length() && i<=6 ; i++) {
            if (!isVocale(auxNome.charAt(i)))
                buffer.append(Character.toUpperCase(auxNome.charAt(i)));
        }
        for (int i=0; buffer.length()<6 && i<auxNome.length() ; i++) {
            if (isVocale(auxNome.charAt(i)))
                buffer.append(Character.toUpperCase(auxNome.charAt(i)));
        }
        while (buffer.length()<6) {
            buffer.append("X");
        }
        buffer.append(String.valueOf(dataNascita.getYear()).charAt(2));
        buffer.append(String.valueOf(dataNascita.getYear()).charAt(3));
        buffer.append(meseCF(dataNascita.getMonthValue()));
        if (sesso=='F') {
            buffer.append(dataNascita.getDayOfMonth()+40);
        } else {
            if (dataNascita.getDayOfMonth()<10) {
                buffer.append("0"+dataNascita.getDayOfMonth());
            } else {
                buffer.append(dataNascita.getDayOfMonth());
            }
        }
        if (!buffer.toString().equals(String.valueOf(codiceFiscale).substring(0,11))){
            return false;
        }
        if (nazione!="ITA" && codiceFiscale.charAt(11)!='Z') {
            return false;
        }
        if (!(Character.isLetter(codiceFiscale.charAt(11))
                && Character.isDigit(codiceFiscale.charAt(12))
                && Character.isDigit(codiceFiscale.charAt(13))
                && Character.isDigit(codiceFiscale.charAt(14))
                && Character.isLetter(codiceFiscale.charAt(15)))) {
            return false;
        }
        return true;
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
