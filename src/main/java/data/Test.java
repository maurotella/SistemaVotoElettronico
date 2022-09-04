package data;

import models.Sessione;
import org.springframework.security.crypto.bcrypt.BCrypt;

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

        Sessione F = ElettoreDAOImpl.getInstance().getSessioni().get(0);

        System.out.println(
                ElettoreDAOImpl.getInstance().puoVotare(
                        ElettoreDAOImpl.getInstance().login("TLLMRA99L13H2640","Datem1StaL@urea"),
                        F
                )
        );
    }

}
