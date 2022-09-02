package data;

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

        print(enc);
        print(enc2);
        print("MAGGIORANZA_ASSOLUTA".length());

    }

}
