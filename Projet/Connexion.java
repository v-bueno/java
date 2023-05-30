import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class Connexion implements Graphique{

    public static Compte compteUtilise;
    private JLabel labelErreur=new JLabel();
    /**
     *
     * @throws IOException
     */
    Connexion() throws IOException {

        //On définie les paramètres de la fenêtre
        FRAME.setSize(600,600);
        FRAME.setPreferredSize(new Dimension(600,600));
        FRAME.setVisible(true);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.setLocationRelativeTo(null);

        //On crée un menu que nous allons ajouter à la fenêtre
        JMenu partieSolo=new JMenu("Partie Solo");
        JMenu partieDuo=new JMenu("Partie Duo");
        JMenu aide=new JMenu("Aide");
        JMenuItem jouerSolo=new JMenuItem("Jouer");
        JMenuItem jouerDuo=new JMenuItem("Jouer");
        JMenuItem menuprincipal=new JMenuItem("Menu principal");
        JMenuItem deconnexion=new JMenuItem("Se déconnecter");
        JMenuBar menuBar=new JMenuBar();
        menuBar.add(partieSolo);
        menuBar.add(partieDuo);
        menuBar.add(aide);
        partieSolo.add(jouerSolo);
        partieDuo.add(jouerDuo);
        aide.add(menuprincipal);
        aide.add(deconnexion);
        FRAME.setJMenuBar(menuBar);
        menuBar.setVisible(false); // Ce menu est invisible tant que l'utilisateur n'est pas connecté

        jouerSolo.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                new ChoixJeuSolo();
                CARD.show(CONTAINER,"choixjeu"); //Affiche la page "choixjeu" créée grâce au constructeur
            }
        });

        jouerDuo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChoixJeuMulti();
                CARD.show(CONTAINER,"choixjeumulti"); //Affiche la page "choixjeumulti" créée grâce au constructeur
            }
        });

        //Le JMenuItem menuprincipal permet de revenir au menu principal
        menuprincipal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CARD.show(CONTAINER,"menuprincipal");
            }
        });

        //Le JMenuItem deconnexion permet de se déconnecter
        deconnexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CARD.show(CONTAINER,"connexion"); //Affiche la page "connexion" enregistrée
                menuBar.setVisible(false); //La menuBar est invisible car on se déconnecte
            }
        });

        CONTAINER.setLayout(CARD); //On applique le cardlayout définie dans l'interface pour ce container

        //On instancie tous les composants que nous allons mettre dans cet affichage
        JLabel identifiantlabel= new JLabel("Identifiant");
        JPasswordField passwordField= new JPasswordField();
        Dimension dimension=new Dimension(120,20);
        passwordField.setPreferredSize(dimension); //On set la dimension souhaitée
        JTextField identifiant=new JTextField();
        identifiant.setPreferredSize(dimension); //On set la dimension souhaitée

        JLabel passwordlabel= new JLabel("Mot de passe");
        JButton connecter=new JButton("Se connecter");
        JButton creercompte= new JButton("Créer un compte");

        JPanel panel= new JPanel(new GridBagLayout()); //On instancie un JPanel afin d'y mettre les composants
        GridBagConstraints gbc = new GridBagConstraints(); // On définie les contraintes du GridBagLayout pour le JPanel
        //On ajoute ainsi tous les composants à l'endroit souhaité dans le JPanel
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.insets=new Insets(5,0,0,0);
        panel.add(identifiantlabel,gbc);
        gbc.gridx=0;
        gbc.gridy=1;
        panel.add(identifiant,gbc);
        gbc.gridx=0;
        gbc.gridy=2;
        panel.add(passwordlabel,gbc);
        gbc.gridx=0;
        gbc.gridy=3;
        panel.add(passwordField,gbc);
        gbc.gridx=0;
        gbc.gridy=4;
        panel.add(connecter,gbc);
        gbc.gridx=0;
        gbc.gridy=5;
        panel.add(creercompte,gbc);
        gbc.gridx=0;
        gbc.gridy=7;
        panel.add(labelErreur,gbc);


        //On ajoute le JPanel en tant que page "connexion" dans le container afin de la réafficher par la suite
        CONTAINER.add(panel,"connexion");

        //On ajoute un événement pour le bouton créercompte
        creercompte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new CreerCompte(); //Appelle le constructeur de la classe qui s'occuppe de l'affichage de la page qui crée un nouveau compte
                    labelErreur.setVisible(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                CARD.show(CONTAINER,"creercompte"); //Affiche la page "creercompte" créée grâce à CreerCompte()
            }
        });

        //On ajoute un événement pour bouton connecter
        connecter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String password="";

                    for (int i=0;i<passwordField.getPassword().length;i++){
                        password=password+passwordField.getPassword()[i];
                    } // récupère le mot de passe rentré dans la variable password
                    Compte compte=new Compte(identifiant.getText(),password); //Instancie un compte avec l'identifiant et le mot de passe rentrés
                    Compte test = compte.verifieIdentifiantMotdepasse(); //On appelle la méthode (expliqué dans la classe Comtpe)
                    if (test!=null){ //Si un compte sérialisé a le même identifiant et le même mot de passe
                        if(!test.getSuspendu()) {
                            compteUtilise = test; // Ce Compte devient le compte utilise
                            new MenuPrincipal(); // Appelle ce constructeur afin de gérer l'affichage du menu principal
                            CARD.show(CONTAINER, "menuprincipal"); //affiche la page du menu principal
                            menuBar.setVisible(true); //affiche la barre de menu car le joueur est connecté
                            //Les composants pouvant avoir été changé par rapport à l'affichage de base sont remis à zéro
                            labelErreur.setVisible(false);
                            identifiant.setText("");
                            passwordField.setText("");
                        }else{
                            setLabelErreur("Compte suspendu",Color.red);
                        }
                    }else{ //Aucun compte sérialisé ne correspond
                        setLabelErreur("Identifiant ou mot de passe incorrect",Color.red);

                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void setLabelErreur(String text,Color color){
        labelErreur.setText(text);
        labelErreur.setForeground(color);
    }

    /**
     *
     * @param args entrée données par l'utilisateur.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        new Connexion();
    }
}