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
public class JeuMultiv2 implements Graphique, ActionListener  {
    private ArrayList<Question> listequestions;
    int scoreA;
    int scoreB;
    private Partie partie;
    private BoutonReponse bouton1;
    private BoutonReponse bouton2;
    private BoutonReponse bouton3;
    private BoutonReponse bouton4;
    private BoutonReponse[] boutons;
    private JLabel labelReponse=new JLabel();
    private JLabel labelEquipe;
    private JLabel labelQuestion;
    private JLabel labelScoreA;
    private JLabel labelScoreB;

    private JLabel labelTempsRestant;
    private Timer chrono;
    private int tempsRestant;
    private int equipeDuTour;
    private int equipeDuProchainTour;
    private String nomEquipeA;
    private String nomEquipeB;

    /**
     *
     * @param questions
     * @param scoreA
     * @param scoreB
     * @param derniereReponse
     * @param partie
     */
    JeuMultiv2(ArrayList<Question> questions,int scoreA,int scoreB,String nomEquipeA,String nomEquipeB,int equipeDuTour, String derniereReponse,Partie partie) {

        this.scoreA=scoreA;
        this.scoreB=scoreB;
        this.partie=partie;
        this.listequestions=questions;
        this.equipeDuTour = equipeDuTour;
        this.nomEquipeA=nomEquipeA;
        this.nomEquipeB=nomEquipeB;
        equipeDuProchainTour=(equipeDuTour+1)%2;
        chrono = new Timer(true);
        tempsRestant = questions.get(0).getTemps();
        TimerTask timeOutTask = new TimerTask() {
            @Override
            public void run() {
                if (tempsRestant==1) {
                    if(listequestions.size()!=1) {
                        String dernierereponse = listequestions.get(0).getReponses()[listequestions.get(0).getBonnereponse() - 1];
                        listequestions.remove(0);
                        new JeuMultiv2(listequestions, scoreA,scoreB,nomEquipeA,nomEquipeB,equipeDuProchainTour, dernierereponse, partie).setLabelReponse("Temps écoulé ! La bonne réponse était "+dernierereponse,Color.orange);
                        CARD.show(CONTAINER, "JeuMulti");
                        chrono.cancel();
                    }else{
                        try {
                            new FinJeuMulti(scoreA,scoreB,nomEquipeA,nomEquipeB,2,partie).setLabelReponse("Temps écoulé ! La bonne réponse était "+listequestions.get(0).getReponses()[listequestions.get(0).getBonnereponse() - 1],Color.orange);
                            CARD.show(CONTAINER,"FinJeu");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
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

        labelEquipe =new JLabel();
        if (equipeDuTour==0){
            labelEquipe.setText("Au tour de l'équipe "+nomEquipeA);
        }else {
            labelEquipe.setText("Au tour de l'équipe "+nomEquipeB);
        }
        labelQuestion=new JLabel(questions.get(0).getQuestion());
        labelScoreA =new JLabel("Score équipe " +nomEquipeA+" : "+scoreA);
        labelScoreB =new JLabel("Score équipe " +nomEquipeA+" : "+scoreB);

        labelTempsRestant = new JLabel("Temps Restant : "+tempsRestant);
        labelTempsRestant.setForeground(Color.BLACK);
        labelTempsRestant.setVisible(true);


        JPanel panel= new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill=GridBagConstraints.CENTER;
        gbc.insets=new Insets(25,0,0,0);
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=2;
        panel.add(labelEquipe,gbc);
        gbc.gridx=0;
        gbc.gridy=1;
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
        gbc.gridwidth=1;
        gbc.insets=new Insets(25,0,0,0);
        panel.add(labelScoreA,gbc);
        gbc.gridx=1;
        gbc.gridy=6;
        panel.add(labelScoreB,gbc);
        gbc.gridwidth=2;
        gbc.gridx=0;
        gbc.gridy=7;
        panel.add(labelTempsRestant,gbc);
        gbc.gridx=0;
        gbc.gridy=8;

        panel.add(labelReponse,gbc);
        CONTAINER.add("JeuMulti",panel);
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
            if (equipeDuTour == 0) {
                if(listequestions.size()==1) {
                    scoreA = scoreA + listequestions.get(0).getPoints();
                    partie.setScore(scoreA);
                    try {
                        new FinJeuMulti(scoreA,scoreB,nomEquipeA,nomEquipeB,2,partie).setLabelReponse("Bonne réponse !", Color.green);
                        ;
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    CARD.show(CONTAINER, "FinJeu");
                } else {
                    String dernierereponse = listequestions.get(0).getReponses()[listequestions.get(0).getBonnereponse() - 1];
                    scoreA = scoreA + listequestions.get(0).getPoints();
                    listequestions.remove(0);
                    new JeuMultiv2(listequestions, scoreA,scoreB,nomEquipeA,nomEquipeB,equipeDuProchainTour, dernierereponse, partie).setLabelReponse("Bonne réponse !", Color.green);
                    CARD.show(CONTAINER, "JeuMulti");
                }
            }
            else{
                if(listequestions.size()==1) {
                    scoreB = scoreB + listequestions.get(0).getPoints();
                    partie.setScore(scoreB);
                    try {
                        new FinJeuMulti(scoreA,scoreB,nomEquipeA,nomEquipeB,2,partie).setLabelReponse("Bonne réponse !", Color.red);
                        ;
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    CARD.show(CONTAINER, "FinJeu");
                } else {
                    String dernierereponse = listequestions.get(0).getReponses()[listequestions.get(0).getBonnereponse() - 1];
                    scoreB = scoreB + listequestions.get(0).getPoints();
                    listequestions.remove(0);
                    new JeuMultiv2(listequestions, scoreA,scoreB,nomEquipeA,nomEquipeB,equipeDuProchainTour, dernierereponse, partie).setLabelReponse("Bonne réponse !", Color.green);
                    CARD.show(CONTAINER, "JeuMulti");
                }
            }
        }else{
            if (equipeDuTour==0) {
                if (listequestions.size() == 1) {
                    scoreA = scoreA + listequestions.get(0).getMalus();
                    if (scoreA < 0) {
                        scoreA = 0;
                    }
                    partie.setScore(scoreA);
                    try {
                        new FinJeuMulti(scoreA,scoreB,nomEquipeA,nomEquipeB,2,partie).setLabelReponse("Mauvaise réponse ! La bonne réponse était " + listequestions.get(0).getReponses()[listequestions.get(0).getBonnereponse() - 1], Color.red);
                        ;
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    CARD.show(CONTAINER, "FinJeu");
                } else {
                    scoreA = scoreA + listequestions.get(0).getMalus();
                    if (scoreA < 0) {
                        scoreA = 0;
                    }
                    String dernierereponse = listequestions.get(0).getReponses()[listequestions.get(0).getBonnereponse() - 1];
                    listequestions.remove(0);
                    new JeuMultiv2(listequestions, scoreA,scoreB,nomEquipeA,nomEquipeB,equipeDuProchainTour, dernierereponse, partie).setLabelReponse("Mauvaise réponse ! La bonne réponse était " + dernierereponse, Color.red);
                    CARD.show(CONTAINER, "JeuMulti");
                }
            }
            else{
                if(listequestions.size()==1){
                    scoreB=scoreB+listequestions.get(0).getMalus();
                    if (scoreB<0){
                        scoreB=0;
                    }
                    partie.setScore(scoreB);
                    try {
                        new FinJeuMulti(scoreA,scoreB,nomEquipeA,nomEquipeB,2,partie).setLabelReponse("Mauvaise réponse ! La bonne réponse était "+listequestions.get(0).getReponses()[listequestions.get(0).getBonnereponse() - 1],Color.red);;
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    CARD.show(CONTAINER,"FinJeu");
                }else {
                    scoreB=scoreB+listequestions.get(0).getMalus();
                    if (scoreB<0){
                        scoreB=0;
                    }
                    String dernierereponse=listequestions.get(0).getReponses()[listequestions.get(0).getBonnereponse()-1];
                    listequestions.remove(0);
                    new JeuMultiv2(listequestions, scoreA,scoreB,nomEquipeA,nomEquipeB,equipeDuProchainTour, dernierereponse, partie).setLabelReponse("Mauvaise réponse ! La bonne réponse était "+ dernierereponse,Color.red);
                    CARD.show(CONTAINER, "JeuMulti");
                }
            }
        }
        chrono.cancel();
    }
    public void setLabelReponse(String text,Color color){
        labelReponse.setText(text);
        labelReponse.setForeground(color);
    }

}
