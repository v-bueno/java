import javax.swing.*;

public class Statistiques implements Graphique {

    /**
     * Affiche un JOptionPane pour les statistiques
     */
    Statistiques(){
        JOptionPane.showMessageDialog(FRAME,"Voici vos statistiques :\nQuestions totales : "
                +Connexion.compteUtilise.getNombreQuestions()+"\nQuestions répondues juste : "
                +Connexion.compteUtilise.getNombreReponses()+"\nNombre de parties solo jouées : "
                +Connexion.compteUtilise.getNombreParties());
    }
}
