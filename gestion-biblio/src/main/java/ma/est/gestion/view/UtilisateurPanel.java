package ma.est.gestion.view;

import java.awt.*;
import javax.swing.*;

import ma.est.gestion.controller.LivreController;
import ma.est.gestion.dao.LivreDao;

public class UtilisateurPanel extends JFrame {

    private LivrePanel livrePanel;

    public UtilisateurPanel() {

        setTitle("Espace Adhérent");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(46, 204, 113));
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel title = new JLabel("Espace Adhérent - Livres disponibles");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);

        JButton btnEmprunter = new JButton("Emprunter");
        JButton btnRetour = new JButton("Retour");

        styliserBouton(btnEmprunter, new Color(52, 152, 219));
        styliserBouton(btnRetour, new Color(149, 165, 166));

        actions.add(btnEmprunter);
        actions.add(btnRetour);

        header.add(title, BorderLayout.WEST);
        header.add(actions, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // LIVRES
        livrePanel = new LivrePanel();

        // Masquons les fonctionnalités admin
        livrePanel.getBtnAjouter().setVisible(false);
        livrePanel.getBtnSupp().setVisible(false);
        livrePanel.getBtnGereEmp().setVisible(false);
        livrePanel.getBtnGereAdh().setVisible(false);
        livrePanel.getBtnValiderForm().setVisible(false);
        livrePanel.getBtnRetour().setVisible(false);
        livrePanel.getCbCategorie().setVisible(false);
        livrePanel.getBtnSelectCat().setVisible(false);

        // Chargement des livres depuis la base
        new LivreController(livrePanel, new LivreDao());

        add(livrePanel.getContentPane(), BorderLayout.CENTER);

        //ACTIONS

        btnEmprunter.addActionListener(e -> emprunterLivre());

        btnRetour.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }

    //STYLE
    private void styliserBouton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }

    //MÉTHODE EMPRUNTER 
    private void emprunterLivre() {

        int[] rows = livrePanel.getTable().getSelectedRows();

        if (rows.length == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Veuillez sélectionner au moins un livre",
                    "Aucun livre sélectionné",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        StringBuilder livres = new StringBuilder("Livres sélectionnés :\n\n");

        for (int row : rows) {
            Object titre = livrePanel.getTable().getValueAt(row, 2);
            livres.append("• ").append(titre).append("\n");
        }

        JOptionPane.showMessageDialog(
                this,
                livres + "\nRedirection vers l'interface d'emprunt",
                "Emprunt",
                JOptionPane.INFORMATION_MESSAGE
        );

        /*
         * dispose();
         * new EmpruntPanel(livresSelectionnes).setVisible(true);
         * 
         */
    }
}
