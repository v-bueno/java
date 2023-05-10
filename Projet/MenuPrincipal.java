import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MenuPrincipal {

    MenuPrincipal(){
        JFrame frame= new JFrame("Quizz");
        frame.setSize(300,300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenu partieSolo=new JMenu("Partie Solo");
        JMenu partieDuo=new JMenu("Partie Duo");
        JMenu aide=new JMenu("Aide");
        JMenuBar menuBar=new JMenuBar();
        menuBar.add(partieSolo);
        menuBar.add(partieDuo);
        menuBar.add(aide);
        frame.setJMenuBar(menuBar);

        Container c = frame.getContentPane();
        CardLayout card=new CardLayout(30,20);
        c.setLayout(card);
        JButton jeuSolo = new JButton("Jeu solo");
        JButton jeuDuo = new JButton("Jeu duo");
        JButton addQuestion = new JButton("Ajouter une question");
        addQuestion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new ChoixTheme(c,card);
                card.show(c, "list");
            }
        });
        JPanel menuprincipal=new JPanel();
        menuprincipal.add(jeuSolo);
        menuprincipal.add(jeuDuo);
        menuprincipal.add(addQuestion);
        c.add(menuprincipal, "menuprincipal");
    }

    public static void main(String[] args) {
        new MenuPrincipal();
        }
}
