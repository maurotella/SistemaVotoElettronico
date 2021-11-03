package ar1;

import java.util.GregorianCalendar;

public abstract class Persona {

	private final String nome;
	private final String cognome;
	private final GregorianCalendar dataNascita;
	private final String codiceFiscale;
	
	public Persona (String n, String c, GregorianCalendar dn, String cf) {
		nome = n;
		cognome = c;
		dataNascita = new GregorianCalendar(dn.YEAR, dn.MONTH, dn.DATE);
		codiceFiscale = cf;
	}
	
}
