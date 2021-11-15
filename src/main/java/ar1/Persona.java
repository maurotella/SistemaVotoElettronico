import java.util.GregorianCalendar;

public class Persona {

	private final String nome;
	private final String cognome;
	private final GregorianCalendar dataNascita;
	private final String codiceFiscale;
	
	public Persona (String n, String c, GregorianCalendar dn, String cf) {
		this.nome = n;
		this.cognome = c;
		this.dataNascita = new GregorianCalendar(dn.YEAR, dn.MONTH, dn.DATE);
		this.codiceFiscale = cf;
	}
	
    public getDataNascita toString(){
        String str = "";
        str += dataNascita.get(Calendar.DAY_OF_MONTH) + "-" + dataNascita.get(Calendar.MONTH) + "-" + dataNascita.get(Calendar.YEAR);
        return str;
    }
	
}
