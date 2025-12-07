package ma.est.gestion.dao.impl;

import ma.est.gestion.*;
import ma.est.gestion.dao.UtilisateurDao;
import ma.est.gestion.model.Utilisateur;
import ma.est.gestion.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDaoImpl implements UtilisateurDao {

	private Connection connection;

	public UtilisateurDaoImpl(Connection connection) {
		this.connection = connection;
	}

	public void ajouter(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateur(nom, email, password) VALUES(?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getEmail());
            ps.setString(3, utilisateur.getPassword());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifier(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateur SET nom=?, email=?, password=? WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getEmail());
            ps.setString(3, utilisateur.getPassword());
            ps.setInt(4, utilisateur.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int id) {
        String sql = "DELETE FROM utilisateur WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Utilisateur getById(int id) {
        String sql = "SELECT * FROM utilisateur WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Utilisateur> getAll() {
        List<Utilisateur> liste = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur";

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Utilisateur u = new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                liste.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return liste;
    }
}
