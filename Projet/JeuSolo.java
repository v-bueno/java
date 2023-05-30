import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 */
public class JeuSolo implements Graphique, ActionListener  {
    private ArrayList<Question> listequestions;
    int score;
    private Partie partie;
    private BoutonReponse bouton1;
    private BoutonReponse bouton2;
    private BoutonReponse bouton3;
    private BoutonReponse bouton4;
    private BoutonReponse[] boutons;
    private JLabel labelQuestion;
    private JLabel labelScore;
    private JLabel labelBonneReponse;
    private JLabel labelMauvaiseReponse;
    private JLabel labelPasReponse;
    private JLabel labelTempsRestant;
    private Timer chrono;
    private int tempsRestant;


    /**
     *
     * @param questions
     * @param score
     * @param derniereReponse
     * @param partie
     */
    JeuSolo(ArrayList<Question> questions,int score, String derniereReponse,Partie partie) {

        this.score=score;
        this.partie=partie;
        this.listequestions=questions;

        chrono = new Timer(true);
        tempsRestant = questions.get(0).getTemps();
        TimerTask timeOutTask = new TimerTask() {
            @Override
            public void run() {
                if (tempsRestant==1) {
                    String dernierereponse = listequestions.get(0).getReponses()[listequestions.get(0).getBonnereponse() - 1];
                    listequestions.remove(0);
                    new JeuSolo(listequestions, score, dernierereponse, partie).pasReponse();
                    labelPasReponse.setVisible(true);
                    CARD.show(CONTAINER, "JeuSolo");
                    chrono.cancel();
                }
                else{
                    tempsRestant-=1;
                    labelTempsRestant.setText("Temps Restant : "+tempsRestant);
                }
            }
        };

        bouton1 = new BoutonReponse(chrono,0,questions);
        bouton2 = new BoutonReponse(chrono,1,questions);
        bouton3 = new BoutonReponse(chrono,2,questions);
        bouton4 = new BoutonReponse(chrono,3,questions);

        boutons = new BoutonReponse[]{bouton1, bouton2, bouton3, bouton4};

        labelQuestion=new JLabel(questions.get(0).getQuestion());
        labelScore = new JLabel("Score : "+score);

        
        labelBonneReponse = new JLabel("Bonne réponse !");
        labelMauvaiseReponse = new JLabel("Mauvaise réponse ! La bonne réponse était"+derniereReponse);
        labelPasReponse = new JLabel("Temps Écoulé ! La bonne réponse était"+derniereReponse);
        labelTempsRestant = new JLabel("Temps Restant : "+tempsRestant);
        labelTempsRestant.setForeground(Color.BLACK);
        labelTempsRestant.setVisible(true);
        labelBonneReponse.setForeground(Color.green);
        labelBonneReponse.setVisible(false);
        labelMauvaiseReponse.setForeground(Color.red);
        labelMauvaiseReponse.setVisible(false);
        labelPasReponse.setForeground((Color.yellow));
        labelPasReponse.setVisible((false));

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
        gbc.insets=new Insets(0,0,0,0);
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
        panel.add(labelBonneReponse,gbc);
        panel.add(labelMauvaiseReponse,gbc);
        panel.add(labelPasReponse,gbc);
        CONTAINER.add("JeuSolo",panel);
        bouton1.getBouton().addActionListener(this);
        bouton2.getBouton().addActionListener(this);
        bouton3.getBouton().addActionListener(this);
        bouton4.getBouton().addActionListener(this);


        chrono.schedule(timeOutTask,1000,1000);

    }

    /**
     *
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==boutons[listequestions.get(0).getBonnereponse()-1].getBouton()){
            if(listequestions.size()==1){
                score=score+listequestions.get(0).getPoints();
                partie.setScore(score);
                Connexion.compteUtilise.ajoutePartie(partie);
                try {
                    new FinJeu(score);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                CARD.show(CONTAINER,"FinJeu");
            }else {
                String dernierereponse=listequestions.get(0).getReponses()[listequestions.get(0).getBonnereponse()-1];
                score=score+listequestions.get(0).getPoints();
                listequestions.remove(0);
                new JeuSolo(listequestions,score,dernierereponse,partie).bonneReponse();
                labelBonneReponse.setVisible(true);
                CARD.show(CONTAINER, "JeuSolo");
            }
        }else{
            if(listequestions.size()==1){
                score=score+listequestions.get(0).getMalus();
                if (score<0){
                    score=0;
                }
                partie.setScore(score);
                Connexion.compteUtilise.ajoutePartie(partie);
                try {
                    new FinJeu(score);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                CARD.show(CONTAINER,"FinJeu");
            }else {
                score=score+listequestions.get(0).getMalus();
                if (score<0){
                    score=0;
                }
                String dernierereponse=listequestions.get(0).getReponses()[listequestions.get(0).getBonnereponse()-1];
                listequestions.remove(0);
                new JeuSolo(listequestions,score,dernierereponse,partie).mauvaiseReponse();
                labelMauvaiseReponse.setVisible(true);
                CARD.show(CONTAINER, "JeuSolo");
            }
        }
    chrono.cancel();
    }
    public void bonneReponse(){
        labelBonneReponse.setVisible(true);
    }
    public void mauvaiseReponse(){
        labelMauvaiseReponse.setVisible(true);
    }
    public void pasReponse(){
        labelPasReponse.setVisible(true);
    }

}
