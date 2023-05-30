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
     * @param mode
     * @param difficulte
     * @param theme
     */
    public Partie(String mode, String difficulte, String[] theme) {
        this.mode = mode;
        this.difficulte = difficulte;
        this.theme = theme;
    }

    /**
     *
     * @param mode
     * @param difficulte
     * @param theme
     * @param score
     * @param nombrequestions
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
     * @param mode
     * @param difficulte
     * @param theme
     * @param nombrequestions
     */
    public Partie(String mode, String difficulte, String[] theme,int nombrequestions) {
        this.mode = mode;
        this.difficulte = difficulte;
        this.theme = theme;
        this.nombrequestions=nombrequestions;
    }

    public String getMode() {
        return mode;
    }

    public String getDifficulte() {
        return difficulte;
    }

    public String[] getTheme() {
        return theme;
    }

    public int getScore() {
        return score;
    }

    public int getNombrequestions() {
        return nombrequestions;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    public void setTheme(String[] theme) {
        this.theme = theme;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setNombrequestions(int nombrequestions) {
        this.nombrequestions = nombrequestions;
    }

    /**
     *
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
