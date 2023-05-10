import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AjouterTexteDansFichier extends JFrame implements ActionListener {

    private JTextField textField;
    JTextField textField2;
    Container c;
    CardLayout card;
    JPanel question;
    JLabel labelquesion;
    JPanel reponse;
    JLabel labelreponse;
    String theme;
    String difficulte;

    public AjouterTexteDansFichier(String theme,String difficulte,Container c, CardLayout card) {
        this.difficulte=difficulte;
        this.theme=theme;
        this.c=c;
        this.card=card;
        /*JFrame frame= new JFrame("Quizz");
        frame.setSize(300,300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        c = frame.getContentPane();
        card=new CardLayout(30,20);
        c.setLayout(card);*/


        

        JPanel question = new JPanel(new GridLayout(0,1));
        JLabel labelquesion= new JLabel("Entrez une nouvelle question");

        textField = new JTextField();
        question.add(labelquesion);
        question.add(textField);
        textField.addActionListener(this);


        reponse = new JPanel(new GridLayout(0,1));
        labelreponse = new JLabel("Entrer la réponse correspondante");
        textField2 = new JTextField();
        reponse.add(labelreponse);
        reponse.add(textField2);
        textField2.addActionListener(this);


        

        c.add("question",question);
        c.add("reponse",reponse);

        
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == textField && !textField.getText().equals("")) {
            String texte = textField.getText();
            try {
                ajouterTexte(texte,"Questions",difficulte);
                card.show(c,"reponse");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            textField.setText("");
        }
        if (e.getSource() == textField2) {
            String texte = textField2.getText();
            try {
                ajouterTexte(texte,"Reponses",difficulte);
                card.show(c,"menuprincipal");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            textField2.setText("");
        }
    }

    public void ajouterTexte(String texte,String question,String difficulte) throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./Dossier/"+question+this.theme+difficulte+".txt", true));
            writer.write(texte);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
