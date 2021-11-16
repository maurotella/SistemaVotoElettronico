import java.util.GregorianCalendar;

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

//_________________

// Esprimere in JML le seguenti proprietà (sta a voi decidere se come invarianti, pre- o post- condizioni):
//     • il nome e il cognome non possono essere nulli
//     • un elettore può essere o maschio o femmina
//     • un elettore può votare solo se maggiorenne
//     • un elettore può votare al più una volta
//     • se l’elettore è nato in Italia, allora deve essere indicato anche il comune di nascita
//     • la data di nascita deve essere valida (ovvero non successiva alla data corrente)
//     • il codice fiscale deve essere valido 
//	   (nel vostro caso potete fare un controllo semplificato degli ultimi
//     5 caratteri alfanumerici del codice fiscale, controllando solo che 
//		(i) lettere e numeri compaiano nella giusta posizione e che 
//		(ii) per chi è nato all’estero la lettera sia Z).

//________________
//	precondizioni ai metodi
//	//@ requires <espressione booleana>;
//	postcondizioni ai metodi
//	//@ ensures <espressione booleana>;
//	invarianti di classe
//	//@ invariant <espressione booleana>;

public class Elettore extends Persona {
    public final char sesso;
    public final String comune;
    public final String nazione;
    public boolean voto;
    //@ requires (nome != NULL && cognome != NULL)
    //@ requires (sesso == 'M' || sesso == 'F')
    //@ requires ((Calendar.getInstance().get(Calendar.YEAR) - dataNascita.get(Calendar.YEAR)) >= 18)
    //@ requires (nazione == 'ITA' <==> comune != NULL
    /*@ requires (dataCorrente - dataInserita) > 0 
    	@*/
    //@requires cF_corretto
    
    public Elettore (String nome, String cognome, GregorianCalendar dataNascita, String cf, char sesso, String comune, String nazione){
            super(nome, cognome, dataNascita, cf);
            this.sesso = sesso;
            this.comune = comune;
            this.nazione = nazione;
            this.voto = false;
    }
    
    //@ requires this.voto == false;
    public void esprimi_voto (boolean voto){
        this.voto = voto;
    }
    
}
