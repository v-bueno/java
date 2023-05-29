import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JeuSolo implements Graphique, ActionListener  {
    private JButton bouton1;
    private JButton bouton2;
    private JButton bouton3;
    private JButton bouton4;
    private JButton[] boutons;
    private JLabel labelQuestion;
    private JLabel labelScore;
    private JLabel labelBonneReponse;
    private JLabel labelMauvaiseReponse;
    ArrayList<Question> listequestions;
    int score2;
    private Partie partiejouee;
    JeuSolo(ArrayList<Question> questions,int score, String derniereReponse,Partie partie) {



        bouton1 = new JButton(questions.get(0).getReponses()[0]);
        bouton2 = new JButton(questions.get(0).getReponses()[1]);
        bouton3 = new JButton(questions.get(0).getReponses()[2]);
        bouton4 = new JButton(questions.get(0).getReponses()[3]);

        boutons = new JButton[]{bouton1, bouton2, bouton3, bouton4};

        labelQuestion=new JLabel(questions.get(0).getQuestion());
        labelScore = new JLabel("Score : "+score);

        labelBonneReponse = new JLabel("Bonne réponse !");
        labelMauvaiseReponse=new JLabel("Mauvaise réponse ! La bonne réponse était"+derniereReponse);
        labelBonneReponse.setForeground(Color.green);
        labelBonneReponse.setVisible(false);
        labelMauvaiseReponse.setForeground(Color.red);
        labelMauvaiseReponse.setVisible(false);

        JPanel panel= new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill=GridBagConstraints.CENTER;
        gbc.insets=new Insets(25,0,0,0);
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=2;
        panel.add(labelQuestion,gbc);
        gbc.gridwidth=1;
        gbc.gridheight=1;
        gbc.gridx=0;
        gbc.gridy=2;
        panel.add(bouton1,gbc);
        gbc.gridx=1;
        gbc.gridy=2;
        panel.add(bouton2,gbc);
        gbc.gridx=0;
        gbc.gridy=3;
        panel.add(bouton3,gbc);
        gbc.gridx=1;
        gbc.gridy=3;
        panel.add(bouton4,gbc);
        gbc.gridx=0;
        gbc.gridy=4;
        panel.add(labelScore,gbc);
        gbc.gridx=1;
        gbc.gridy=4;

        panel.add(labelBonneReponse,gbc);

        panel.add(labelMauvaiseReponse,gbc);
        CONTAINER.add("JeuSolo",panel);
        listequestions=questions;
        bouton1.addActionListener(this);
        bouton2.addActionListener(this);
        bouton3.addActionListener(this);
        bouton4.addActionListener(this);

        score2=score;
        partiejouee=partie;


    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==boutons[listequestions.get(0).getBonnereponse()-1]){
            if(listequestions.size()==1){
                score2=score2+listequestions.get(0).getPoints();
                partiejouee.setScore(score2);
                Connexion.compteUtilise.ajoutePartie(partiejouee);
                try {
                    new FinJeu(score2);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                CARD.show(CONTAINER,"FinJeu");
            }else {
                String dernierereponse=listequestions.get(0).getReponses()[listequestions.get(0).getBonnereponse()-1];
                score2=score2+listequestions.get(0).getPoints();
                listequestions.remove(0);
                new JeuSolo(listequestions,score2,dernierereponse,partiejouee).bonneReponse();
                labelBonneReponse.setVisible(true);
                CARD.show(CONTAINER, "JeuSolo");
            }
        }else{
            if(listequestions.size()==1){
                score2=score2+listequestions.get(0).getMalus();
                if (score2<0){
                    score2=0;
                }
                partiejouee.setScore(score2);
                Connexion.compteUtilise.ajoutePartie(partiejouee);
                try {
                    new FinJeu(score2);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                CARD.show(CONTAINER,"FinJeu");
            }else {
                score2=score2+listequestions.get(0).getMalus();
                if (score2<0){
                    score2=0;
                }
                String dernierereponse=listequestions.get(0).getReponses()[listequestions.get(0).getBonnereponse()-1];
                listequestions.remove(0);
                new JeuSolo(listequestions,score2,dernierereponse,partiejouee).mauvaiseReponse();
                labelMauvaiseReponse.setVisible(true);
                CARD.show(CONTAINER, "JeuSolo");
            }
        }

    }
    public void bonneReponse(){
        labelBonneReponse.setVisible(true);
    }
    public void mauvaiseReponse(){
        labelMauvaiseReponse.setVisible(true);

    }
}
