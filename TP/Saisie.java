import java.util.Scanner;

public class Saisie {
    int saisieEntier(Scanner scan){
        System.out.println("Saisir un entier :");
        return scan.nextInt();
    }
    String saisieChaine(Scanner scan){
        System.out.println("Saisir chaîne de charactère :");
        return scan.next();
    }
}
