import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 *
 */
public class Question {
    private String question;
    private String[] reponses;
    private String theme;
    private String difficulte;
    private int temps;
    private int points;
    private int bonnereponse;
    private int malus;

    /**
     *
     * @param question écnoncé de la question
     * @param reponses liste des réponses
     * @param theme thème de la question
     * @param difficulte difficulté de la question
     * @param temps temps limite pour répondre à la question
     * @param points nombre de points attirbués pour une bonne réponse
     * @param bonnereponse place de la bonne réponse dans le tableau des réponses
     * @param malus nombre de points retirés en cas de mauvaise réponse
     */
    public Question(String question,
                    String[] reponses,
                    String theme,
                    String difficulte,
                    int temps,
                    int points,
                    int bonnereponse,
                    int malus) {
        this.question = question;
        this.reponses = reponses;
        this.theme = theme;
        this.difficulte = difficulte;
        this.temps = temps;
        this.points = points;
        this.bonnereponse = bonnereponse;
        this.malus = malus;
    }

    /**
     *
     */
    public Question() {
    }

    /**
     *
     * @return
     */
    public String getQuestion() {
        return question;
    }

    /**
     *
     * @return
     */
    public String[] getReponses() {
        return reponses;
    }

    /**
     *
     * @return
     */
    public String getTheme() {
        return theme;
    }

    /**
     *
     * @return
     */
    public String getDifficulte() {
        return difficulte;
    }

    /**
     *
     * @return
     */
    public int getTemps() {
        return temps;
    }

    /**
     *
     * @return
     */
    public int getPoints() {
        return points;
    }

    /**
     *
     * @return
     */
    public int getBonnereponse() {
        return bonnereponse;
    }

    /**
     *
     * @return
     */
    public int getMalus() {
        return malus;
    }

    /**
     *
     * @param question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     *
     * @param reponses
     */
    public void setReponses(String[] reponses) {
        this.reponses = reponses;
    }

    /**
     *
     * @param theme
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     *
     * @param difficulte
     */
    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    /**
     *
     * @param temps
     */
    public void setTemps(int temps) {
        this.temps = temps;
    }

    /**
     *
     * @param points
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     *
     * @param bonnereponse
     */
    public void setBonnereponse(int bonnereponse) {
        this.bonnereponse = bonnereponse;
    }

    /**
     *
     * @param malus
     */
    public void setMalus(int malus) {
        this.malus = malus;
    }

    /**
     * Ajoute la question dans Question.csv
     */
    public void ajouteCSV(){
        try {
            File file= new File("Question.csv");
            FileWriter writer = new FileWriter(file, true);

            // Ajouter l'en-tête si le fichier est vide
            if (file.length() == 0) {
                writer.append("Question,Réponse1,Réponse2,Réponse3,Réponse4,Thème,Difficulté,Temps,Points,Bonne Réponse,Malus\n");
            }

            //On enlève les virgules et les apostrophes qui posent problème dans la lecture du CSV
            String nouvelleQuestion=this.getQuestion().replace(",",";").replace('"',' ');
            // Ajouter les objets dans le fichier CSV
            String [] nouvelleReponses=new String[4];
            for(int i=0;i<4;i++) {
                nouvelleReponses[i]=this.getReponses()[i].replace(",",";").replace('"','\'');;
            }

            writer.append(nouvelleQuestion);
            writer.append(",");
            for(int i=0;i<4;i++) {
                writer.append(nouvelleReponses[i]);
                writer.append(",");
            }
            writer.append(this.getTheme());
            writer.append(",");
            writer.append(this.getDifficulte());
            writer.append(",");
            writer.append(String.valueOf(this.getTemps()));
            writer.append(",");
            writer.append(String.valueOf(this.getPoints()));
            writer.append(",");
            writer.append(String.valueOf(this.getBonnereponse()));
            writer.append(",");
            writer.append(String.valueOf(this.getMalus()));
            writer.append("\n");

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
