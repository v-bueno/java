import com.opencsv.CSVReader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MenuPrincipal implements Graphique{

    MenuPrincipal() throws IOException {

        //Instancie les composants
        JButton jeuSolo = new JButton("Jeu solo");
        JButton jeuDuo = new JButton("Jeu duo");
        JButton espaceAdministrateur = new JButton("Espace Admin");
        JLabel label= new JLabel("Historique de vos parties");
        JLabel label10Derniere=new JLabel("10 dernières parties");
        JButton rejouer=new JButton("Rejouer avec les mêmes paramètres");
        JLabel labelErreur=new JLabel("Sélectionner une seule partie");
        labelErreur.setForeground(Color.red);
        labelErreur.setVisible(false);


        ArrayList<Partie> historique=Connexion.compteUtilise.getHistorique();
        Collections.reverse(historique);

        String[] colonnes= {"Mode","Difficulté","Thèmes","Score","Nombre"};
        DefaultTableModel model= new DefaultTableModel(colonnes,0);
        Iterator<Partie> iterator = historique.iterator();
        while (iterator.hasNext()) {
            Partie partie = iterator.next();

            model.addRow(new String[]{partie.getMode(), partie.getDifficulte(), Arrays.toString(partie.getTheme()), Integer.toString(partie.getScore()),Integer.toString(partie.getNombrequestions())});
        }
        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêche l'édition des cellules
            }
        };
        JScrollPane scrollPane=new JScrollPane(table);

        String[] colonnes2= {"Utilisateur","Thèmes","Difficulté","Nombre","Score"};
        DefaultTableModel model2= new DefaultTableModel(colonnes2,0);
        FileReader file= new FileReader("Parties.csv");
        CSVReader reader= new CSVReader(file);
        List<String[]> lines = reader.readAll();
        lines.remove(0);
        Collections.reverse(lines);
        Iterator<String[]> iterator2= lines.iterator();
        for(int i=0;i<10;i++) {
            if(iterator2.hasNext()){
                String[] line=iterator2.next();
                model2.addRow(new String[]{line[0],line[1],line[2],line[3],line[4]});
            }
        }
        JTable table2=new JTable(model2);
        table2.setEnabled(false);
        JScrollPane scrollPane2=new JScrollPane(table2);

        /* Si le compte connecté n'est pas administrateur le bouton permettant
        d'accéder à l'espace administrateur n'est pas affiché */
        if(Connexion.compteUtilise.getType().equals("Joueur")){
            espaceAdministrateur.setVisible(false);
        }

        jeuSolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelErreur.setVisible(false);
                new ChoixJeuSolo();
                CARD.show(CONTAINER,"choixjeu");
            }
        });


        espaceAdministrateur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                labelErreur.setVisible(false);
                new EspaceAdministrateur();
                CARD.show(CONTAINER, "espaceadmin");
            }
        });

        //Instancie un JPanel auquel on ajoute les composants
        JPanel menuprincipal=new JPanel(new GridBagLayout());
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.gridx=0;
        gbc.gridy=0;
        menuprincipal.add(jeuSolo,gbc);

        gbc.gridx=1;
        gbc.gridy=0;
        menuprincipal.add(jeuDuo,gbc);

        gbc.gridx=2;
        gbc.gridy=0;
        menuprincipal.add(espaceAdministrateur,gbc);

        gbc.gridx=3;
        gbc.gridy=0;
        menuprincipal.add(new JButton("Déconnexion"),gbc);

        gbc.insets=new Insets(15,0,0,0);

        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=2;
        gbc.fill=GridBagConstraints.CENTER;
        menuprincipal.add(label,gbc);

        gbc.gridx=2;
        gbc.gridy=1;

        menuprincipal.add(label10Derniere,gbc);
        gbc.insets=new Insets(5,0,0,10);


        gbc.fill=GridBagConstraints.BOTH;
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.weightx=1;
        gbc.weighty=1;
        gbc.gridheight=2;
        gbc.gridwidth=2;
        menuprincipal.add(scrollPane,gbc);

        gbc.insets=new Insets(5,10,0,0);

        gbc.gridx=2;
        gbc.gridy=2;
        menuprincipal.add(scrollPane2,gbc);

        gbc.insets=new Insets(0,0,0,0);

        gbc.gridheight=1;
        gbc.weightx=0;
        gbc.weighty=0;
        gbc.gridx=0;
        gbc.gridy=4;
        menuprincipal.add(rejouer,gbc);
        gbc.fill=GridBagConstraints.CENTER;
        gbc.gridx=2;
        gbc.gridy=4;
        menuprincipal.add(labelErreur,gbc);







        //Ajoute le panel au container afin d'afficher cette page quand on le souhaite
        CONTAINER.add(menuprincipal, "menuprincipal");

        rejouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRowCount()!=1){
                    labelErreur.setVisible(true);
                }
                else {
                    int ligne = table.getSelectedRow();
                    String difficulte = table.getValueAt(ligne, 1).toString();
                    String theme = table.getValueAt(ligne, 2).toString();
                    String listeTheme = theme.substring(1, theme.length() - 1);
                    String[] elements = listeTheme.split(", ");
                    List<String> liste = Arrays.asList(elements);
                    ArrayList<String> arrayList = new ArrayList<>(liste);
                    int nombreQuestions = Integer.parseInt(table.getValueAt(ligne, 4).toString());
                    new ChoixJeuSolo().lanceJeu(arrayList, difficulte, nombreQuestions);
                    labelErreur.setVisible(false);

                }
            }
        });
    }


}
