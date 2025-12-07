package ma.est.gestion.view;

import ma.est.gestion.controller.LivreController;
import ma.est.gestion.model.Livre;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LivrePanel extends JPanel {

    // Champs du formulaire
    private JTextField txtCode, txtTitre, txtAuteur, txtExemplaires;
    private JComboBox<String> comboCategorie;

    // Tableau d'affichage
    private JTable table;
    private DefaultTableModel model;

    // Contrôleur qui gère la logique métier
    private LivreController controller;

    public LivrePanel(LivreController controller) {
        this.controller = controller;         // Injection du contrôleur
        initUI();                             // Création des composants graphiques
        controller.chargerLivres();           // Charge les livres dès l'ouverture du panel
    }

    private void initUI() {
        setLayout(new BorderLayout());        // Layout principal

        //formulaire
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        // Labels du formulaire
        JLabel lblCode = new JLabel("Code (ISBN):");
        JLabel lblTitre = new JLabel("Titre:");
        JLabel lblAuteur = new JLabel("Auteur:");
        JLabel lblExemplaires = new JLabel("Nombre Exemplaires:");
        JLabel lblCat = new JLabel("Catégorie:");

        // Champs de saisie
        txtCode = new JTextField();
        txtTitre = new JTextField();
        txtAuteur = new JTextField();
        txtExemplaires = new JTextField();

        // Liste déroulante des catégories
        comboCategorie = new JComboBox<>(new String[]{
                "Science", "Roman", "Informatique", "Histoire"
        });

        // Ajout des champs au panel du formulaire
        formPanel.add(lblCode); formPanel.add(txtCode);
        formPanel.add(lblTitre); formPanel.add(txtTitre);
        formPanel.add(lblAuteur); formPanel.add(txtAuteur);
        formPanel.add(lblExemplaires); formPanel.add(txtExemplaires);
        formPanel.add(lblCat); formPanel.add(comboCategorie);

        add(formPanel, BorderLayout.NORTH);

        // les boutons
        JPanel buttonPanel = new JPanel();

        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnReset = new JButton("Reset");

        // Ajout des boutons
        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnModifier);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnReset);

        add(buttonPanel, BorderLayout.CENTER);

        // tableau
        model = new DefaultTableModel(
                new String[]{"Code", "Titre", "Auteur", "Exemplaires", "Catégorie"}, 
                0
        );                                        // Modèle des colonnes
        table = new JTable(model);                // JTable pour afficher les livres

        add(new JScrollPane(table), BorderLayout.SOUTH);

        // action des boutons
        btnAjouter.addActionListener(e -> ajouterLivre());      // Ajout
        btnModifier.addActionListener(e -> modifierLivre());    // Modification
        btnSupprimer.addActionListener(e -> supprimerLivre());  // Suppression
        btnReset.addActionListener(e -> resetForm());           // Réinitialisation

        // Sélection dans le tableau → Remplissage du formulaire
        table.getSelectionModel().addListSelectionListener(
                e -> chargerDepuisTable()
        );
    }

    // Affiche la liste des livres reçus depuis le contrôleur
    public void afficherLivres(java.util.List<Livre> livres) {
        model.setRowCount(0);               // Effacer anciennes données

        for (Livre l : livres) {            // Parcours des livres
            model.addRow(new Object[]{
                l.getCode(),
                l.getTitre(),
                l.getAuteur(),
                l.getNombreExemplaire(),
                l.getCategorie().getCategorie()
            });
        }
    }

    // méthodes appellant le controller

    private void ajouterLivre() {
        controller.ajouterLivre(              // Transfert des données
                txtCode.getText(),
                txtTitre.getText(),
                txtAuteur.getText(),
                txtExemplaires.getText(),
                comboCategorie.getSelectedItem().toString()
        );
    }

    private void modifierLivre() {
        controller.modifierLivre(
                txtCode.getText(),
                txtTitre.getText(),
                txtAuteur.getText(),
                txtExemplaires.getText(),
                comboCategorie.getSelectedItem().toString()
        );
    }

    private void supprimerLivre() {
        controller.supprimerLivre(txtCode.getText());
    }

    // Réinitialisation des champs du formulaire
    private void resetForm() {
        txtCode.setText("");
        txtTitre.setText("");
        txtAuteur.setText("");
        txtExemplaires.setText("");
        comboCategorie.setSelectedIndex(0);
    }

    // Charge un livre sélectionné dans le tableau vers le formulaire
    private void chargerDepuisTable() {
        int row = table.getSelectedRow();   // Ligne sélectionnée
        if (row == -1) return;             // Aucun élément sélectionné

        // Remplissage des champs
        txtCode.setText(model.getValueAt(row, 0).toString());
        txtTitre.setText(model.getValueAt(row, 1).toString());
        txtAuteur.setText(model.getValueAt(row, 2).toString());
        txtExemplaires.setText(model.getValueAt(row, 3).toString());
        comboCategorie.setSelectedItem(model.getValueAt(row, 4).toString());
    }
}
