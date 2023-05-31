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
     * @param question
     * @param reponses
     * @param theme
     * @param difficulte
     * @param temps
     * @param points
     * @param bonnereponse
     * @param malus
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

    public String getQuestion() {
        return question;
    }

    public String[] getReponses() {
        return reponses;
    }

    public String getTheme() {
        return theme;
    }

    public String getDifficulte() {
        return difficulte;
    }

    public int getTemps() {
        return temps;
    }

    public int getPoints() {
        return points;
    }

    public int getBonnereponse() {
        return bonnereponse;
    }

    public int getMalus() {
        return malus;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setReponses(String[] reponses) {
        this.reponses = reponses;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    public void setTemps(int temps) {
        this.temps = temps;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setBonnereponse(int bonnereponse) {
        this.bonnereponse = bonnereponse;
    }

    public void setMalus(int malus) {
        this.malus = malus;
    }

    /**
     *
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
                nouvelleReponses[i]=this.getReponses()[i].replace(",",";").replace('"',' ');;
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
