import com.opencsv.CSVReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class ChoixJeuSolo implements Graphique, ActionListener {
    private JCheckBox theme1 = new JCheckBox("Sport");
    private JCheckBox theme2 = new JCheckBox("Musique");
    private JCheckBox theme3 = new JCheckBox("Géographie");
    private JCheckBox theme4 = new JCheckBox("Cinéma");
    private String[] difficultes = {"Facile", "Normal", "Difficile"};
    private JCheckBox[] themes= {theme1,theme2,theme3,theme4};
    private JComboBox difficultecombobox= new JComboBox(difficultes);
    private String[] init={"Aucune"};


    private JComboBox nombrequestioncombobox= new JComboBox(init);
    private JButton jouer = new JButton("Jouer !");

    private JLabel labelErreur = new JLabel("Sélectionnez un nombre");

    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints gbc=new GridBagConstraints();

    /**
     *
     */
    ChoixJeuSolo() {
        //Créer les label
        JLabel labeltheme=new JLabel("Cochez les thèmes :");
        JLabel labeldifficulte=new JLabel("Difficulté :");
        JLabel labelnombrequestion= new JLabel("Nombre de questions :");
        labelErreur.setForeground(Color.red);
        labelErreur.setVisible(false);

        //Place les composants dans le panel à l'endroit souhaité
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.gridx=0;
        gbc.gridy=0;
        panel.add(labeltheme,gbc);
        gbc.gridx=0;
        gbc.gridy=1;
        panel.add(theme1,gbc);
        gbc.gridx=0;
        gbc.gridy=2;
        panel.add(theme2,gbc);
        gbc.gridx=0;
        gbc.gridy=3;
        panel.add(theme3,gbc);
        gbc.gridx=0;
        gbc.gridy=4;
        panel.add(theme4,gbc);
        gbc.insets=new Insets(15,0,0,0);
        gbc.gridx=0;
        gbc.gridy=5;
        panel.add(labeldifficulte,gbc);
        gbc.insets=new Insets(0,0,0,0);
        gbc.gridx=0;
        gbc.gridy=6;
        panel.add(difficultecombobox,gbc);
        gbc.insets=new Insets(15,0,0,0);
        gbc.gridx=0;
        gbc.gridy=7;
        panel.add(labelnombrequestion,gbc);
        gbc.insets=new Insets(0,0,0,0);
        gbc.gridx=0;
        gbc.gridy=8;
        panel.add(nombrequestioncombobox,gbc);
        gbc.insets=new Insets(15,0,0,0);

        gbc.gridx=0;
        gbc.gridy=9;
        panel.add(jouer,gbc);

        gbc.gridx=0;
        gbc.gridy=10;
        panel.add(labelErreur,gbc);

        CONTAINER.add(panel,"choixjeu");

        //Ajoute les Action Listener aux composants permettant de choisir les paramètres
        theme1.addActionListener(this);
        theme2.addActionListener(this);
        theme3.addActionListener(this);
        theme4.addActionListener(this);
        difficultecombobox.addActionListener(this);

        //Ajoute l'action listener pour jouer au bouton jouer
        jouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Si le joueur ne sélectionne pas de question affiche un message d'erreur
                if(((String)nombrequestioncombobox.getSelectedItem()).equals("Aucune")) {
                    labelErreur.setVisible(true);
                }else { //Si le nombre est valide
                    //On récupère les informations nécessaires dans les composantspour lancer le jeu
                    ArrayList<String> themeselectionnes = new ArrayList<>();
                    for (JCheckBox theme : themes) {
                        if (theme.isSelected()) {
                            themeselectionnes.add(theme.getText());
                        }
                    }
                    int nombrequestions = (Integer.parseInt((String) nombrequestioncombobox.getSelectedItem()));
                    String difficulte = (String) difficultecombobox.getSelectedItem();
                    lanceJeu(themeselectionnes, difficulte, nombrequestions); //lance le jeu
                }
            }
        });

    }

    /**
     *À chaque fois qu'un composant qui permet de sélectionner un paramètre de jeu est modifié, on
     * met à jour le combobox contenant le nombre de question afin d'avoir le nombre de questions
     * disponible avec ces paramètres
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e){
        panel.remove(nombrequestioncombobox);
        ArrayList<Question> listequestion=new ArrayList<>();
        //On parcourt une liste des thèmes sélectionnés
        for (JCheckBox theme:themes){
            if(theme.isSelected()){
                try {
                    //Pour chaque thème on récupère une liste temporaire de question ayant le même thème et la difficulté choisie
                    ArrayList<Question> temporaire =lisCSV(theme.getText(),(String)difficultecombobox.getSelectedItem());
                    //On ajoute la liste temporaire à la liste générale
                    listequestion.addAll(temporaire);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        /* Ensuite on crée un nouveau combobox avec chaque nombre possible compris entre 1 et le nombre de questions
        dans listquestion et on met aucune dans la nouvelle combobox si listequestion est vide */
        ArrayList<String> nombrequestions= new ArrayList<>();
        if(listequestion.size()==0){
            nombrequestions.add("Aucune");
        }
        else {
            for (int i = 1; i <= listequestion.size(); i++) {
                nombrequestions.add(String.valueOf(i));
            }
        }
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(nombrequestions.toArray(new String[0]));
        nombrequestioncombobox=new JComboBox(comboBoxModel);
        gbc.gridx=0;
        gbc.gridy=8;
        gbc.insets=new Insets(0,0,0,0);
        panel.add(nombrequestioncombobox,gbc);
        CONTAINER.add(panel,"choixjeu");
        CARD.show(CONTAINER,"choixjeu");
    }

    /**
     * Renvoie une ArrayList de question de toutes les questions dans Question.csv qui ont les paramètres d'entrées
     * @param theme thème dans lequel on doit récupérer les questions.
     * @param difficulte niveau de difficulté que les questions doivent avoir.
     * @return Array avec toutes les questions de la difficulté et du thème choisi.
     * @throws IOException
     */
    public ArrayList<Question> lisCSV(String theme,String difficulte) throws IOException {
        FileReader file= new FileReader("Question.csv");
        CSVReader reader= new CSVReader(file);
        ArrayList<Question> listequestion=new ArrayList<>();
        List<String[]> lines = reader.readAll();
        //Parcourt les lignes du csv sous forme de tableau (chaque case est séparé par une virgule dans le csv)
        for (String[] line : lines){
            if(line[5].equals(theme)){
                if(line[6].equals(difficulte)){
                    String []reponses={line[1],line[2],line[3],line[4]};
                    Question question=new Question(line[0],reponses,line[5],line[6],Integer.parseInt(line[7]),Integer.parseInt(line[8]),Integer.parseInt(line[9]),Integer.parseInt(line[10]));
                    listequestion.add(question);
                }
            }
        }
        return listequestion;
    }

    /**
     * Lance le jeu via un appel à Jeu Solo
     * @param themes Liste des thèmes dans lesquels les questions sont choisies.
     * @param difficulte niveau de difficulté des questions.
     * @param nombrequestions nombre de questions jouées.
     */
    public void lanceJeu(ArrayList<String> themes,String difficulte,int nombrequestions){
        //Récupère la liste de toutes les questions qui ont ces paramètres dans le Question.csv
        ArrayList<Question> listequestion = new ArrayList<>();
        Iterator<String> iterator = themes.iterator();
        while (iterator.hasNext()) {
            String theme = iterator.next();
            try {
                ArrayList<Question> temporaire = lisCSV(theme, difficulte);
                listequestion.addAll(temporaire);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        //On mélange la liste de questions
        Collections.shuffle(listequestion);
        //On réduit la taille de la liste au nombre de questions voulus
        List<Question> sublist = listequestion.subList(0, nombrequestions);
        ArrayList<Question> nouvelleliste = new ArrayList<>(sublist);
        //On appelle JeuSolo et on instancie des objets selon le bon format
        String[] themesjeu = new String[themes.size()];
        themes.toArray(themesjeu);
        Partie partie = new Partie("Jeu Solo", difficulte, themesjeu, nombrequestions);
        new JeuSolo(nouvelleliste, 0, "", partie);
        CARD.show(CONTAINER, "JeuSolo");
        }
    }



