package util;

import java.sql.Date;
import java.time.LocalDate;

public class Util {

    /**
     * Presa una Date D la converte il LocalDate
     *
     * @param D
     * @return la data in LocalDate
     */
    public static LocalDate dateToLocal(Date D) {
        String[] A = D.toString().split("-");
        int y = Integer.parseInt(A[0]);
        int m = Integer.parseInt(A[1]);
        int d = Integer.parseInt(A[2]);
        return LocalDate.of(y,m,d);
    }

}
