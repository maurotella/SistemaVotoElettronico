package data;

import models.Sessione;
import models.TipoUtente;
import org.springframework.security.crypto.bcrypt.BCrypt;
import models.Gestore;

public class Test {

    public static void print (Object S) {
        System.out.println(S.toString());
    }

    public static void main(String[] args) {
        String psw = "Datem1StaL@urea";
        String psw2 = "3$ca34";
        String enc = BCrypt.hashpw(psw, BCrypt.gensalt());
        String enc2 = BCrypt.hashpw(psw2, BCrypt.gensalt());

        //Gestore G = ImplGestoreDAO.getIstance()
        //      .login("RBNSRA92R50L113H", "3$ca34");

        Sessione F = ImplElettoreDAO.getInstance().getSessioni().get(0);

        System.out.println(
                ImplElettoreDAO.getInstance().puoVotare(
                        ImplElettoreDAO.getInstance().login("TLLMRA99L13H2640","Datem1StaL@urea"),
                        F
                )
        );
    }

}
