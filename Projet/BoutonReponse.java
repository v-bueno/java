import javax.swing.*;
import java.util.ArrayList;
import java.util.Timer;

/**
 *
 */
public class BoutonReponse {

    public static Timer chrono;

    private JButton bouton;

    /**
     * @param chrono    Timer qui gère le temps restant pour répondre
     * @param i         numéro du bouton
     * @param questions liste des questions restantes a jouer.
     */
    public BoutonReponse(Timer chrono, int i, ArrayList<Question> questions) {
        this.chrono = chrono;
        this.bouton = new JButton(questions.get(0).getReponses()[i]);
    }

    /**
     * @return Renvoie le JButton lié à ce BoutonReponse
     */
    public JButton getBouton() {
        return bouton;
    }

    /**
     *
     * @return Renvoie le Timer lié à ce BoutonReponse
     */
    public Timer getChrono() {
        return chrono;
    }
}
