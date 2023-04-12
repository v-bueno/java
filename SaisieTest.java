import java.util.Scanner;

public class SaisieTest {
    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);
        Saisie saisie = new Saisie();
        int ent1 ;
        int ent2 ;
        System.out.println(saisie.saisieChaine(scan));
        ent1 = saisie.saisieEntier(scan);
        ent2 = saisie.saisieEntier(scan);
        System.out.println("entier_1 x entier_2 = "+ent1*ent2);
        System.out.println("entier_2 / entier_1 = "+ent2/ent1);

    }
}
