import java.util.Random;
import java.util.Arrays;

public class Tableau {
    public static void main(String args[]){
        int[] tableau = new int[10];
        Random r = new Random();
        for (int i=0; i<tableau.length; i++){
            tableau[i] = r.nextInt();
        }
        System.out.println("Le tableau non trié");
        affiche(tableau);
        Arrays.sort(tableau);
        System.out.println("Le tableau trié");
        affiche(tableau);
    }

    public static void affiche(int[] tableau){
        for (int i=0; i<tableau.length; i++){
                System.out.print(tableau[i] + " ");
        }
        System.out.println(" ");
    }
}



