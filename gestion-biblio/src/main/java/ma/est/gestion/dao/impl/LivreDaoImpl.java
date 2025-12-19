package ma.est.gestion.dao.impl;

import java.sql.*;
import java.util.*;
import ma.est.gestion.model.*;
import ma.est.gestion.util.DatabaseConnection;

public class LivreDaoImpl {

    public List<Livre> getAll() {

        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM livre";

        try (Statement st =
                     DatabaseConnection.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                livres.add(new Livre(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getInt("nombreExemplaire"),
                        new Categorie(rs.getString("categorie"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return livres;
    }
}
