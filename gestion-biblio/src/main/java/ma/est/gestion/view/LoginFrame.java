package ma.est.gestion.view;

import java.awt.*;
import javax.swing.*;

import ma.est.gestion.dao.impl.UtilisateurDaoImpl;
import ma.est.gestion.model.Utilisateur;
import ma.est.gestion.util.DatabaseConnection;

public class LoginFrame extends JFrame {

    private JTextField tfLogin;
    private JPasswordField pfPassword;
    private UtilisateurDaoImpl userDao;

    public LoginFrame() {

        DatabaseConnection.getConnection();

        userDao = new UtilisateurDaoImpl();

        setTitle("Gestion Bibliothèque");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(header(), BorderLayout.NORTH);
        add(content(), BorderLayout.CENTER);
    }

    private JPanel header() {
        JPanel h = new JPanel();
        h.setBackground(new Color(52, 152, 219));
        JLabel l = new JLabel("Bienvenue dans la Bibliothèque");
        l.setFont(new Font("Arial", Font.BOLD, 20));
        l.setForeground(Color.WHITE);
        h.add(l);
        return h;
    }

    private JPanel content() {

        JPanel main = new JPanel(new GridLayout(2, 1, 10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ADMIN
        JPanel admin = new JPanel(new GridLayout(3, 2, 10, 10));
        admin.setBorder(BorderFactory.createTitledBorder("Administrateur"));

        tfLogin = new JTextField();
        pfPassword = new JPasswordField();

        JButton btnAdmin = new JButton("Connexion Admin");
        btnAdmin.setBackground(new Color(231, 76, 60));
        btnAdmin.setForeground(Color.WHITE);

        btnAdmin.addActionListener(e -> loginAdmin());

        admin.add(new JLabel("Login : "));
        admin.add(tfLogin);
        admin.add(new JLabel("Mot de passe : "));
        admin.add(pfPassword);
        admin.add(new JLabel());
        admin.add(btnAdmin);

        // ADHERENT
        JPanel adh = new JPanel(new BorderLayout());
        adh.setBorder(BorderFactory.createTitledBorder("Utilisateur()"));

        JButton btnAdh = new JButton("Se connecter");
        btnAdh.setBackground(new Color(46, 204, 113));
        btnAdh.setForeground(Color.WHITE);

        btnAdh.addActionListener(e -> {
            dispose();
            new UtilisateurPanel().setVisible(true);
        });

        adh.add(btnAdh, BorderLayout.CENTER);

        main.add(admin);
        main.add(adh);

        return main;
    }

    private void loginAdmin() {

        String login = tfLogin.getText();
        String pwd = new String(pfPassword.getPassword());

        Utilisateur u = userDao.authentifier(login, pwd);

        if ("admin".equals(login) && "admin".equals(pwd) ||
                (u != null && "ADMIN".equals(u.getRole().getNomRole()))) {

            dispose();
            new MainFrame().setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this,
                    "Accès refusé",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

