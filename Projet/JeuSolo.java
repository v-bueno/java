import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 */
public class JeuSolo implements Graphique, ActionListener  {
    private ArrayList<Question> listequestions;
    private int score;
    private Partie partie;
    private BoutonReponse bouton1;
    private BoutonReponse bouton2;
    private BoutonReponse bouton3;
    private BoutonReponse bouton4;
    private BoutonReponse[] boutons;
    private JLabel labelReponse=new JLabel();
    private JLabel labelQuestion;
    private JLabel labelScore;

    private JLabel labelTempsRestant;
    private Timer chrono;
    private int tempsRestant;


    /**
     * Le programme porte sur la première question de la liste puis s'appelle récursivement en supprimant
     * le premier élément de la liste jusqu'à ce qu'il n'y ait plus de questions dans la liste
     * @param questions liste de toutes les questions restantes
     * @param score score de la partie en cours
     * @param derniereReponse réponse de la question précédente
     * @param partie partie en cours de jeu
     */
    JeuSolo(ArrayList<Question> questions,int score, String derniereReponse,Partie partie) {

        this.score=score;
        this.partie=partie;
        this.listequestions=questions;

        Connexion.menuBar.setVisible(false); //pour ne pas avoir de probleme avec le timer et un nouvel appel de fonction

        Connexion.compteUtilise.incrementeNombreQuestion();

        chrono = new Timer(true);
        tempsRestant = questions.get(0).getTemps(); //Prends la valeur dédiée pour la question
        TimerTask timeOutTask = new TimerTask() {
            @Override
            public void run() {
                if (tempsRestant==1) { //S'il ne reste plus de temps pour répondre
                    if(listequestions.size()!=1) { //Si la liste de question n'est pas finie
                        //On appelle la prochaine question via Jeu Solo sans changer le score car on ne change pas dans ce cas
                        String dernierereponse = listequestions.get(0).getReponses()[listequestions.get(0).getBonnereponse() - 1];
                        listequestions.remove(0);
                        new JeuSolo(listequestions, score, dernierereponse, partie).setLabelReponse("Temps écoulé ! La bonne réponse était "+dernierereponse,Color.orange);
                        CARD.show(CONTAINER, "JeuSolo");
                        chrono.cancel(); //Cancel le timer
                    }else{ //Si les questions sont finies
                        try {
                            //Appelle la fin du jeu et set les données
                            partie.setScore(score);
                            Connexion.compteUtilise.ajoutePartie(partie);
                            new FinJeu(score).setLabelReponse("Temps écoulé ! La bonne réponse était "+listequestions.get(0).getReponses()[listequestions.get(0).getBonnereponse() - 1],Color.orange);
                            CARD.show(CONTAINER,"FinJeu");
                            chrono.cancel();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                else{
                    //Actualise le temps et restant et le label correspondant
                    tempsRestant-=1;
                    labelTempsRestant.setText("Temps Restant : "+tempsRestant);
                }
            }
        };

        //Instancie les boutons
        bouton1 = new BoutonReponse(chrono,0,questions);
        bouton2 = new BoutonReponse(chrono,1,questions);
        bouton3 = new BoutonReponse(chrono,2,questions);
        bouton4 = new BoutonReponse(chrono,3,questions);
        //Tableau de réponses afin d'avoir l'entier correspondant à la bonne réponse pour l'indice du tableau
        boutons = new BoutonReponse[]{bouton1, bouton2, bouton3, bouton4};

        //Label de la fenêtre
        labelQuestion=new JLabel(questions.get(0).getQuestion());
        labelScore = new JLabel("Score : "+score);
        labelTempsRestant = new JLabel("Temps Restant : "+tempsRestant);
        labelTempsRestant.setForeground(Color.BLACK);
        labelTempsRestant.setVisible(true);

        //On place les composants à l'endroit souhaité dans un panel
        JPanel panel= new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill=GridBagConstraints.CENTER;
        gbc.insets=new Insets(25,0,0,0);
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=2;
        panel.add(labelQuestion,gbc);

        gbc.gridx=0;
        gbc.gridy=2;
        panel.add(bouton1.getBouton(),gbc);
        gbc.gridx=0;
        gbc.gridy=3;
        gbc.insets=new Insets(5,0,0,0);
        panel.add(bouton2.getBouton(),gbc);
        gbc.gridx=0;
        gbc.gridy=4;
        panel.add(bouton3.getBouton(),gbc);
        gbc.gridx=0;
        gbc.gridy=5;
        panel.add(bouton4.getBouton(),gbc);
        gbc.gridx=0;
        gbc.gridy=6;
        gbc.insets=new Insets(25,0,0,0);
        panel.add(labelScore,gbc);
        gbc.gridx=0;
        gbc.gridy=7;
        panel.add(labelTempsRestant,gbc);
        gbc.gridx=0;
        gbc.gridy=8;
        panel.add(labelReponse,gbc);
        CONTAINER.add("JeuSolo",panel);

        //Ajoute l'action listener définie après aux boutons
        bouton1.getBouton().addActionListener(this);
        bouton2.getBouton().addActionListener(this);
        bouton3.getBouton().addActionListener(this);
        bouton4.getBouton().addActionListener(this);

        //lance le chrono qui fait la timeOutTask à chaque secondes
        chrono.schedule(timeOutTask,1000,1000);

    }

    /**
     *
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e){
        //On observe la première question de la liste
        //Si c'est le bouton correspondant à la bonne réponse
        if(e.getSource()==boutons[listequestions.get(0).getBonnereponse()-1].getBouton()){
            if(listequestions.size()==1){ //si la liste de question est finie
                //On fait tous les changement nécessaires pour le compte et la partie
                score=score+listequestions.get(0).getPoints();
                Connexion.compteUtilise.incrementeNombreReponses();
                partie.setScore(score);
                Connexion.compteUtilise.ajoutePartie(partie);
                try {
                    //On appelle l'écran de fin de jeu
                    new FinJeu(score).setLabelReponse("Bonne réponse !",Color.green);;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                CARD.show(CONTAINER,"FinJeu");
            }else { //Si il reste des questions après
                //On fait les modifications nécessaires
                Connexion.compteUtilise.incrementeNombreReponses();
                String dernierereponse=listequestions.get(0).getReponses()[listequestions.get(0).getBonnereponse()-1];
                score=score+listequestions.get(0).getPoints();
                listequestions.remove(0); //On retire le premiers éléments de la liste de questions
                //On rappelle JeuSolo avec la liste de questions sans le premier élément et on adapte le label de réponse
                new JeuSolo(listequestions,score,dernierereponse,partie).setLabelReponse("Bonne réponse !",Color.green);
                CARD.show(CONTAINER, "JeuSolo");
            }
        }else{ //Si le bouton correspondant à une mauvaise réponse
            if(listequestions.size()==1){//Si c'est la dernière question
                //On applique le malus et on met le socre à 0 s'il est censé être négatif
                score=score+listequestions.get(0).getMalus();
                if (score<0){
                    score=0;
                }
                //On set les paramètres de fin et on affiche l'écran de fin
                partie.setScore(score);
                Connexion.compteUtilise.ajoutePartie(partie);
                try {
                    new FinJeu(score).setLabelReponse("Mauvaise réponse ! La bonne réponse était "+listequestions.get(0).getReponses()[listequestions.get(0).getBonnereponse() - 1],Color.red);;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                CARD.show(CONTAINER,"FinJeu");
            }else { //Si il reste des questions après
                //Modifie le score et rappelle la fonction comme dans le cas de bonne réponse
                score=score+listequestions.get(0).getMalus();
                if (score<0){
                    score=0;
                }
                String dernierereponse=listequestions.get(0).getReponses()[listequestions.get(0).getBonnereponse()-1];
                listequestions.remove(0);
                new JeuSolo(listequestions,score,dernierereponse,partie).setLabelReponse("Mauvaise réponse ! La bonne réponse était "+ dernierereponse,Color.red);
                CARD.show(CONTAINER, "JeuSolo");
            }
        }
    chrono.cancel(); //On arrête le timer comme c'est finis
    }

    /**
     * Permet de modifier le label des réponses
     * @param text
     * @param color
     */
    public void setLabelReponse(String text,Color color){
        labelReponse.setText(text);
        labelReponse.setForeground(color);
    }

}
