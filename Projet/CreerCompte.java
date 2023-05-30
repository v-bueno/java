import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class CreerCompte implements Serializable, Graphique {

    String identifiant;
    String motdepasse;

    /**
     *
     * @throws IOException
     */
    CreerCompte() throws IOException {

        //On instancie tous les composants
        JLabel identifiantlabel = new JLabel("Identifiant");
        JPasswordField passwordField = new JPasswordField();
        JTextField identifiantField = new JTextField();
        JLabel passwordlabel = new JLabel("Mot de passe");
        JButton btn = new JButton("Créer le compte");
        JButton annuler=new JButton("Annuler");
        JLabel labelErreur = new JLabel();
        labelErreur.setForeground(Color.red);


        //On instancie un JPanel afin d'y ranger tous les composants
        JPanel panel = new JPanel(new GridBagLayout());

        //On ajoute les composants à l'endroit voulu grâce au GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.insets=new Insets(5,0,0,0);
        panel.add(identifiantlabel,gbc);
        gbc.gridx=0;
        gbc.gridy=1;
        panel.add(identifiantField,gbc);
        gbc.gridx=0;
        gbc.gridy=2;
        panel.add(passwordlabel,gbc);
        gbc.gridx=0;
        gbc.gridy=3;
        panel.add(passwordField,gbc);
        gbc.gridx=0;
        gbc.gridy=4;
        panel.add(btn,gbc);
        gbc.gridx=0;
        gbc.gridy=5;
        panel.add(annuler,gbc);
        gbc.gridx=0;
        gbc.gridy=6;
        panel.add(labelErreur,gbc);


        CONTAINER.add(panel, "creercompte"); //Ajoute la mise en page du panel dans le container

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    File file=new File("Comptes.data"); //fichier contenant les comptes serialisés
                    identifiant = identifiantField.getText();
                    String password = "";
                    for (int i = 0; i < passwordField.getPassword().length; i++) {
                        password = password + passwordField.getPassword()[i];
                    }//On recupère l'identifiant et le mot de passe rentrés
                    Compte compte = new Compte(identifiant, password, "Joueur",false);//Instancie un nouveau compte joueur avec ces deux attributs
                    int length = passwordField.getPassword().length; //longueur du mot de passe
                    if(identifiant.length()<=3||length<=3) {//identifiant et mot de passe ont plus de 3 caractères
                        //Affiche le message d'erreur associé
                        labelErreur.setText("Identifiant et mot de passe doivent faire plus de 3 caractères");
                    } else if (!file.exists()) { //si le fichier n'existe pas
                        compte.setType("Administrateur");
                        compte.serialiser(); //on sérialise le compte en tant qu'administrateur (car c'est le premier compte)
                        CARD.show(CONTAINER, "connexion");//affiche la page de connexion
                    } else if(compte.verifieIdentifiant()){//identifiant existe déjà pour un autre compte
                        //Affiche le message d'erreur associé
                        labelErreur.setText("Identifiant déjà existant");
                    }
                    else{
                        compte.serialiser(); // sérialise le compte
                        CARD.show(CONTAINER, "connexion"); //Affiche la page de connexion
                        //Enlève les messages d'erreurs
                        labelErreur.setVisible(false);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        //Le bouton annuler permet de revenir sur la page de connexion sans créer de compte
        annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CARD.show(CONTAINER,"connexion");
            }
        });
    }
}
