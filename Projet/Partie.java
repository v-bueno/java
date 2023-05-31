import java.io.Serializable;
import java.util.Arrays;

/**
 *
 */
public class Partie implements Serializable {
    private String mode;
    private String difficulte;
    private String[] theme;
    private int score;
    private int nombrequestions;

    /**
     *
     * @param mode mode de la partie
     * @param difficulte difficulté de la partie
     * @param theme liste des thèmes
     */
    public Partie(String mode, String difficulte, String[] theme) {
        this.mode = mode;
        this.difficulte = difficulte;
        this.theme = theme;
    }

    /**
     *
     * @param mode mode de la partie
     * @param difficulte difficulté de la partie
     * @param theme liste des thèmes
     * @param score score du joueur
     * @param nombrequestions nombre de questions jouées lors de la partie
     */
    public Partie(String mode, String difficulte, String[] theme, int score, int nombrequestions) {
        this.mode = mode;
        this.difficulte = difficulte;
        this.theme = theme;
        this.score = score;
        this.nombrequestions = nombrequestions;
    }

    /**
     *
     * @param mode mode de la partie
     * @param difficulte difficulté de la partie
     * @param theme liste des thèmes
     * @param nombrequestions nombre de questions jouées lors de la partie
     */
    public Partie(String mode, String difficulte, String[] theme,int nombrequestions) {
        this.mode = mode;
        this.difficulte = difficulte;
        this.theme = theme;
        this.nombrequestions=nombrequestions;
    }

    /**
     *
     * @return renvoie le mode de la partie
     */
    public String getMode() {
        return mode;
    }

    /**
     *
     * @return renvoie la difficulté de la partie
     */
    public String getDifficulte() {
        return difficulte;
    }

    /**
     *
     * @return renvoie les thèmes de la partie
     */
    public String[] getTheme() {
        return theme;
    }

    /**
     *
     * @return renvoie le score de la partie
     */
    public int getScore() {
        return score;
    }

    /**
     *
     * @return renvoie le nombre de questions de la partie
     */
    public int getNombrequestions() {
        return nombrequestions;
    }

    /**
     *
     * @param mode mode de jeu de la partie
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     *
     * @param difficulte difficulté de la partie
     */
    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    /**
     *
     * @param theme liste des thèmes de la partie
     */
    public void setTheme(String[] theme) {
        this.theme = theme;
    }

    /**
     *
     * @param score score de la partie
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     *
     * @param nombrequestions nombre de questions de la partie
     */
    public void setNombrequestions(int nombrequestions) {
        this.nombrequestions = nombrequestions;
    }

    /**
     * Convertit la classe en String
     * @return
     */
    @Override
    public String toString() {
        return "Partie{" +
                "mode='" + mode + '\'' +
                ", difficulte='" + difficulte + '\'' +
                ", theme=" + Arrays.toString(theme) +
                ", score=" + score +
                ", nombrequestions=" + nombrequestions +
                '}';
    }
}
