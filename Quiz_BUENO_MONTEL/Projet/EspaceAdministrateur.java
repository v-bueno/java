import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 *
 */
public class EspaceAdministrateur implements Graphique{
    private JLabel labelSucces=new JLabel();

    /**
     *
     */
    EspaceAdministrateur(){
        //Instancie les trois boutons souhaités
        JButton addQuestion=new JButton("Ajouter une question");
        JButton gerer=new JButton("Gérer les comptes");
        JButton menuPrincipal=new JButton("Menu principal");

        //Bouton addQuestion permet de construire puis d'afficher la page pour ajouter une question
        addQuestion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NouvelleQuestion();
                CARD.show(CONTAINER,"ajouterquestion");
                labelSucces.setVisible(false);
            }
        });

        //Bouton gerer permet de construire puis d'afficher la page pour gérer les comptes
        gerer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new GestionCompte();
                    CARD.show(CONTAINER,"gestioncomptes");
                    labelSucces.setVisible(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        //Bouton permet de revenir sur la page du Menu principal
        menuPrincipal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CARD.show(CONTAINER,"menuprincipal");
            }
        });

        //Instancie le JPanel auquel on ajoute les deux boutons
        JPanel panel= new JPanel(new GridBagLayout());
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(0,0,0,5);
        gbc.gridx=0;
        gbc.gridy=0;
        panel.add(gerer,gbc);
        gbc.gridx=1;
        gbc.gridy=0;
        panel.add(addQuestion,gbc);
        gbc.gridx=2;
        gbc.gridy=0;
        panel.add(menuPrincipal,gbc);

        gbc.insets=new Insets(15,0,0,0);
        gbc.fill=GridBagConstraints.CENTER;
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=3;
        panel.add(labelSucces,gbc);


        //Ajoute le panel dans le container afin d'afficher cette page quand on veut
        CONTAINER.add("espaceadmin",panel);
    }

    /**
     *
     */
    public void setLabelSucces(String text,Color color){
        labelSucces.setText(text);
        labelSucces.setForeground(color);
    }
}
