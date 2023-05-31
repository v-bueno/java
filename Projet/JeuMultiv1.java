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
public class JeuMultiv1 implements Graphique, ActionListener  {
    private ArrayList<Question> listequestionsA;
    private ArrayList<Question> listequestionsB;
    private int scoreA;
    private int scoreB;
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
    private boolean equipeAFinie;
    private boolean equipeBFinie;
    private String nomEquipeA;
    private String nomEquipeB;

    /**
     * Affiche une interface avec 4 boutons pour répondre à la première question de la liste de l'équipe qui joue.
     * Si la liste n'a qu'une question le jeu s'arrête ensuite. Sinon on rappelle JeuMultiv1 en supprimant la première
     * question afin d'en avoir une nouvelle et en faisant toutes les modifications pour la gestion des équipes.
     * @param questionsA liste de questions restantes de l'équipe A
     * @param questionsB liste de questions restantes de l'équipe B
     * @param scoreA score en cours de l'équipe A
     * @param scoreB score en cours de l'équipe B
     * @param nomEquipeA nom de l'équipe A
     * @param nomEquipeB nom de l'équipe B
     * @param equipeAFinie booléen qui indique si l'équipe A a répondu à toutes ses questions
     * @param equipeBFinie booléen qui indique si l'équipe B a répondu à toutes ses questions
     * @param equipeDuTour échange entre 0 et 1 à chaque appel afin de déterminer l'équipe qui doit jouer
     * @param derniereReponse réponse de la question en cours
     * @param partie partie permettant de connaître les paramètres et de pouvoir rejouer avec les mêmes par la suite
     */
    JeuMultiv1(ArrayList<Question> questionsA,ArrayList<Question> questionsB,int scoreA,int scoreB,String nomEquipeA,String nomEquipeB,boolean equipeAFinie,boolean equipeBFinie,int equipeDuTour, String derniereReponse,Partie partie) {

        this.scoreA=scoreA;
        this.scoreB=scoreB;
        this.partie=partie;
        this.listequestionsA=questionsA;
        this.listequestionsB=questionsB;
        this.equipeDuTour = equipeDuTour;
        this.equipeAFinie = equipeAFinie;
        this.equipeBFinie = equipeBFinie;
        this.nomEquipeA = nomEquipeA;
        this.nomEquipeB=nomEquipeB;
        //Détermine quelle équipe doit jouer
        if (equipeDuTour==0){
            joueEquipeA();
        }
        else{
            joueEquipeB();
        }
        Connexion.menuBar.setVisible(false); //pour ne pas avoir de probleme avec le timer et un nouvel appel de fonction

    }


    /**
     * Fait jouer l'équipe A. Elle joue tant que ça liste n'est pas épuisé, qu'elle a la bonne réponse ou qu'elle a la
     * mauvaise réponse mais que l'équipe B à sa liste de questions épuisée.
     */
    public void joueEquipeA(){
        this.equipeDuProchainTour=(equipeDuTour+1)%2;
        this.chrono = new Timer(true);
        this.tempsRestant = listequestionsA.get(0).getTemps();
        TimerTask timeOutTask = new TimerTask() {
            @Override
            public void run() {
                if (tempsRestant==1) { //S'il n'y a plus de temps pour répondre
                    if(listequestionsA.size()!=1) { //S'il y a des questions après
                        //Définie les paramètres
                        String derniereReponse = listequestionsA.get(0).getReponses()[listequestionsA.get(0).getBonnereponse() - 1];
                        listequestionsA.remove(0);
                        if (!(equipeBFinie)) {
                            //Appel récursif en ajustant le joueur
                            new JeuMultiv1(listequestionsA, listequestionsB, scoreA, scoreB,nomEquipeA,nomEquipeB,equipeAFinie,equipeBFinie, equipeDuProchainTour, derniereReponse, partie).setLabelReponse("Temps écoulé ! La bonne réponse était " + derniereReponse, Color.orange);
                            CARD.show(CONTAINER, "JeuMulti");
                        }
                        else {
                            //Appel récursif en ajustant le joueur
                            new JeuMultiv1(listequestionsA, listequestionsB, scoreA, scoreB,nomEquipeA,nomEquipeB,equipeAFinie,equipeBFinie, equipeDuTour, derniereReponse, partie).setLabelReponse("Temps écoulé ! La bonne réponse était " + derniereReponse, Color.orange);
                            CARD.show(CONTAINER, "JeuMulti");
                        }
                        //on éteint le chrono car le temps est écoulé
                        chrono.cancel();
                    }
                    //Sinon il n'y a plus de questions
                    else{
                        //on indique que l'équipe A a fini
                        equipeAFinie=true;
                        //Si l'équipe B a déjà fini on met fin à la partie
                        if (equipeBFinie) {
                            try {
                                //Le jeu s'arrête
                                new FinJeuMulti(scoreA, scoreB,nomEquipeA,nomEquipeB, 1,partie).setLabelReponse("Temps écoulé ! La bonne réponse était " + listequestionsA.get(0).getReponses()[listequestionsA.get(0).getBonnereponse() - 1], Color.orange);
                                CARD.show(CONTAINER, "FinJeu");
                                chrono.cancel();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        //Sinon c'est à l'équipe A de jouer
                        else{
                            String dernierereponse = listequestionsA.get(0).getReponses()[listequestionsA.get(0).getBonnereponse() - 1];
                            new JeuMultiv1(listequestionsA,listequestionsB, scoreA,scoreB,nomEquipeA,nomEquipeB,equipeAFinie,equipeBFinie,equipeDuProchainTour, dernierereponse, partie).setLabelReponse("Temps écoulé ! La bonne réponse était "+dernierereponse,Color.orange);
                            CARD.show(CONTAINER, "JeuMulti");
                            //on éteint le chrono, le temps est écoulé
                            chrono.cancel();

                        }
                    }
                }
                //Sinon le temps n'est pas écoulé on décrémente le temps restant de 1 seconde
                else{
                    tempsRestant-=1;
                    labelTempsRestant.setText("Temps Restant : "+tempsRestant);
                }
            }
        };

        //Instancie les boutons correspondant à chaque réponse possible
        bouton1 = new BoutonReponse(chrono,0,listequestionsA);
        bouton2 = new BoutonReponse(chrono,1,listequestionsA);
        bouton3 = new BoutonReponse(chrono,2,listequestionsA);
        bouton4 = new BoutonReponse(chrono,3,listequestionsA);

        boutons = new BoutonReponse[]{bouton1, bouton2, bouton3, bouton4};

        labelEquipe =new JLabel();
        labelEquipe.setText("Au tour de l'équipe "+nomEquipeA);

        labelQuestion=new JLabel(listequestionsA.get(0).getQuestion());
        labelScoreA =new JLabel("Score équipe A : "+scoreA);
        labelScoreB =new JLabel("Score équipe B : "+scoreB);

        labelTempsRestant = new JLabel("Temps Restant : "+tempsRestant);
        labelTempsRestant.setForeground(Color.BLACK);
        labelTempsRestant.setVisible(true);

        //Place les composants le panel à l'endroit souhaité
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

        //On lance le chrono avec une période de 1 seconde
        chrono.schedule(timeOutTask,1000,1000);

    }

    /**
     * Fais jouer l'équipe B (fonctionne de la même que l'équipe A)
     */
    public void joueEquipeB(){
        this.equipeDuProchainTour=(equipeDuTour+1)%2;
        this.chrono = new Timer(true);
        this.tempsRestant = listequestionsB.get(0).getTemps();
        TimerTask timeOutTask = new TimerTask() {
            @Override
            public void run() {
                if (tempsRestant==1) {
                    if(listequestionsB.size()!=1) {
                        String dernierereponse = listequestionsB.get(0).getReponses()[listequestionsB.get(0).getBonnereponse() - 1];
                        listequestionsB.remove(0);
                        if (!(equipeAFinie)) {
                            new JeuMultiv1(listequestionsA, listequestionsB, scoreA, scoreB,nomEquipeA,nomEquipeB,equipeAFinie,equipeBFinie, equipeDuProchainTour, dernierereponse, partie).setLabelReponse("Temps écoulé ! La bonne réponse était " + dernierereponse, Color.orange);
                            CARD.show(CONTAINER, "JeuMulti");
                        }
                        else {
                            new JeuMultiv1(listequestionsA, listequestionsB, scoreA, scoreB,nomEquipeA,nomEquipeB,equipeAFinie,equipeBFinie, equipeDuTour, dernierereponse, partie).setLabelReponse("Temps écoulé ! La bonne réponse était " + dernierereponse, Color.orange);
                            CARD.show(CONTAINER, "JeuMulti");
                        }
                        chrono.cancel();
                    }else{
                        equipeBFinie=true;
                        if(equipeAFinie) {
                            try {
                                new FinJeuMulti(scoreA, scoreB, nomEquipeA,nomEquipeB,1,partie).setLabelReponse("Temps écoulé ! La bonne réponse était " + listequestionsB.get(0).getReponses()[listequestionsB.get(0).getBonnereponse() - 1], Color.orange);
                                CARD.show(CONTAINER, "FinJeu");
                                chrono.cancel();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else{
                            String dernierereponse = listequestionsA.get(0).getReponses()[listequestionsA.get(0).getBonnereponse() - 1];
                            new JeuMultiv1(listequestionsA, listequestionsB, scoreA, scoreB,nomEquipeA,nomEquipeB,equipeAFinie,equipeBFinie, equipeDuProchainTour, dernierereponse, partie).setLabelReponse("Temps écoulé ! La bonne réponse était " + dernierereponse, Color.orange);
                            CARD.show(CONTAINER, "JeuMulti");

                        }
                    }
                }
                else{
                    tempsRestant-=1; //Decrémente le temps restants chaque seconde et set le label
                    labelTempsRestant.setText("Temps Restant : "+tempsRestant);
                }
            }
        };

        bouton1 = new BoutonReponse(chrono,0,listequestionsB);
        bouton2 = new BoutonReponse(chrono,1,listequestionsB);
        bouton3 = new BoutonReponse(chrono,2,listequestionsB);
        bouton4 = new BoutonReponse(chrono,3,listequestionsB);

        boutons = new BoutonReponse[]{bouton1, bouton2, bouton3, bouton4};

        labelEquipe =new JLabel();
        labelEquipe.setText("Au tour de l'équipe "+nomEquipeB);
        labelQuestion=new JLabel(listequestionsB.get(0).getQuestion());
        labelScoreA =new JLabel("Score équipe "+nomEquipeA+" : "+scoreA);
        labelScoreB =new JLabel("Score équipe "+nomEquipeB+ " : "+scoreB);

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
     * Action effectuée lors d'un appui du bouton, le score est modifié selon la réponse et un appel récursif au jeu
     * est effectué, l'équipe qui doit jouer au prochain tour est décidée selon si l'équipe actuelle a bien ou mal
     * répondu et selon si la prochaine équipe à jouer a encore des questions dans sa liste.
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e){
        //Si c'est à l'équipe A de jouer
        if (equipeDuTour == 0) {
            //On vérifie que c'est la bonne réponse
            if (e.getSource() == boutons[listequestionsA.get(0).getBonnereponse() - 1].getBouton()) {
                //Si c'est la dernière question de la liste de l'équipe A
                if (listequestionsA.size() == 1) {
                    //On indique que l'équipe A a fini, on met à jour le score et on enregistre la bonne réponse
                    equipeAFinie = true;
                    scoreA = scoreA + listequestionsA.get(0).getPoints();
                    partie.setScore(scoreA);
                    String dernierereponse = listequestionsA.get(0).getReponses()[listequestionsA.get(0).getBonnereponse() - 1];
                    //Puis si l'équipe B a déjà répondue à toutes ses questions on met fin à la partie
                    if (equipeBFinie) {
                        try {
                            new FinJeuMulti(scoreA, scoreB, nomEquipeA,nomEquipeB,1,partie).setLabelReponse("Bonne réponse !", Color.green);
                            ;
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        CARD.show(CONTAINER, "FinJeu");
                    }
                    //Sinon comme l'équipe A a fini on fait jouer l'équipe B
                    else {
                        new JeuMultiv1(listequestionsA, listequestionsB, scoreA, scoreB,nomEquipeA,nomEquipeB,equipeAFinie,equipeBFinie, equipeDuProchainTour, dernierereponse, partie).setLabelReponse("Bonne réponse !", Color.green);
                        CARD.show(CONTAINER, "JeuMulti");
                    }
                }
                //Sinon, l'équipe A a encore des questions à jouer, on refait jouer l'équipe A après mise à jour du score
                else {
                    String dernierereponse = listequestionsA.get(0).getReponses()[listequestionsA.get(0).getBonnereponse() - 1];
                    scoreA = scoreA + listequestionsA.get(0).getPoints();
                    listequestionsA.remove(0);
                    new JeuMultiv1(listequestionsA, listequestionsB, scoreA, scoreB,nomEquipeA,nomEquipeB,equipeAFinie,equipeBFinie, equipeDuTour, dernierereponse, partie).setLabelReponse("Bonne réponse !", Color.green);
                    CARD.show(CONTAINER, "JeuMulti");
                }
            }
            //Si l'équipe A s'est trompée
            else {
                //Si c'était la dernière question de l'équipe A, on indique que l'équipe A a fini, on met à jour le score, on enregistre la derniere reponse et on réduit la liste de questions
                if (listequestionsA.size() == 1) {
                    equipeAFinie=true;
                    String dernierereponse = listequestionsA.get(0).getReponses()[listequestionsA.get(0).getBonnereponse() - 1];
                    scoreA = scoreA + listequestionsA.get(0).getMalus();
                    if (scoreA < 0) {
                        scoreA = 0;
                    }
                    partie.setScore(scoreA);
                    //Si l'équipe B à fini on met fin à la partie
                    if (equipeBFinie) {
                        try {
                            new FinJeuMulti(scoreA, scoreB, nomEquipeA,nomEquipeB,1,partie).setLabelReponse("Mauvaise réponse ! La bonne réponse était " + listequestionsA.get(0).getReponses()[listequestionsA.get(0).getBonnereponse() - 1], Color.red);
                            ;
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        CARD.show(CONTAINER, "FinJeu");
                    }
                    //Sinon il faut donner la main à l'équipe B pour qu'elle finisse sa liste
                    else{
                        new JeuMultiv1(listequestionsA, listequestionsB, scoreA, scoreB,nomEquipeA,nomEquipeB,equipeAFinie,equipeBFinie, equipeDuProchainTour, dernierereponse, partie).setLabelReponse("Mauvaise réponse ! La bonne réponse était " + dernierereponse, Color.red);
                        CARD.show(CONTAINER, "JeuMulti");
                    }
                }
                //Si l'équipe A a mal répondu on met à jour le score, on enregistre la réponse valide de la question et on réduit la liste de questions
                else {
                    scoreA = scoreA + listequestionsA.get(0).getMalus();
                    if (scoreA < 0) {
                        scoreA = 0;
                    }
                    String dernierereponse = listequestionsA.get(0).getReponses()[listequestionsA.get(0).getBonnereponse() - 1];
                    listequestionsA.remove(0);
                    //Si l'équipe B n'a pas fini on laisse l'équipe B jouer car c'est son tour
                    if (!(equipeBFinie)) {
                        new JeuMultiv1(listequestionsA, listequestionsB, scoreA, scoreB,nomEquipeA,nomEquipeB,equipeAFinie,equipeBFinie, equipeDuProchainTour, dernierereponse, partie).setLabelReponse("Mauvaise réponse ! La bonne réponse était " + dernierereponse, Color.red);
                        CARD.show(CONTAINER, "JeuMulti");
                    }
                    //Sinon on refait jouer l'équipe A pour qu'elle finisse sa liste
                    else{
                        new JeuMultiv1(listequestionsA, listequestionsB, scoreA, scoreB,nomEquipeA,nomEquipeB,equipeAFinie,equipeBFinie, equipeDuTour, dernierereponse, partie).setLabelReponse("Mauvaise réponse ! La bonne réponse était " + dernierereponse, Color.red);
                        CARD.show(CONTAINER, "JeuMulti");
                    }
                }
            }
        }
        //Sinon c'est au tour de l'équipe B.
        else{
            //On vérifie que c'est la bonne réponse
            if (e.getSource() == boutons[listequestionsB.get(0).getBonnereponse() - 1].getBouton()) {
                //Si c'est la dernière question de la liste de l'équipe B
                if (listequestionsB.size() == 1) {
                    //On indique que l'équipe B a fini, on met à jour le score et on enregistre la bonne réponse
                    equipeBFinie=true;
                    scoreB = scoreB + listequestionsB.get(0).getPoints();
                    partie.setScore(scoreB);
                    String dernierereponse = listequestionsB.get(0).getReponses()[listequestionsB.get(0).getBonnereponse() - 1];
                    //Si l'équipe A a fini on met fin à la partie
                    if(equipeAFinie) {
                        try {
                            new FinJeuMulti(scoreA, scoreB, nomEquipeA,nomEquipeB,1,partie).setLabelReponse("Bonne réponse !", Color.green);
                            ;
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        CARD.show(CONTAINER, "FinJeu");
                    }
                    //Sinon on laisse l'équipe A finir sa liste de questions
                    else{
                        new JeuMultiv1(listequestionsA, listequestionsB, scoreA, scoreB,nomEquipeA,nomEquipeB,equipeAFinie,equipeBFinie, equipeDuProchainTour, dernierereponse, partie).setLabelReponse("Bonne réponse !", Color.green);
                        CARD.show(CONTAINER, "JeuMulti");
                    }
                }
                //Sinon il reste des questions dans la liste de l'équipe B et elle a bien répondu, elle doit donc rejouer
                else {
                    //On met à jour le score, on enregistre la bonne réponse et on réduit la liste de question puis on fait rejouer l'équipe B
                    String dernierereponse = listequestionsB.get(0).getReponses()[listequestionsB.get(0).getBonnereponse() - 1];
                    scoreB = scoreB + listequestionsB.get(0).getPoints();
                    listequestionsB.remove(0);
                    new JeuMultiv1(listequestionsA, listequestionsB, scoreA, scoreB,nomEquipeA,nomEquipeB,equipeAFinie,equipeBFinie, equipeDuTour, dernierereponse, partie).setLabelReponse("Bonne réponse !", Color.green);
                    CARD.show(CONTAINER, "JeuMulti");
                }
            }
            //Sinon l'équipe B s'est trompée.
            else{
                    //Si c'était la dernière question de l'équipe B
                    if (listequestionsB.size() == 1) {
                        //On indique que l'équipe B a fini, on met à jour le score, on enregistre la bonne réponse et on réduit la taille de la liste
                        equipeBFinie=true;
                        String dernierereponse = listequestionsB.get(0).getReponses()[listequestionsB.get(0).getBonnereponse() - 1];
                        scoreB = scoreB + listequestionsB.get(0).getMalus();
                        if (scoreB < 0) {
                            scoreB = 0;
                        }
                        partie.setScore(scoreB);
                        //Si l'équipe A a déjà fini on met fin à la partie
                        if (equipeAFinie) {
                            try {
                                new FinJeuMulti(scoreA, scoreB, nomEquipeA,nomEquipeB,1,partie).setLabelReponse("Mauvaise réponse ! La bonne réponse était " + listequestionsB.get(0).getReponses()[listequestionsB.get(0).getBonnereponse() - 1], Color.red);
                                ;
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            } catch (ClassNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                            CARD.show(CONTAINER, "FinJeu");
                        }
                        //Sinon on laisse l'équipe A finir sa liste de questions
                        else {
                            new JeuMultiv1(listequestionsA,listequestionsB, scoreA, scoreB,nomEquipeA,nomEquipeB,equipeAFinie,equipeBFinie, equipeDuProchainTour, dernierereponse, partie).setLabelReponse("Mauvaise réponse ! La bonne réponse était " + dernierereponse, Color.red);
                            CARD.show(CONTAINER, "JeuMulti");
                        }
                    }
                    //Sinon il reste des questions dans la liste de l'équipe B
                    else {
                        //On met à jour le score, on enregistre la bonne réponse et on réduit la liste de questions
                        scoreB = scoreB + listequestionsB.get(0).getMalus();
                        if (scoreB < 0) {
                            scoreB = 0;
                        }
                        String dernierereponse = listequestionsB.get(0).getReponses()[listequestionsB.get(0).getBonnereponse() - 1];
                        listequestionsB.remove(0);
                        //Si l'équipe A n'a pas fini, c'est a son tour donc on la laisse jouer
                        if (!(equipeAFinie)) {
                            new JeuMultiv1(listequestionsA, listequestionsB, scoreA, scoreB,nomEquipeA,nomEquipeB,equipeAFinie,equipeBFinie, equipeDuProchainTour, dernierereponse, partie).setLabelReponse("Mauvaise réponse ! La bonne réponse était " + dernierereponse, Color.red);
                            CARD.show(CONTAINER, "JeuMulti");
                        }
                        //Sinon l'équipe B rejoue, elle doit finir sa liste
                        else{
                            new JeuMultiv1(listequestionsA, listequestionsB, scoreA, scoreB,nomEquipeA,nomEquipeB,equipeAFinie,equipeBFinie, equipeDuTour, dernierereponse, partie).setLabelReponse("Mauvaise réponse ! La bonne réponse était " + dernierereponse, Color.red);
                            CARD.show(CONTAINER, "JeuMulti");
                        }
                    }
                }
            }
        //On éteint le chrono car l'équipe a répondu avant la fin de celui-ci
        chrono.cancel();
    }

    /**
     * Change le texte affiché dans le labelReponse
     * @param text texte à afficher
     * @param color couleur du texte
     */
    public void setLabelReponse(String text,Color color){
        labelReponse.setText(text);
        labelReponse.setForeground(color);
    }

}
