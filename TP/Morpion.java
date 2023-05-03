import java.util.Scanner;
public class Morpion {
    public static void main(String[] args) {
        int[][] grille = new int[3][];
        int[] ligne1 = new int[3];
        ligne1[0] = 0;
        ligne1[1] = 0;
        ligne1[2] = 0;
        int[] ligne2 = new int[3];
        ligne2[0] = 0;
        ligne2[1] = 0;
        ligne2[2] = 0;
        int[] ligne3 = new int[3];
        ligne3[0] = 0;
        ligne3[1] = 0;
        ligne3[2] = 0;

        grille[0] = ligne1;
        grille[1] = ligne2;
        grille[2] = ligne3;
    }

    public static void affiche(int[][] grille){
        System.out.println(" 1 2 3 ");
        System.out.println("-------");
        for (int i=0; i<3; i++){
            System.out.println("|");
            for (int j=0; j<3;j++){
                System.out.print(grille[i][j]+"|");
            }   
            System.out.print(" "+i);
            System.out.println("-------");
        }
    }

    public static void saisie(int joueur,int[][] grille){
        Scanner scan = new Scanner(System.in);
        System.out.println("Joueur "+joueur+" (format \"ligne colonne\") : ");
        int ligne = scan.nextInt();
        System.out.print(" ");
        int colonne = scan.nextInt();
        scan.close();
        if (grille[ligne][colonne] == 0){
            grille[ligne][colonne]=joueur;
        }
        else saisie(joueur, grille);
    }

    public static boolean case_libre(int[][] grille){
        boolean res = false;
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                if (grille[i][j]==0){
                    res = true;
                }
            }
        }
        return res;
    }

    
}
