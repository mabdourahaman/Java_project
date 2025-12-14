package ma.est.gestion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import ma.est.gestion.model.Adherent;

public class AdherentDao implements AdherentDaoi {

    private final Connection connection = DBConnection.getInstance().getConnection();

    public void ajouter(Adherent adherent) {
        // Code pour ajouter un adhérent
        String sql = "INSERT INTO adherents (nom, prenom, email) VALUES (?, ?, ?)";
        // Exécution de la requête avec les paramètres de l'objet adherent

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, adherent.getNomAdherent());
            stmt.setString(2, adherent.getPrenomAdherent());
            stmt.setString(3, adherent.getEmailAdherent());
            stmt.executeUpdate();

            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifier(Adherent adherent) {
        // Code pour modifier un adhérent
        String sql = "UPDATE adherents SET nom = ?, prenom = ?, email = ? WHERE numAdherent = ?";
        // Exécution de la requête avec les paramètres de l'objet adherent
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, adherent.getNomAdherent());
            stmt.setString(2, adherent.getPrenomAdherent());
            stmt.setString(3, adherent.getEmailAdherent());
            stmt.setInt(4, adherent.getNumAdherent());
            stmt.executeUpdate();

            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void supprimer(int numAdherent) {
        // Code pour supprimer un adhérent
        String sql = "DELETE FROM adherents WHERE numAdherent = ?";
        // Exécution de la requête avec le numéro d'adhérent
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, numAdherent);
            stmt.executeUpdate();

            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
}

    public Adherent trouverParId(int numAdherent) {
        // Code pour trouver un adhérent par son numéro
        String sql = "SELECT * FROM adherents WHERE numAdherent = ?";
        Adherent adherent = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, numAdherent);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                adherent = new Adherent(
                    rs.getInt("numAdherent"),
                    rs.getString("email"),
                    rs.getString("nom"),
                    rs.getString("prenom")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adherent;
    }

    public List<Adherent> getAll() {
        // Code pour lister tous les adhérents
        String sql = "SELECT * FROM adherents";
        List<Adherent> adherents = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            var rs = stmt.executeQuery();
            while (rs.next()) {
                Adherent adherent = new Adherent(
                    rs.getInt("numAdherent"),
                    rs.getString("email"),
                    rs.getString("nom"),
                    rs.getString("prenom")
                );
                adherents.add(adherent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adherents;
    }
}
