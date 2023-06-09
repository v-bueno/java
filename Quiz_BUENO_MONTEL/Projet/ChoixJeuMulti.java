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

public class ChoixJeuMulti implements Graphique,ActionListener {
    private JCheckBox theme1 = new JCheckBox("Sport");
    private JCheckBox theme2 = new JCheckBox("Musique");
    private JCheckBox theme3 = new JCheckBox("Géographie");
    private JCheckBox theme4 = new JCheckBox("Cinéma");
    private String[] difficultes = {"Facile", "Normal", "Difficile"};
    private JCheckBox[] themes= {theme1,theme2,theme3,theme4};
    private JComboBox difficultecombobox= new JComboBox(difficultes);
    private String[] init={"Aucune"};


    private JComboBox nombrequestioncombobox= new JComboBox(init);
    private JButton jouerV1 = new JButton("Jouer à la V1!");
    private JButton jouerV2 = new JButton("Jouer à la V2!");
    private JLabel labelnombrequestion;
    private JLabel labelErreur = new JLabel();
    private JLabel labelEquipeA=new JLabel("Nom de l'équipe A");
    private JLabel labelEquipeB=new JLabel("Nom de l'équipe B");
    private JTextField fieldEquipeA=new JTextField("Équipe A");
    private JTextField fieldEquipeB=new JTextField("Équipe B");



    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc=new GridBagConstraints();

    /**
     * Créer la page qui permet au joueur de choisir les paramètres pour lancer son jeu en équipe
     */
    ChoixJeuMulti() {
        //Instancie les labels
        JLabel labeltheme=new JLabel("Cochez les thèmes :");
        JLabel labeldifficulte=new JLabel("Difficulté :");
        labelnombrequestion= new JLabel("Nombre de questions chacun :");
        labelErreur.setForeground(Color.red);
        //Place les composants à l'endroit souhaité
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
        panel.add(labelEquipeA,gbc);
        gbc.gridx=1;
        panel.add(labelEquipeB,gbc);
        gbc.insets=new Insets(0,0,0,0);
        gbc.gridx=0;
        gbc.gridy=10;
        panel.add(fieldEquipeA,gbc);
        gbc.gridx=1;
        panel.add(fieldEquipeB,gbc);
        gbc.insets=new Insets(15,0,0,0);
        gbc.gridx=0;
        gbc.gridy=11;
        panel.add(jouerV1,gbc);
        gbc.gridx=1;
        panel.add(jouerV2,gbc);
        gbc.gridx=0;
        gbc.gridy=12;
        panel.add(labelErreur,gbc);

        CONTAINER.add(panel,"choixjeumulti");
        theme1.addActionListener(this);
        theme2.addActionListener(this);
        theme3.addActionListener(this);
        theme4.addActionListener(this);
        difficultecombobox.addActionListener(this);
        //action à réaliser si on appuie sur le bouton jouerV1
        jouerV1.addActionListener(new ActionListener() {
            //On lance une partie dans le mode MultiV1
            @Override
            public void actionPerformed(ActionEvent e) {
                if(((String)nombrequestioncombobox.getSelectedItem()).equals("Aucune")) {
                    labelErreur.setText("Sélectionner un nombre de questions");
                }else if(fieldEquipeA.getText().equals("")||fieldEquipeB.getText().equals("")||fieldEquipeA.getText().equals(fieldEquipeB.getText())){
                    labelErreur.setText("Problème de nom d'équipe");
                }
                else {
                    ArrayList<String> themeselectionnes = new ArrayList<>();
                    for (JCheckBox theme : themes) {
                        if (theme.isSelected()) {
                            themeselectionnes.add(theme.getText());
                        }
                    }
                    int nombrequestions = (Integer.parseInt((String) nombrequestioncombobox.getSelectedItem()));
                    String difficulte = (String) difficultecombobox.getSelectedItem();
                    lanceJeuV1(themeselectionnes, difficulte, nombrequestions);
                }
            }
        });
        //action à réaliser en cas d'appui sur jouerv2
        jouerV2.addActionListener(new ActionListener() {
            //On lance une partie dans le mode MultiV2
            @Override
            public void actionPerformed(ActionEvent e) {
                if(((String)nombrequestioncombobox.getSelectedItem()).equals("Aucune")) {
                    labelErreur.setText("Sélectionner un nombre de questions");
                }else if(fieldEquipeA.getText().equals("")||fieldEquipeB.getText().equals("")||fieldEquipeA.getText().equals(fieldEquipeB.getText())){
                    labelErreur.setText("Problème de nom d'équipe");
                }
                else {
                    ArrayList<String> themeselectionnes = new ArrayList<>();
                    for (JCheckBox theme : themes) {
                        if (theme.isSelected()) {
                            themeselectionnes.add(theme.getText());
                        }
                    }
                    int nombrequestions = (Integer.parseInt((String) nombrequestioncombobox.getSelectedItem()));
                    String difficulte = (String) difficultecombobox.getSelectedItem();
                    lanceJeuV2(themeselectionnes, difficulte, nombrequestions);
                }
            }
        });

    }
    public void actionPerformed(ActionEvent e){
        panel.remove(nombrequestioncombobox);
        ArrayList<Question> listequestion=new ArrayList<>();
        for (JCheckBox theme:themes){
            if(theme.isSelected()){
                try {
                    ArrayList<Question> temporaire =lisCSV(theme.getText(),(String)difficultecombobox.getSelectedItem());
                    listequestion.addAll(temporaire);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        ArrayList<String> nombrequestions= new ArrayList<>();
        if(listequestion.size()==0){
            nombrequestions.add("Aucune");
        }
        else {
            for (int i = 1; i <= listequestion.size()/2; i++) {
                nombrequestions.add(String.valueOf(i));
            }
        }
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(nombrequestions.toArray(new String[0]));
        nombrequestioncombobox=new JComboBox(comboBoxModel);
        gbc.gridx=0;
        gbc.gridy=8;
        gbc.insets=new Insets(0,0,0,0);
        panel.add(nombrequestioncombobox,gbc);
        CONTAINER.add(panel,"choixjeumulti");
        CARD.show(CONTAINER,"choixjeumulti");
    }

    /**
     * Récupère toutes les questions du thème et de la difficulté choisi
     * @param theme theme choisi
     * @param difficulte difficulté choisie
     * @return renvoi la liste des questions qui correspondent à la difficulté et au thème choisi
     * @throws IOException
     */
    public ArrayList<Question> lisCSV(String theme,String difficulte) throws IOException {
        FileReader file= new FileReader("Question.csv");
        CSVReader reader= new CSVReader(file);
        ArrayList<Question> listequestion=new ArrayList<>();
        List<String[]> lines = reader.readAll();
        for (String[] line : lines){ //Améliorer ensuite avec for-each (fonctions lambda)
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
     * Lance une partie dans le mode Multi V1 avec le thème, la difficulté et le nombre de questions choisis
     * @param themes themes choisis
     * @param difficulte difficulté choisie
     * @param nombrequestions nombre de questions choisi
     */
    public void lanceJeuV1(ArrayList<String> themes,String difficulte,int nombrequestions){
        ArrayList<Question> listequestion = new ArrayList<>();
        Iterator<String> iterator = themes.iterator();
        //On utilise un while pour parcourir la liste des thèmes et récupérer les questions associées
        while (iterator.hasNext()) {
            String theme = iterator.next();
            try {
                ArrayList<Question> temporaire = lisCSV(theme, difficulte);
                listequestion.addAll(temporaire);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        //On mélange la liste de toutes les questions obtenues
        Collections.shuffle(listequestion);
        //On rempli la liste destinée à l'équipe A en prenant les n premières questions
        List<Question> sublistA = listequestion.subList(0, nombrequestions);
        ArrayList<Question> nouvellelisteA = new ArrayList<>(sublistA);
        //On rempli la liste destinée à l'équipe B en prenant les n questions suivantes
        List<Question> sublistB = listequestion.subList(nombrequestions, nombrequestions*2);
        ArrayList<Question> nouvellelisteB = new ArrayList<>(sublistB);
        String[] themesjeu = new String[themes.size()];
        themes.toArray(themesjeu);
        //On lance la partie
        Partie partie = new Partie("Jeu Duo", difficulte, themesjeu, nombrequestions);
        new JeuMultiv1(nouvellelisteA,nouvellelisteB, 0, 0,fieldEquipeA.getText(),fieldEquipeB.getText(),false,false,0,"",partie);
        CARD.show(CONTAINER, "JeuMulti");
    }

    /**
     * Lance une partie dans le mode Multi V1 avec le thème, la difficulté et le nombre de questions choisis
     * @param themes thème choisi
     * @param difficulte difficulté choisie
     * @param nombrequestions nombre de questions choisi
     */
    public void lanceJeuV2(ArrayList<String> themes,String difficulte,int nombrequestions){
        ArrayList<Question> listequestion = new ArrayList<>();
        Iterator<String> iterator = themes.iterator();
        //On utilise un while pour parcourir la liste des thèmes et récupérer les questions associées
        while (iterator.hasNext()) {
            String theme = iterator.next();
            try {
                ArrayList<Question> temporaire = lisCSV(theme, difficulte);
                listequestion.addAll(temporaire);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        //On mélange la liste de toutes les questions obtenues
        Collections.shuffle(listequestion);
        //On rempli la liste destinée à la partie en prenant les 2n premières questions de la liste
        List<Question> sublist = listequestion.subList(0, nombrequestions*2);
        ArrayList<Question> nouvelleliste = new ArrayList<>(sublist);
        String[] themesjeu = new String[themes.size()];
        themes.toArray(themesjeu);
        //On lance la partie
        Partie partie = new Partie("Jeu Duo", difficulte, themesjeu, nombrequestions);
        new JeuMultiv2(nouvelleliste, 0, 0,fieldEquipeA.getText(),fieldEquipeB.getText(),0,"",partie);
        CARD.show(CONTAINER, "JeuMulti");
    }

    public void setLabel(String label) {
        labelnombrequestion.setText(label);
    }
}
