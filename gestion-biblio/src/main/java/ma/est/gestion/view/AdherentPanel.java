package ma.est.gestion.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.util.List;

import ma.est.gestion.dao.AdherentDao;
import ma.est.gestion.model.Adherent;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdherentPanel extends JFrame {

    JTextField txtNumAdherent, txtNomAdherent, txtPrenomAdherent, txtEmailAdherent;

    public JButton btnAddAdherent = new JButton("Ajouter");
    public JButton btnUpdateAdherent = new JButton("Modifier");
    public JButton btnDeleteAdherent = new JButton("Supprimer");

    private DefaultTableModel model;
    private JTable table;

    JPanel panel_general, panelAdherentForm, panelAdherentButtons, panelSearch, panelTop;

    private AdherentDao dao;

    public AdherentPanel() {

        setTitle("Gestion des Adhérents");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 🎨 --- STYLE LUMINEUX ---
        Color bgLight = new Color(245, 245, 250);
        Color bgPanel = new Color(255, 255, 255);
        Color bgField = new Color(255, 255, 255);
        Color textColor = new Color(40, 40, 40);
        Color borderColor = new Color(180, 180, 200);

        getContentPane().setBackground(bgLight);
        setLayout(new BorderLayout());

        UIManager.put("Label.foreground", textColor);
        UIManager.put("TextField.background", bgField);
        UIManager.put("TextField.foreground", textColor);
        UIManager.put("TextField.caretForeground", Color.BLACK);
        UIManager.put("TextField.border", BorderFactory.createLineBorder(borderColor, 1));

        // En-tête
        JLabel header = new JLabel("Gestion des Adhérents", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.setForeground(new Color(60, 60, 60));
        header.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        //  Champs
        txtNumAdherent = new JTextField();
        txtNumAdherent.setVisible(false);

        txtNomAdherent = createField(bgField, borderColor);
        txtPrenomAdherent = createField(bgField, borderColor);
        txtEmailAdherent = createField(bgField, borderColor);

        // Tableau
        model = new DefaultTableModel(new Object[]{"Numéro Adhérent", "Nom", "Prénom", "Email"}, 0);
        table = new JTable(model);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setForeground(textColor);
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(220, 220, 220));

        table.getTableHeader().setBackground(new Color(240, 240, 240));
        table.getTableHeader().setForeground(new Color(50, 50, 50));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(new Color(200, 200, 200)));

        // DAO
        dao = new AdherentDao();
        chargerAdherents();

        // FORMULAIRE EN HAUT SUR UNE LIGNE
        panelAdherentForm = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panelAdherentForm.setBackground(bgPanel);
        panelAdherentForm.setBorder(new EmptyBorder(10, 20, 10, 20));

        panelAdherentForm.add(createLabel("Nom :"));
        panelAdherentForm.add(txtNomAdherent);
        txtNomAdherent.setPreferredSize(new Dimension(150, 28));

        panelAdherentForm.add(createLabel("Prénom :"));
        panelAdherentForm.add(txtPrenomAdherent);
        txtPrenomAdherent.setPreferredSize(new Dimension(150, 28));

        panelAdherentForm.add(createLabel("Email :"));
        panelAdherentForm.add(txtEmailAdherent);
        txtEmailAdherent.setPreferredSize(new Dimension(200, 28));

        // Boutons sur la même ligne
        styleButton(btnAddAdherent, new Color(0, 150, 255));
        styleButton(btnUpdateAdherent, new Color(255, 180, 0));
        styleButton(btnDeleteAdherent, new Color(255, 80, 80));

        panelAdherentForm.add(btnAddAdherent);
        panelAdherentForm.add(btnUpdateAdherent);
        panelAdherentForm.add(btnDeleteAdherent);

        // BARRE DE RECHERCHE DÉTACHÉE
        panelSearch = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSearch.setBackground(bgLight);
        panelSearch.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField txtSearch = createField(bgField, borderColor);
        txtSearch.setPreferredSize(new Dimension(350, 32));

        JLabel searchLabel = new JLabel("🔍 Rechercher : ");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchLabel.setForeground(textColor);

        panelSearch.add(searchLabel);
        panelSearch.add(txtSearch);

        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text = txtSearch.getText().toLowerCase();
                sorter.setRowFilter(text.trim().isEmpty() ? null : RowFilter.regexFilter("(?i)" + text));
            }
        });

        // Panel haut = formulaire + recherche
        panelTop = new JPanel(new BorderLayout());
        panelTop.setBackground(bgLight);
        panelTop.add(panelAdherentForm, BorderLayout.NORTH);
        panelTop.add(panelSearch, BorderLayout.SOUTH);

        // Panel général
        panel_general = new JPanel(new BorderLayout());
        panel_general.setBackground(bgLight);
        panel_general.add(panelTop, BorderLayout.NORTH);
        panel_general.add(scroll, BorderLayout.CENTER);

        add(panel_general, BorderLayout.CENTER);
    }

    // Méthodes utilitaires
    private JTextField createField(Color bg, Color border) {
        JTextField f = new JTextField();
        f.setBackground(bg);
        f.setForeground(Color.BLACK);
        f.setCaretColor(Color.BLACK);
        f.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(border, 1, true),
                new EmptyBorder(6, 10, 6, 10)
        ));
        return f;
    }

    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(new Color(60, 60, 60));
        l.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        return l;
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(6, 18, 6, 18));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(color.brighter()); }
            public void mouseExited(MouseEvent e) { btn.setBackground(color); }
        });
    }

    // Chargement automatique
    private void chargerAdherents() {
        model.setRowCount(0);
        List<Adherent> adherents = dao.getAll();

        for (Adherent a : adherents) {
            model.addRow(new Object[]{
                    a.getNumAdherent(),
                    a.getNomAdherent(),
                    a.getPrenomAdherent(),
                    a.getEmailAdherent()
            });
        }
    }

    public void refreshAdherentTable(List<Adherent> adherents) {
        model.setRowCount(0);
        for (Adherent a : adherents) {
            model.addRow(new Object[]{
                    a.getNumAdherent(),
                    a.getNomAdherent(),
                    a.getPrenomAdherent(),
                    a.getEmailAdherent()
            });
        }
    }

    public void setForm(int id, String nom, String prenom, String email) {
        txtNumAdherent.setText(String.valueOf(id));
        txtNomAdherent.setText(nom);
        txtPrenomAdherent.setText(prenom);
        txtEmailAdherent.setText(email);
    }

    public JButton getBtnAddAdherent() { return btnAddAdherent; }
    public JButton getBtnUpdateAdherent() { return btnUpdateAdherent; }
    public JButton getBtnDeleteAdherent() { return btnDeleteAdherent; }

    public JTable getAdherentTable() { return table; }

    public String getNomAdherent() { return txtNomAdherent.getText(); }
    public String getPrenomAdherent() { return txtPrenomAdherent.getText(); }
    public String getEmailAdherent() { return txtEmailAdherent.getText(); }

}
