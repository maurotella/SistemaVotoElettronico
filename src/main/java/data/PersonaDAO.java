package data;

public interface PersonaDAO {

    /**
     * Dato il CF restituisce il nominativo
     *
     * @param CF codice fiscale
     * @return il nominativo o null se non
     *         corrisponde nessuna persona al CF
     */
    String getNominativo(String CF);

}
