package ma.est.gestion.view;

import java.awt.*;
import javax.swing.*;

import ma.est.gestion.controller.LivreController;
import ma.est.gestion.dao.LivreDao;

public class MainFrame extends JFrame {

    private JPanel contentPanel;

    public MainFrame() {

        setTitle("Gestion Bibliothèque - Admin");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // MENU BAR 
        JMenuBar menuBar = new JMenuBar();

        JMenu menuGestion = new JMenu("Gestion");
        JMenuItem itemLivres = new JMenuItem("Livres");
        JMenuItem itemAdherents = new JMenuItem("Adhérents");
        JMenuItem itemQuitter = new JMenuItem("Quitter");

        menuGestion.add(itemLivres);
        menuGestion.add(itemAdherents);
        menuGestion.addSeparator();
        menuGestion.add(itemQuitter);

        menuBar.add(menuGestion);
        setJMenuBar(menuBar);

        //PANEL CENTRAL
        contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        //ACTIONS MENU
        itemLivres.addActionListener(e -> afficherLivres());
        itemAdherents.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Interface adhérent"));

        itemQuitter.addActionListener(e -> System.exit(0));

        //AFFICHAGE PAR DÉFAUT
        afficherLivres();
    }

    private void afficherLivres() {

        contentPanel.removeAll();

        LivrePanel livrePanel = new LivrePanel();
        LivreDao dao = new LivreDao();
        new LivreController(livrePanel, dao);

        contentPanel.add(livrePanel.getContentPane(),
                BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }
}

