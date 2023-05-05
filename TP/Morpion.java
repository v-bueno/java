import java.util.Scanner;

public class Morpion {
    public static final int LENGTH = 3;
    public static boolean gagne = false;
    private static final Scanner sc = new Scanner(System.in);

    public static void initializeGrille(int grille[][]) {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                grille[i][j] = 0;
            }
        }
    }

    public static void affiche(int grile[][]) {
        System.out.println(" 1 2 3 ");
        System.out.println("-------");
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                System.out.print("|"+grile[i][j]);
            }
            System.out.println("| "+(i+1));
        }
        System.out.println("-------");
    }

    public static boolean caseLibre(int grille[][]) {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                if (grille[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void saisie(int joueur, int grille[][]) {
        int l, c;
        boolean t = true;
        do {
            System.out.print("Joueur "+joueur+ " (format \"ligne colonne\") : ");
            l = sc.nextInt() - 1;
            c = sc.nextInt() - 1;
            if (grille[l][c] == 0) {
                grille[l][c] = joueur;
                t = false;
            } else {
                System.out.println("Case deja occupe, rejouez !");
            }
        } while (t);
    }

    private static boolean ligne(int i, int grille[][]) {
        return ((grille[i][0]==grille[i][1])
                &&(grille[i][0]==grille[i][2]))
                && grille[i][0] != 0;
    }

    private static boolean colonne(int i, int grille[][]) {
        return ((grille[0][i]==grille[1][i])
                &&(grille[0][i]==grille[2][i]))
                && grille[0][i] != 0;
    }

    public static boolean gagne(int grille[][]) {
        return
                ligne(0, grille)||ligne(1, grille)||ligne(2, grille)||colonne(0, grille)||colonne(1, grille)||colonne(2, grille)
                        ||((grille[0][0]==grille[1][1])&&(grille[0][0]==grille[2][2]))
                        ||((grille[0][2]==grille[1][1])&&(grille[0][2]==grille[2][0]))
                        && grille[1][1]!=0;
    }

    public static void joue() {
        int[][] grille = new int[3][3];
        int joueur = 1;
        initializeGrille(grille);
        do {
            affiche(grille);
            saisie(joueur, grille);
            joueur = joueur == 1 ? 2 : 1;
        } while(!gagne(grille));
        System.out.println("Bravo Joueur "+joueur+ " !");
    }

    public static void main(String[] args) {
        Morpion.joue();
    }
}
