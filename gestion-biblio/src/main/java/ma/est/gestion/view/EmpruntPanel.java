package ma.est.gestion.view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ma.est.gestion.model.Emprunt;

public class EmpruntPanel extends JFrame {
    
    private final DefaultTableModel tableModelEmp;
    private final JTable tableEmp;
    private final JButton buttonModifierEmp;
    private final JButton buttonSupprimerEmp;
    private final JButton buttonRechercherEmp;
    private final JLabel label4Emp;

    public EmpruntPanel(){

        super("Gestion des emprunts");

        JPanel panEmprunt = new JPanel();
        panEmprunt.setLayout(new BorderLayout());

        JPanel panel1Emp;
        panel1Emp = new JPanel();
            panel1Emp.setBorder(
            BorderFactory.createTitledBorder("Recherche d’ Emprunt")
        );

        panel1Emp.setLayout(new FlowLayout( FlowLayout.LEFT, 10, 10));

        JTextField textFieldEmp = new JTextField(35);
        Dimension textSizeEmp = new Dimension(200, 30);
        textFieldEmp.setPreferredSize(textSizeEmp);
        textFieldEmp.setMinimumSize(textSizeEmp);
        textFieldEmp.setMaximumSize(textSizeEmp);

        buttonRechercherEmp = new JButton("Rechercher");

        panel1Emp.add(textFieldEmp);
        panel1Emp.add(buttonRechercherEmp);

        JPanel panel2Emp = new JPanel();
            panel2Emp.setBorder(BorderFactory.createTitledBorder("Liste des Emprunts :"));
            tableModelEmp = new DefaultTableModel(
                    new Object[]{"Code Emprunt", "Num Adherent", "Date Emprunt", "Date Retour", "Status", "Code Livre"}, 0
            );
        tableEmp = new JTable(tableModelEmp);
        label4Emp = new JLabel("Total Emprunts: 0");
        panel2Emp.setLayout(new BoxLayout(panel2Emp, BoxLayout.Y_AXIS));
        panel2Emp.add(new JScrollPane(tableEmp));
        panel2Emp.add(label4Emp);

        JPanel panel3Emp = new JPanel();
            panel3Emp.setBorder(
                BorderFactory.createTitledBorder("Gestion des Emprunts")
            );

        panel3Emp.setLayout( new BoxLayout(panel3Emp, BoxLayout.Y_AXIS));

        Dimension panelSizeEmp = new Dimension(300, 200);
        panel3Emp.setPreferredSize(panelSizeEmp);
        panel3Emp.setMinimumSize(panelSizeEmp);
        panel3Emp.setMaximumSize(panelSizeEmp);

        buttonModifierEmp = new JButton("Modifier Emprunt ");
        buttonSupprimerEmp = new JButton("Supprimer Emprunt ");
        JLabel labEmp = new JLabel("Colonne modifiable : ");
        JLabel modEmp = new JLabel("Date Retour et Status");

        buttonModifierEmp.setAlignmentX(CENTER_ALIGNMENT);
        buttonSupprimerEmp.setAlignmentX(CENTER_ALIGNMENT);
        labEmp.setAlignmentX(CENTER_ALIGNMENT);
        modEmp.setAlignmentX(CENTER_ALIGNMENT);


        Dimension buttonSizeEmp = new Dimension(195, 30);
        
        buttonModifierEmp.setPreferredSize(buttonSizeEmp);
        buttonModifierEmp.setMinimumSize(buttonSizeEmp);
        buttonModifierEmp.setMaximumSize(buttonSizeEmp);
        
        buttonSupprimerEmp.setPreferredSize(buttonSizeEmp);
        buttonSupprimerEmp.setMinimumSize(buttonSizeEmp);
        buttonSupprimerEmp.setMaximumSize(buttonSizeEmp);

        buttonRechercherEmp.setPreferredSize(buttonSizeEmp);
        buttonRechercherEmp.setMinimumSize(buttonSizeEmp);
        buttonRechercherEmp.setMaximumSize(buttonSizeEmp);


        panel3Emp.add(Box.createVerticalStrut(30));
        panel3Emp.add(buttonModifierEmp);
        panel3Emp.add(Box.createVerticalStrut(20));
        panel3Emp.add(buttonSupprimerEmp);
        
        panel3Emp.add(Box.createVerticalStrut(50));
        panel3Emp.add(labEmp);
        panel3Emp.add(Box.createVerticalStrut(20));
        panel3Emp.add(modEmp);


        /*
            Fin la partie Emprunt
        */


        JButton[] buttons = {buttonModifierEmp, buttonRechercherEmp };

        for (JButton b : buttons) {
            styliserBouton(b, new Color(46, 204, 113));
        }

        styliserBouton(buttonSupprimerEmp, new Color(231, 76, 60));

        panEmprunt.add(panel1Emp, BorderLayout.NORTH);
        panEmprunt.add(panel2Emp, BorderLayout.CENTER);
        panEmprunt.add(panel3Emp, BorderLayout.EAST);



        add(panEmprunt);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 500));
        pack();
        setLocationRelativeTo(null);
    }

    private void styliserBouton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
    }

    public void refreshTable(List<Emprunt> liste) {
        if (tableModelEmp == null) return;
        tableModelEmp.setRowCount(0);
        for (Emprunt e : liste) {
            tableModelEmp.addRow(new Object[]{e.getCodeEmprunt(), e.getNumAdherent(), e.getDateEmprunt(), e.getDateRetour(), e.getStatut(), e.getCodeLivre()});
            
        }
    }

    // Getters used by controller
    public JTable getTable() {
        return tableEmp;
    }

    public JButton getModifier() {
        return buttonModifierEmp;
    }

    public JButton getSupprimer() {
        return buttonSupprimerEmp;
    }

    public JButton getRechercher() {
        return buttonRechercherEmp;
    }

    public JLabel getLabel4Emp() {
        return label4Emp;
    }
}
