import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 *
 */
public class EspaceAdministrateur implements Graphique{
    private JLabel labelCompte;
    private JLabel labelQuestion;

    /**
     *
     */
    EspaceAdministrateur(){
        //Instancie les deux boutons souhaités
        JButton addQuestion=new JButton("Ajouter une question");
        JButton gerer=new JButton("Gérer les comptes");
        labelCompte=new JLabel("Compte modifié avec succès");
        labelQuestion=new JLabel("Question ajoutée avec succès");
        labelCompte.setForeground(Color.green);
        labelQuestion.setForeground(Color.green);
        labelCompte.setVisible(false);
        labelQuestion.setVisible(false);


        //Bouton addQuestion permet de construire puis d'afficher la page pour ajouter une question
        addQuestion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NouvelleQuestion();
                CARD.show(CONTAINER,"ajouterquestion");
                labelCompte.setVisible(false);
                labelQuestion.setVisible(false);
            }
        });

        //Bouton gerer permet de construire puis d'afficher la page pour gérer les comptes
        gerer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new GestionCompte();
                    CARD.show(CONTAINER,"gestioncomptes");
                    labelCompte.setVisible(false);
                    labelQuestion.setVisible(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        //Instancie le JPanel auquel on ajoute les deux boutons
        JPanel panel= new JPanel(new GridBagLayout());
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        panel.add(gerer,gbc);
        gbc.gridx=1;
        gbc.gridy=0;
        panel.add(addQuestion,gbc);
        gbc.insets=new Insets(15,0,0,0);
        gbc.fill=GridBagConstraints.CENTER;
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=2;
        panel.add(labelCompte,gbc);
        panel.add(labelQuestion,gbc);

        //Ajoute le panel dans le container afin d'afficher cette page quand on veut
        CONTAINER.add("espaceadmin",panel);
    }

    /**
     *
     */
    public void visibleLabelCompte(){
        labelCompte.setVisible(true);
    }

    /**
     *
     */
    public void visibleLabelQuestion(){
        labelQuestion.setVisible(true);
    }
}
