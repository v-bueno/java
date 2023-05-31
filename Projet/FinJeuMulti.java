import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FinJeuMulti implements Graphique{
    private Clip clip;
    private JLabel labelReponse=new JLabel();
    private int scoreA;
    private int scoreB;
    private Partie partie;

    /**
     * Créé une interface qui peut être affiché
     * @param scoreA
     * @param scoreB
     * @param nomEquipeA
     * @param nomEquipeB
     * @param version
     * @param partie
     * @throws IOException
     * @throws ClassNotFoundException
     */
    FinJeuMulti(int scoreA, int scoreB,String nomEquipeA,String nomEquipeB,int version,Partie partie) throws IOException, ClassNotFoundException {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.partie=partie;
        Connexion.menuBar.setVisible(true); //on réaffiche le menu

        //Gère les labels pour afficher l'équipe gagnante et les scores
        JLabel label;
        JLabel labelScoreFinal=new JLabel("Score équipe "+nomEquipeA+" : "+scoreA+" et Score équipe "+nomEquipeB+" : " +scoreB);
        if (scoreA<scoreB) {
            label = new JLabel("FIN ! L'équipe "+nomEquipeB+" l'emporte !");
        }
        else if (scoreA>scoreB) {
            label = new JLabel("FIN ! L'équipe "+nomEquipeA+" l'emporte !");
        }
        else {
            label = new JLabel("FIN ! Les 2 équipes sont ex aequo !");
        }
        //Instancie les deux boutons utiles
        JButton rejouer = new JButton("Rejouer");
        JButton menuPrincipal = new JButton("Menu Principal");
        joueSon();
        //Affiche le menu principal
        menuPrincipal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new MenuPrincipal();
                    clip.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                CARD.show(CONTAINER, "menuprincipal");
            }
        });
        //rejoue avec la même version et les mêmes paramètres via la méthode lanceJeu dans ChoixJeuMulti() selon la version
        rejouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (version == 1) {
                    new ChoixJeuMulti().lanceJeuV1(new ArrayList<>(Arrays.asList(partie.getTheme())), partie.getDifficulte(), partie.getNombrequestions());
                    clip.close();
                }
                else{
                    new ChoixJeuMulti().lanceJeuV2(new ArrayList<>(Arrays.asList(partie.getTheme())), partie.getDifficulte(), partie.getNombrequestions());
                    clip.close();

                }
            }
        });

        //Place les composants dans le panel à l'endroit souhaité
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(25, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelReponse, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(label, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(labelScoreFinal,gbc);
        gbc.gridy = 3;
        panel.add(rejouer, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(menuPrincipal, gbc);
        CONTAINER.add(panel, "FinJeu");
    }


    /**
     * Joue le son de Sound.wav
     */
    public void joueSon(){
        File file= new File("Sound.wav");
        try {
            AudioInputStream ais= AudioSystem.getAudioInputStream(file);
            clip= AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Modifie le label réponse
     * @param texte
     * @param couleur
     */
    public void setLabelReponse(String texte,Color couleur){
        labelReponse.setText(texte);
        labelReponse.setForeground(couleur);
    }

}
