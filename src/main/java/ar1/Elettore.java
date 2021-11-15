package ar1;

// Definire una classe Elettore che contenga i seguenti attributi e metodi:
//     • nome e cognome
//     • data di nascita (per comodità potete esprimere la data con 3 interi, uno per esprimere il giorno,
//     uno per il mese e uno per l’anno)
//     • luogo (comune) di nascita
//     • nazione di nascita
//     • sesso
//     • codice fiscale (espresso come array di caratteri)
//     • voto, un booleano che ha valore true nel caso in cui l’elettore abbia espresso il suo voto, false
//     nel caso in cui l’elettore non abbia ancora espresso il voto
//     • metodo esprimi_voto, di cui non è necessario dare l’implementazione completa (non mi
//     interessa sapere cosa abbia votato l’elettore, ma che abbia votato. Serve solo per poter specificare
//     alcune condizioni JML)


public class Elettore extends Persona {
    public final char sesso;
    public final String comune, nazione;
    public final boolean voto;
    public Elettore (
        String nome, String cognome, GregorianCalendar dataNascita, String cf, 
            char sesso, String comune, String nazione, boolean voto
        ){
            super(nome, cognome, dataNascita, cf);
                
    }
}
