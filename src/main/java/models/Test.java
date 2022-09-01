package models;

import java.time.LocalDate;

public class Test {

    public static void main (String[] args) {

        Persona P = new Persona("Mario","Rossi","0123456789ABDEFG",'M',"ITA","Rho", LocalDate.of(1999,07,13));
        Sessione S = SessioneBuilder.newBuilder(1)
                .titolo("Prova")
                .dataApertura(LocalDate.of(2022,9,10))
                .dataChiusura(LocalDate.of(2022,10,10))
                .tipoVotazione(TipoVotazione.REFERENDUM)
                .tipoScrutinio(TipoScrutinio.REFERENDUM)
                .gestore(new Gestore(P,"Gino"))
                .build();

        Referendum R = new Referendum(S, "Eutanasia legale?");

        R.addNo();

        System.out.println(R);

    }

}
