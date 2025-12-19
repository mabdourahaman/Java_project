package ma.est.gestion.dao;

import java.sql.*;
import ma.est.gestion.model.Utilisateur;
import ma.est.gestion.model.Role;

public class UtilisateurDao {

    private final String URL = "jdbc:mysql://localhost:3306/gestion_bibliotheque";
    private final String USER = "root";
    private final String PASS = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Vérifie login/password
    public Utilisateur authentifier(String login, String password) {
        String sql = "SELECT * FROM utilisateur WHERE login=? AND password=? AND statut='ACTIF'";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, login);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("statut"),
                        new Role(rs.getString("role"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
