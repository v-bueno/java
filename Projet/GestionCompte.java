import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

/**
 *
 */
public class GestionCompte implements Graphique{
    JLabel labelErreur=new JLabel();
    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    GestionCompte() throws IOException, ClassNotFoundException {

        //Instancie les 3 boutons de la page
        JButton supprimer=new JButton("Supprimer le compte");
        JButton passeradmin=new JButton("Passer le compte en admin");
        JButton suspendre=new JButton("Suspendre le compte");


        //On instancie la JTable qui contient les informations de tous les comptes
        String[] colonnes= {"Identifiant","Mot de passe","Rôle","Suspendu"};
        DefaultTableModel model= new DefaultTableModel(colonnes,0);
        FileInputStream fis = new FileInputStream("Comptes.data");
        ObjectInputStream ois = new ObjectInputStream(fis);
        //On récupère la hashmap sérialisée contenant tous les comptes
        HashMap<String,Compte> hashMap = (HashMap<String,Compte>) ois.readObject();
        Compte compte;
        for (String key : hashMap.keySet()){ //On parcourt toute la hashmap
            //Pour chaque compte dans la hashmap on ajoute une ligne de tous ces attributs à la JTable
            compte = hashMap.get(key);
            model.addRow(new String[]{compte.getIdentifiant(), compte.getMotdepasse(),compte.getType(),compte.getSuspendu().toString()});
        }
        ois.close();
        JTable table = new JTable(model){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêche l'édition des cellules
            }
        };
        JScrollPane scroll = new JScrollPane(table);

        //Instancie un JLabel qui sera le "titre de la page"
        JLabel labelHead=new JLabel("Comptes enregistrés",SwingConstants.CENTER);
        Font customFont=new Font("Serif",Font.BOLD,20);
        labelHead.setFont(customFont);
        labelHead.setForeground(Color.BLUE);

        //On place les composants dans un JPanel à l'aide de GridBagLayout
        JPanel panel=new JPanel(new GridBagLayout());
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.gridx=1;
        gbc.gridy=0;
        panel.add(labelHead,gbc);
        gbc.fill=GridBagConstraints.BOTH;
        gbc.insets=new Insets(20,0,0,0);
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.weightx=1;
        gbc.weighty=1;
        gbc.gridheight=2;
        gbc.gridwidth=3;
        panel.add(scroll,gbc);
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.weightx=0;
        gbc.weighty=0;
        gbc.gridwidth=1;
        gbc.gridx=0;
        gbc.gridy=3;
        panel.add(supprimer,gbc);
        gbc.gridx=1;
        gbc.gridy=3;
        panel.add(passeradmin,gbc);
        gbc.gridx=2;
        gbc.gridy=3;
        panel.add(suspendre,gbc);
        gbc.gridwidth=3;
        gbc.gridx=0;
        gbc.gridy=5;
        panel.add(labelErreur,gbc);

        //On ajoute cette page au CONTAINER
        CONTAINER.add(panel,"gestioncomptes");

        //Compte sélectionné dans la JTable devient administrateur
        passeradmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRowCount() != 1) { //s'il y a aucune ou plusieurs lignes sélectionnées
                    setLabelErreur("Vous ne pouvez modifier qu'un compte à la fois",Color.red);//affiche erreur
                } else {
                    int ligne = table.getSelectedRow();//ligne sélectionnée dans la JTable
                    //Si le compte n'est pas admin
                    if (!table.getValueAt(ligne, 2).toString().equals("Administrateur")) {
                        String id = table.getValueAt(ligne, 0).toString();
                        String mdp = table.getValueAt(ligne, 1).toString();
                        //On instancie un compte avec l'identifiant et le mot de passe sélectionnés
                        Compte compte = new Compte(id, mdp);
                        try {
                            //Récupère le compte correspondant sérialisé
                            Compte correspondant = compte.verifieIdentifiantMotdepasse();
                            correspondant.setType("Administrateur"); //On le passe admin
                            correspondant.serialiser();
                            //Sérialise avec modification et retourne sur la page admin avec un message de succès
                            new EspaceAdministrateur().setLabelSucces("Compte modifié avec succès !",Color.green);
                            CARD.show(CONTAINER,"espaceadmin");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else { //Sinon le compte est un admin, on ne peut pas le modifier et on affiche un message d'erreur
                        setLabelErreur("Vous ne pouvez pas modifier le compte d'un admin",Color.red);
                    }
                }
            }
        });

        /*Supprime le compte sélectionner le compte fonctionne exactement de la même façon que celui d'avant
         sauf que c'est un appel à une méthode de la classe compte différent*/
        supprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRowCount() != 1) {
                    setLabelErreur("Vous ne pouvez modifier qu'un compte à la fois",Color.red);
                } else {
                    int ligne = table.getSelectedRow();
                    if (!table.getValueAt(ligne, 2).toString().equals("Administrateur")) {
                        String id = table.getValueAt(ligne, 0).toString();
                        Compte compte = new Compte(id);
                        try {
                            compte.supprimer();
                            new EspaceAdministrateur().setLabelSucces("Compte modifié avec succès !",Color.green);
                            CARD.show(CONTAINER,"espaceadmin");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        setLabelErreur("Vous ne pouvez pas modifier le compte d'un admin",Color.red);
                    }
                }
            }
        });

        /*Suspend le compte sélectionner le compte fonctionne exactement de la même façon que celui d'avant
         sauf que c'est un appel à une méthode de la classe compte différent*/
        suspendre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                {
                    if (table.getSelectedRowCount() != 1) {
                        setLabelErreur("Vous ne pouvez modifier qu'un compte à la fois",Color.red);
                    } else {
                        int ligne = table.getSelectedRow();
                        if (!table.getValueAt(ligne, 2).toString().equals("Administrateur")) {
                            String id = table.getValueAt(ligne, 0).toString();
                            Compte compte = new Compte(id);
                            try {
                                compte.suspendre();
                                new EspaceAdministrateur().setLabelSucces("Compte modifié avec succès !",Color.green);;
                                CARD.show(CONTAINER,"espaceadmin");
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            } catch (ClassNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else {
                            setLabelErreur("Vous ne pouvez pas modifier le compte d'un admin",Color.red);
                        }
                    }
                }
            }
        });
    }
    //Modifie le labelErreur
    public void setLabelErreur(String text,Color color){
        labelErreur.setText(text);
        labelErreur.setForeground(color);
    }
}
