package ma.est.gestion.dao.impl;

import java.sql.*;
import ma.est.gestion.model.*;
import ma.est.gestion.util.DatabaseConnection;
import ma.est.gestion.util.PasswordUtil;

public class UtilisateurDaoImpl {

    public Utilisateur authentifier(String login, String password) {

        String sql = """
                SELECT * FROM utilisateur
                WHERE login=? AND statut='ACTIF'
                """;

        try (PreparedStatement ps =
                     DatabaseConnection.getConnection().prepareStatement(sql)) {

            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashed = rs.getString("password");

                if (PasswordUtil.verifyPassword(password, hashed)) {
                    return new Utilisateur(
                            rs.getInt("id"),
                            rs.getString("login"),
                            hashed,
                            rs.getString("statut"),
                            new Role(rs.getString("role"))
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
