package ma.est.gestion.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

public class LivrePanel extends JFrame {

    //TABLE
    private JTable table;

    // BOUTONS HEADER
    private JButton btnAjouter;
    private JButton btnSupp;
    private JButton btnGereAdh;
    private JButton btnGereEmp;
    private JButton btnRetour;

    //FORMULAIRE 
    private JPanel formPanel;
    private JTextField tfId;
    private JTextField tfCode;
    private JTextField tfTitre;
    private JTextField tfAuteur;
    private JTextField tfEx;
    private JComboBox<String> cbCategorie;
    private JButton btnValiderForm;

    // CATEGORIE
    private JButton btnSelectCat;

    public LivrePanel() {

        setTitle("Gestion Bibliothèque - Admin");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(236, 240, 241));

        //HEADER 
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(44, 62, 80));
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel title = new JLabel("Gestion des Livres");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JPanel headerBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerBtns.setOpaque(false);

        btnAjouter = styliserBouton("Ajouter", new Color(46, 204, 113));
        btnSupp = styliserBouton("Supprimer", new Color(231, 76, 60));
        btnGereAdh = styliserBouton("Gérer adhérents", new Color(52, 152, 219));
        btnGereEmp = styliserBouton("Gérer emprunts", new Color(241, 196, 15));
        btnRetour = styliserBouton("Retour accueil", new Color(149, 165, 166));

        headerBtns.add(btnAjouter);
        headerBtns.add(btnSupp);
        headerBtns.add(btnGereAdh);
        headerBtns.add(btnGereEmp);
        headerBtns.add(btnRetour);

        header.add(title, BorderLayout.WEST);
        header.add(headerBtns, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // TABLE 
        table = new JTable();
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, BorderLayout.CENTER);

        // FORMULAIRE
        formPanel = new JPanel(new GridLayout(6, 2, 8, 8));
        formPanel.setBorder(new TitledBorder("Informations du livre"));
        formPanel.setBackground(Color.WHITE);

        tfId = new JTextField();
        tfCode = new JTextField();
        tfTitre = new JTextField();
        tfAuteur = new JTextField();
        tfEx = new JTextField();

        cbCategorie = new JComboBox<>();
        cbCategorie.setEditable(true);

        formPanel.add(new JLabel("ID"));
        formPanel.add(tfId);
        formPanel.add(new JLabel("Code"));
        formPanel.add(tfCode);
        formPanel.add(new JLabel("Titre"));
        formPanel.add(tfTitre);
        formPanel.add(new JLabel("Auteur"));
        formPanel.add(tfAuteur);
        formPanel.add(new JLabel("Nombre d'exemplaires"));
        formPanel.add(tfEx);

        btnValiderForm = styliserBouton("Valider", new Color(155, 89, 182));

        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setBackground(getContentPane().getBackground());
        rightPanel.add(formPanel, BorderLayout.CENTER);
        rightPanel.add(btnValiderForm, BorderLayout.SOUTH);

        formPanel.setVisible(false);
        add(rightPanel, BorderLayout.EAST);

        //FOOTER
        btnSelectCat = styliserBouton("Ajouter une catégorie", new Color(52, 73, 94));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        bottom.setBackground(Color.WHITE);
        bottom.add(new JLabel("Catégories :"));
        bottom.add(cbCategorie);
        bottom.add(btnSelectCat);

        add(bottom, BorderLayout.SOUTH);

        // ACTIONS 

        btnAjouter.addActionListener(e -> formPanel.setVisible(true));

        btnRetour.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        btnGereAdh.addActionListener(e -> {
            /*
             * dispose();
             * new AdherentPanel().setVisible(true);
             */
            JOptionPane.showMessageDialog(this,
                    "Ouverture interface gestion adhérents");
        });

        btnGereEmp.addActionListener(e -> {
            /*
             * dispose();
             * new EmpruntPanel().setVisible(true);
             */
            JOptionPane.showMessageDialog(this,
                    "Ouverture interface gestion emprunts");
        });
    }

    // STYLE
    private JButton styliserBouton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return b;
    }

    //GETTERS 
    public JTable getTable() { return table; }

    public JButton getBtnAjouter() { return btnAjouter; }
    public JButton getBtnSupp() { return btnSupp; }
    public JButton getBtnGereAdh() { return btnGereAdh; }
    public JButton getBtnGereEmp() { return btnGereEmp; }
    public JButton getBtnRetour() { return btnRetour; }

    public JButton getBtnValiderForm() { return btnValiderForm; }
    public JPanel getFormPanel() { return formPanel; }

    public JTextField getTfId() { return tfId; }
    public JTextField getTfCode() { return tfCode; }
    public JTextField getTfTitre() { return tfTitre; }
    public JTextField getTfAuteur() { return tfAuteur; }
    public JTextField getTfEx() { return tfEx; }
    public JComboBox<String> getCbCategorie() { return cbCategorie; }

    public JButton getBtnSelectCat() { return btnSelectCat; }

    public void setTableModel(TableModel model) {
        table.setModel(model);
    }
}
