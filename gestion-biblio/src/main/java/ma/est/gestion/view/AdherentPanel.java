package ma.est.gestion.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import ma.est.gestion.model.Adherent;

import java.awt.*;

public class AdherentPanel extends JFrame {
    JTextField txtNumAdherent, txtNomAdherent, txtPrenomAdherent, txtEmailAdherent;
    // Bouttons accessible par le controllleur
    public JButton btnAddAdherent = new JButton("Ajouter");
    public JButton btnUpdateAdherent = new JButton("Modifier");
    public JButton btnDeleteAdherent = new JButton("Supprimer");

    private DefaultTableModel model;
    private JTable table;
    JPanel panel_general, panelAdherentForm, panelAdherentButtons;

    public AdherentPanel() {
        setTitle("Gestion des Adhérents");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialisation des champ de texte
        txtNumAdherent = new JTextField();
        txtNumAdherent.setVisible(false); // Le numéro d'adhérent est généré automatiquement et invisible pour l'utilisateur
        txtNomAdherent = new JTextField();
        txtPrenomAdherent = new JTextField();
        txtEmailAdherent = new JTextField();

        model = new DefaultTableModel();
        table = new JTable(model);

        panelAdherentForm = new JPanel();
        panelAdherentForm.setLayout(new GridLayout(4, 2, 2, 2));

        panelAdherentForm.add(new JLabel("Nom:"));
        panelAdherentForm.add(txtNomAdherent);
        panelAdherentForm.add(new JLabel("Prénom:"));
        panelAdherentForm.add(txtPrenomAdherent);
        panelAdherentForm.add(new JLabel("Email:"));
        panelAdherentForm.add(txtEmailAdherent);

        panelAdherentButtons = new JPanel();
        panelAdherentButtons.add(btnAddAdherent);
        panelAdherentButtons.add(btnUpdateAdherent);
        panelAdherentButtons.add(btnDeleteAdherent);

        panel_general = new JPanel();
        panel_general.setLayout(new BorderLayout());

        panel_general.add(new JScrollPane(table), BorderLayout.CENTER);
        panel_general.add(panelAdherentForm, BorderLayout.NORTH);
        panel_general.add(panelAdherentButtons, BorderLayout.SOUTH);
        this.add(panel_general);

    }

        // ----------- Méthodes exposées au contrôleur --------------
    public String getNomAdherent() {
            return txtNomAdherent.getText();
    }
    public String getPrenomAdherent() {
            return txtPrenomAdherent.getText();
    }
    public String getEmailAdherent() {
            return txtEmailAdherent.getText();
    }

    public void setForm(int id, String nom, String prenom, String email) {
        txtNumAdherent.setText(id + "");
        txtNomAdherent.setText(nom);
        txtPrenomAdherent.setText(prenom);
        txtEmailAdherent.setText(email);
    }

    public JTable getAdherentTable() {
        return table;
    }

    public void refreshAdherentTable(List<Adherent> adherents) {
        // Réinitialiser le modèle de la table
        model.setRowCount(0);

        // Ajouter les adhérents au modèle
        for (Adherent adherent : adherents) {
            model.addRow(new Object[]{
                adherent.getNumAdherent(),
                adherent.getNomAdherent(),
                adherent.getPrenomAdherent(),
                adherent.getEmailAdherent()
            });
        }
    }
    public JButton getBtnAddAdherent() {
        return btnAddAdherent;
    }
    public JButton getBtnUpdateAdherent() {
        return btnUpdateAdherent;
    }
    public JButton getBtnDeleteAdherent() {
        return btnDeleteAdherent;
    }

     public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            AdherentPanel frame = new AdherentPanel();
            frame.setVisible(true);
        });
    }

     public void setForm(int id, String nom, int age) {
     }

}
