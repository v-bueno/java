import java.sql.Array;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Locale;

import static java.text.Normalizer.Form.NFD;

public class Palindrome {
    public static void main(String args[]){
        String phrase1 = args[0];
        String phrase2;
        boolean res = true;
        phrase1 = phrase1.toLowerCase();
        phrase2 = Normalizer.normalize(phrase1, Normalizer.Form.NFD);
        phrase2 = phrase2.replaceAll("[\u0300-\u036F]","");
        phrase2 = phrase2.replaceAll(" ","");
        System.out.println(phrase2);
        for (int i = 0;i<phrase2.length();i++){
            System.out.println(phrase2.charAt(i) + " | " +phrase2.charAt(phrase2.length()-1-i));
            if (phrase2.charAt(i)!=phrase2.charAt(phrase2.length()-1-i)){
                res = false;
            }
        }
        System.out.println(phrase2  + " est un palindrome ? Réponse : " + res);
    }
}
