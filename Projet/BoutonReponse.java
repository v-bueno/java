import javax.swing.*;
import java.util.ArrayList;
import java.util.Timer;

public class BoutonReponse {

    Timer chrono;

    JButton bouton;

    public BoutonReponse(Timer chrono, int i, ArrayList<Question> questions){
        this.chrono = chrono;
        this.bouton = new JButton(questions.get(0).getReponses()[i]);
    }

    public Timer getChrono() {
        return chrono;
    }

    public void setChrono(Timer chrono) {
        this.chrono = chrono;
    }

    public JButton getBouton() {
        return bouton;
    }

    public void setBouton(JButton bouton) {
        this.bouton = bouton;
    }
}
