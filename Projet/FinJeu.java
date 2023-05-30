import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
public class FinJeu implements Graphique {
    Clip clip;
    /**
     *
     * @param score
     * @throws IOException
     * @throws ClassNotFoundException
     */
    FinJeu(int score) throws IOException, ClassNotFoundException {
        JLabel label=new JLabel("FIN ! Votre score est de "+score);
        JButton rejouer= new JButton("Rejouer");
        JButton menuPrincipal= new JButton("Menu Principal");
        Connexion.compteUtilise.serialiser();
        Partie partie=Connexion.compteUtilise.getHistorique().get(Connexion.compteUtilise.getHistorique().size()-1);
        ajouteCSV(Connexion.compteUtilise.getIdentifiant(),partie.getTheme(), partie.getDifficulte(), partie.getNombrequestions(),partie.getScore());
        menuPrincipal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new MenuPrincipal();
                    clip.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                CARD.show(CONTAINER,"menuprincipal");
            }
        });
        rejouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChoixJeuSolo().lanceJeu(new ArrayList<>(Arrays.asList(partie.getTheme())),partie.getDifficulte(),partie.getNombrequestions());
                clip.close();
            }
        });


        JPanel panel=new JPanel(new GridBagLayout());
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.insets=new Insets(25,0,0,0);
        gbc.gridx=0;
        gbc.gridy=0;
        panel.add(label,gbc);
        gbc.gridx=0;
        gbc.gridy=1;
        panel.add(rejouer,gbc);
        gbc.gridx=0;
        gbc.gridy=2;
        panel.add(menuPrincipal,gbc);
        CONTAINER.add(panel,"FinJeu");
    }

    /**
     *
     * @param utilisateur
     * @param themes
     * @param difficulte
     * @param nombreQuestions
     * @param score
     */
    public void ajouteCSV(String utilisateur, String[] themes,String difficulte,int nombreQuestions, int score){
        try {
            File file= new File("Parties.csv");
            FileWriter writer = new FileWriter(file, true);

            // Ajouter l'en-tête si le fichier est vide
            if (file.length() == 0) {
                writer.append("Utilisateur,Thèmes,Difficulte,Nombre Question,Score \n");
            }

            // Ajouter les objets dans le fichier CSV

            writer.append(utilisateur);
            writer.append(",");

            writer.append(Arrays.toString(themes));
            writer.append(",");
            writer.append(difficulte);
            writer.append(",");
            writer.append(String.valueOf(nombreQuestions));
            writer.append(",");
            writer.append(String.valueOf(score));
            writer.append("\n");

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void joueSon(){
        File file= new File("Sound.wav");
        try {
            AudioInputStream ais= AudioSystem.getAudioInputStream(file);
            clip= AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
}

