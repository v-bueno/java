import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
//import javax.smartcardio.Card;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
public class ChoixTheme extends JFrame{
    String themes []= new String[10];
    String difficultes[]={"Facile","Normal","Difficile"};
    String theme;
    ChoixTheme(Container c, CardLayout card){
        try {
            BufferedReader lecteur = new BufferedReader(new FileReader("theme.txt"));
            String ligne;
            int i=0;
            while ((ligne = lecteur.readLine()) != null) {
                themes[i]=ligne;
                i=i+1;
            }

            lecteur.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JList <String> list=new JList <String> (themes);
        JPanel  themePanel = new JPanel(new GridLayout(1,1));
        JLabel labeltheme= new JLabel("Nouveau thème : ");

        JTextField textField = new JTextField();
        themePanel.add(list);
        themePanel.add(labeltheme);
        themePanel.add(textField);
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("theme.txt", true))) {
                    writer.write(textField.getText());
                    writer.newLine();
                    writer.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                theme=textField.getText();
                card.show(c,"difficulte");
            }
        });

        c.add("list",themePanel);
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && themes[list.getSelectedIndex()]!=null) {
                    theme=themes[list.getSelectedIndex()];
                    card.show(c,"difficulte");
                    
                }
            }
        });

        


        JList <String> difficult=new JList <String> (difficultes);
        c.add("difficulte",difficult);
        difficult.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()  && difficultes[difficult.getSelectedIndex()]!=null) {
                    new AjouterTexteDansFichier(theme,difficultes[difficult.getSelectedIndex()],c,card);
                    card.show(c,"question");
                    
                }
            }
        });
    }
    
}