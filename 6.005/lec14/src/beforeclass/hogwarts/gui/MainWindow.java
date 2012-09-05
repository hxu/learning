package beforeclass.hogwarts.gui;

import beforeclass.hogwarts.model.Castle;
import beforeclass.hogwarts.model.Castle.SameNameException;
import beforeclass.hogwarts.model.Wizard;

import java.awt.BorderLayout;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
    
    private final WizardView wizardView;
    
    public MainWindow() {
        setTitle("Hogwarts");
        setLayout(new BorderLayout());
        wizardView = new WizardView();
        add(wizardView, BorderLayout.CENTER);
        pack();        
    }
    
    public void setStartingWizard(Wizard w) {
        wizardView.setWizard(w);
    }
    
    private static Castle makeHogwarts() {
        try {
            Castle hogwarts = new Castle();        
            URL noPicture = MainWindow.class.getResource("images/no-photo.jpg");
            hogwarts.add(new Wizard(hogwarts, "Harry Potter", MainWindow.class.getResource("images/harry.jpg")));
            hogwarts.add(new Wizard(hogwarts, "Hermione Granger", MainWindow.class.getResource("images/hermione.jpg")));
            hogwarts.add(new Wizard(hogwarts, "Ron Weasley", noPicture));
            hogwarts.add(new Wizard(hogwarts, "Albus Dumbledore", noPicture));
            hogwarts.add(new Wizard(hogwarts, "Severus Snape", noPicture));
            hogwarts.lookup("Harry Potter").friend(hogwarts.lookup("Hermione Granger"));
            hogwarts.lookup("Harry Potter").friend(hogwarts.lookup("Ron Weasley"));
            hogwarts.lookup("Hermione Granger").friend(hogwarts.lookup("Ron Weasley"));
            hogwarts.lookup("Harry Potter").friend(hogwarts.lookup("Albus Dumbledore"));
            hogwarts.lookup("Severus Snape").friend(hogwarts.lookup("Albus Dumbledore"));
            return hogwarts;
        } catch (SameNameException e) {
            e.printStackTrace();
            throw new AssertionError("shouldn't happen");
        }
    }
    
    public static void main(String[] args) {
        Castle hogwarts = makeHogwarts();
        MainWindow win = new MainWindow();
        win.setStartingWizard(hogwarts.lookup("Harry Potter"));
        win.setVisible(true);
    }
}
