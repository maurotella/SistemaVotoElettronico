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
        String psw3 = "E";
        String psw4 = "G";
        String enc = BCrypt.hashpw(psw, BCrypt.gensalt());
        String enc2 = BCrypt.hashpw(psw2, BCrypt.gensalt());
        String enc3 = BCrypt.hashpw(psw3, BCrypt.gensalt());
        String enc4 = BCrypt.hashpw(psw4, BCrypt.gensalt());

        print(enc4);
    }

}
