package ma.est.gestion.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ma.est.gestion.model.Livre;
import ma.est.gestion.model.Categorie;

public class LivreDao {

    private final String URL = "jdbc:mysql://localhost:3306/gestion_bibliotheque";
    private final String USER = "root";
    private final String PASS = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public void addLivre(Livre l) {
        String sql = "INSERT INTO livre VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, l.getId());
            ps.setString(2, l.getCode());
            ps.setString(3, l.getTitre());
            ps.setString(4, l.getAuteur());
            ps.setInt(5, l.getNombreExemplaire());
            ps.setString(6, l.getCategorie().toString());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Livre> getAll() {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM livre";

        try (Connection c = getConnection();
             Statement st = c.createStatement();
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
            e.printStackTrace();
        }
        return livres;
    }

    public void deleteLivre(int index) {
        // À améliorer plus tard (delete by id)
    }
}
