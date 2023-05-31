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
    JCheckBox theme1 = new JCheckBox("Sport");
    JCheckBox theme2 = new JCheckBox("Musique");
    JCheckBox theme3 = new JCheckBox("Géographie");
    JCheckBox theme4 = new JCheckBox("Cinéma");
    String[] difficultes = {"Facile", "Normal", "Difficile"};
    JCheckBox[] themes= {theme1,theme2,theme3,theme4};
    JComboBox difficultecombobox= new JComboBox(difficultes);
    String[] init={"Aucune"};


    JComboBox nombrequestioncombobox= new JComboBox(init);
    JButton jouerV1 = new JButton("Jouer à la V1!");
    JButton jouerV2 = new JButton("Jouer à la V2!");
    JLabel labelnombrequestion;
    JLabel labelErreur = new JLabel("Sélectionnez un nombre");
    JLabel labelEquipeA=new JLabel("Nom de l'équipe A");
    JLabel labelEquipeB=new JLabel("Nom de l'équipe B");
    JTextField fieldEquipeA=new JTextField();
    JTextField fieldEquipeB=new JTextField();



    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc=new GridBagConstraints();


    ChoixJeuMulti() {
        JLabel labeltheme=new JLabel("Cochez les thèmes :");
        JLabel labeldifficulte=new JLabel("Difficulté :");
        labelnombrequestion= new JLabel("Nombre de questions chacun :");
        labelErreur.setForeground(Color.red);
        labelErreur.setVisible(false);
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
        jouerV1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(((String)nombrequestioncombobox.getSelectedItem()).equals("Aucune")) {
                    labelErreur.setVisible(true);
                }else {
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

        jouerV2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(((String)nombrequestioncombobox.getSelectedItem()).equals("Aucune")) {
                    labelErreur.setVisible(true);
                }else {
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
    public void lanceJeuV1(ArrayList<String> themes,String difficulte,int nombrequestions){
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
        Collections.shuffle(listequestion);
        List<Question> sublistA = listequestion.subList(0, nombrequestions);
        ArrayList<Question> nouvellelisteA = new ArrayList<>(sublistA);
        List<Question> sublistB = listequestion.subList(nombrequestions, nombrequestions*2);
        ArrayList<Question> nouvellelisteB = new ArrayList<>(sublistB);
        String[] themesjeu = new String[themes.size()];
        themes.toArray(themesjeu);
        Partie partie = new Partie("Jeu Duo", difficulte, themesjeu, nombrequestions);
        new JeuMultiv1(nouvellelisteA,nouvellelisteB, 0, 0,fieldEquipeA.getText(),fieldEquipeB.getText(),false,false,0,"",partie);
        CARD.show(CONTAINER, "JeuMulti");
    }
    public void lanceJeuV2(ArrayList<String> themes,String difficulte,int nombrequestions){
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
        Collections.shuffle(listequestion);
        List<Question> sublist = listequestion.subList(0, nombrequestions*2);
        ArrayList<Question> nouvelleliste = new ArrayList<>(sublist);
        String[] themesjeu = new String[themes.size()];
        themes.toArray(themesjeu);
        Partie partie = new Partie("Jeu Duo", difficulte, themesjeu, nombrequestions);
        new JeuMultiv2(nouvelleliste, 0, 0,fieldEquipeA.getText(),fieldEquipeB.getText(),0,"",partie);
        CARD.show(CONTAINER, "JeuMulti");
    }

    public void setLabel(String label) {
        labelnombrequestion.setText(label);
    }
}
