import javax.swing.*;
import java.util.ArrayList;
import java.util.Timer;

/**
 *
 */
public class BoutonReponse {

    Timer chrono;

    JButton bouton;

    /**
     *
     * @param chrono
     * @param i
     * @param questions
     */
    public BoutonReponse(Timer chrono, int i, ArrayList<Question> questions){
        this.chrono = chrono;
        this.bouton = new JButton(questions.get(0).getReponses()[i]);
    }

    /**
     *
     * @return
     */
    public Timer getChrono() {
        return chrono;
    }

    /**
     *
     * @param chrono
     */
    public void setChrono(Timer chrono) {
        this.chrono = chrono;
    }

    /**
     *
     * @return
     */
    public JButton getBouton() {
        return bouton;
    }

    /**
     *
     * @param bouton
     */
    public void setBouton(JButton bouton) {
        this.bouton = bouton;
    }
}
