import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 */
public class NouvelleQuestion extends JFrame implements Graphique{
    /**
     *
     */
    NouvelleQuestion(){

        String[] difficultes ={"Facile","Normal","Difficile"};
        String[] themes ={"Sport","Musique","Géographie","Cinéma"};
        String[] entierreponse={"1","2","3","4"};
        String[] maluspossible={"0","-1","-2","-3"};
        String[] tempspossible={"10","15","20","25","30"};

        JComboBox themecombobox=new JComboBox(themes);
        JComboBox difficultecombobox= new JComboBox(difficultes);
        JButton retour=new JButton("Retour");
        JLabel choixtheme=new JLabel("Choisir le thème : ");
        JLabel choixdifficulte= new JLabel("Choisir la difficulté :");
        JLabel questionlabel= new JLabel("Entrer une nouvelle question");
        JLabel reponse=new JLabel("Entrer 4 réponses associées");
        JTextField questionField= new JTextField();
        JTextField reponse1 = new JTextField();
        JTextField reponse2 = new JTextField();
        JTextField reponse3 = new JTextField();
        JTextField reponse4 = new JTextField();
        JLabel entierlabel= new JLabel("Indiquer l'entier de la bonne réponse");
        JComboBox bonnereponse=new JComboBox(entierreponse);
        JLabel pointslabel= new JLabel("Choisissez les points ");
        JComboBox pointscombobox= new JComboBox(entierreponse);
        JLabel maluslabel=new JLabel("Choisir le malus");
        JComboBox maluscombobox=new JComboBox(maluspossible);
        JLabel tempslabel= new JLabel("Choisir le temps ");
        JComboBox tempscombobox = new JComboBox(tempspossible);
        JLabel labelErreur = new JLabel("Veuillez rentrer plus de 3 caractères pour la question et les réponses");
        labelErreur.setForeground(Color.red);
        labelErreur.setVisible(false);
        JButton valider=new JButton("Valider");


        JPanel  themePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill=GridBagConstraints.HORIZONTAL;

        gbc.gridx=0;
        gbc.gridy=0;
        themePanel.add(choixtheme,gbc);

        gbc.gridx=1;
        gbc.gridy=0;
        themePanel.add(themecombobox,gbc);

        gbc.insets=new Insets(10,0,0,0);
        gbc.gridx=0;
        gbc.gridy=1;
        themePanel.add(choixdifficulte,gbc);

        gbc.gridx=1;
        gbc.gridy=1;
        themePanel.add(difficultecombobox,gbc);

        gbc.gridx=0;
        gbc.gridy=2;
        themePanel.add(questionlabel,gbc);

        gbc.gridx=1;
        gbc.gridy=2;
        themePanel.add(questionField,gbc);

        gbc.gridx=0;
        gbc.gridy=3;
        themePanel.add(reponse,gbc);
        gbc.gridx=1;
        gbc.gridy=3;
        themePanel.add(reponse1,gbc);
        gbc.insets=new Insets(0,0,0,0);

        gbc.gridx=1;
        gbc.gridy=4;
        themePanel.add(reponse2,gbc);
        gbc.gridx=1;
        gbc.gridy=5;
        themePanel.add(reponse3,gbc);
        gbc.gridx=1;
        gbc.gridy=6;
        themePanel.add(reponse4,gbc);

        gbc.insets=new Insets(10,0,0,0);
        gbc.gridx=0;
        gbc.gridy=8;
        themePanel.add(entierlabel,gbc);
        gbc.gridx=1;
        gbc.gridy=8;
        themePanel.add(bonnereponse,gbc);
        gbc.gridx=0;
        gbc.gridy=9;
        themePanel.add(pointslabel,gbc);
        gbc.gridx=1;
        gbc.gridy=9;
        themePanel.add(pointscombobox,gbc);
        gbc.gridx=0;
        gbc.gridy=10;
        themePanel.add(maluslabel,gbc);
        gbc.gridx=1;
        gbc.gridy=10;
        themePanel.add(maluscombobox,gbc);
        gbc.gridx=0;
        gbc.gridy=11;
        themePanel.add(tempslabel,gbc);
        gbc.gridx=1;
        gbc.gridy=11;
        themePanel.add(tempscombobox,gbc);
        gbc.gridx=0;
        gbc.gridy=12;
        themePanel.add(retour,gbc);
        gbc.gridx=1;
        gbc.gridy=12;
        themePanel.add(valider,gbc);
        gbc.gridx=0;
        gbc.gridy=13;
        gbc.gridwidth=2;
        gbc.fill=GridBagConstraints.CENTER;
        themePanel.add(labelErreur,gbc);

        CONTAINER.add("ajouterquestion",themePanel);

        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String theme=(String) themecombobox.getSelectedItem();
                String difficulte= (String) difficultecombobox.getSelectedItem();
                String enonce = questionField.getText();
                String [] reponses={reponse1.getText(),reponse2.getText(),reponse3.getText(),reponse4.getText()};
                int entier=Integer.parseInt((String) bonnereponse.getSelectedItem());
                int points=Integer.parseInt((String) pointscombobox.getSelectedItem());
                int malus=Integer.parseInt((String) maluscombobox.getSelectedItem());
                int temps=Integer.parseInt((String) tempscombobox.getSelectedItem());
                if(enonce.length()<4||reponses[0].length()<4||reponses[1].length()<4||reponses[2].length()<4||reponses[3].length()<4) {
                    labelErreur.setVisible(true);
                }else{
                    new EspaceAdministrateur().setLabelSucces("Question ajoutée avec succès !",Color.green);
                    CARD.show(CONTAINER,"espaceadmin");
                    Question question = new Question(enonce, reponses, theme, difficulte, temps, points, entier, malus);
                    question.ajouteCSV();
                }
            }
        });

        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CARD.show(CONTAINER,"espaceadmin");
            }
        });
    }
    
}