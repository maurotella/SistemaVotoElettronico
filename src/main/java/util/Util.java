package util;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Preso un ArrayList<E> e un elemento e,
     * aggiunge l'elemento alla lista e
     * la restituisce
     *
     * @param L la lista
     * @param e l'elemento
     * @return la lista a cui è stato aggiunto l'elemento
     * @param <E> il tipo dell'elemento
     */
    public static <E> ArrayList<E> addAndReturnList (ArrayList<E> L, E e) {
        L.add(e);
        return L;
    }

}
