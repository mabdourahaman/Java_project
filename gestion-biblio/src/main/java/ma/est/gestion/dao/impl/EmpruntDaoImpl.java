package ma.est.gestion.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import ma.est.gestion.dao.EmpruntDao;
import ma.est.gestion.model.Adherent;
import ma.est.gestion.model.Emprunt;
import ma.est.gestion.model.Livre;
import ma.est.gestion.util.DatabaseConnection;

public class EmpruntDaoImpl implements EmpruntDao {

    private final Connection connection = DatabaseConnection.getInstance().getConnection();
    
    public EmpruntDaoImpl() {

    }

    // Ajouter un nouvel emprunt
    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void addEmprunt(Emprunt e) {
        if (e == null) { 
            throw new IllegalArgumentException("Emprunt ne peut pas être null");
        }

        String checkSql = "SELECT COUNT(*) FROM emprunt WHERE codeEmprunt = ?";
        String insertSql = "INSERT INTO emprunt (codeEmprunt, numAdherent, dateEmprunt, dateRetour, statut, codeLivre) VALUES (?, ?, ?, ?, ?, ?)";
        String updateLivreSql = "UPDATE livre SET nombreExemplaire = nombreExemplaire - 1 WHERE code = ?";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql);
             PreparedStatement insertStmt = connection.prepareStatement(insertSql);
             PreparedStatement updateLivreStmt = connection.prepareStatement(updateLivreSql)) {

            // Vérifier si l'emprunt existe déjà
            checkStmt.setString(1, e.getCodeEmprunt());
            try (ResultSet rs = checkStmt.executeQuery()) {
                rs.next();
                if (rs.getInt(1) > 0) {
                    throw new IllegalArgumentException("Cet emprunt existe déjà");
                }
            }

            // Insérer le nouvel emprunt
            insertStmt.setString(1, e.getCodeEmprunt());
            insertStmt.setInt(2, e.getNumAdherent());
            insertStmt.setDate(3, new java.sql.Date(e.getDateEmprunt().getTime()));
            insertStmt.setDate(4, new java.sql.Date(e.getDateRetour().getTime()));
            insertStmt.setString(5, e.getStatut());
            insertStmt.setString(6, e.getCodeLivre());
            insertStmt.executeUpdate();

            // Mettre à jour le nombre d'exemplaires du livre
            updateLivreStmt.setString(1, e.getCodeLivre());
            updateLivreStmt.executeUpdate();


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Récupérer tous les emprunts
    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public List<Emprunt> getAllEmprunts() {
        List<Emprunt> emprunts = new ArrayList<>();
        String sql = "SELECT e.*, l.code AS codeLivre, l.auteur, l.titre, l.nombreExemplaire, a.nom, a.prenom, a.email " +
                     "FROM emprunt e " +
                     "JOIN livre l ON e.codeLivre = l.code " +
                     "JOIN adherents a ON e.numAdherent = a.numAdherent";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Livre livre = new Livre(
                        rs.getString("codeLivre"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getInt("nombreExemplaire"),
                        null
                );
                Adherent adherent = new Adherent(
                        rs.getInt("numAdherent"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email")
                );
                Emprunt emprunt = new Emprunt();
                emprunt.setCodeEmprunt(rs.getString("codeEmprunt"));
                emprunt.setLivre(livre);
                emprunt.setAdherent(adherent);
                emprunt.setDateEmprunt(rs.getDate("dateEmprunt"));
                emprunt.setDateRetour(rs.getDate("dateRetour"));
                emprunt.setStatut(rs.getString("statut"));
                emprunt.setCodeLivre(livre.getCode());
                emprunt.setNumAdherent(adherent.getNumAdherent());

                emprunts.add(emprunt);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return emprunts;
    }

    // Mettre à jour le statut d'un emprunt
    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void updateEmprunt(Emprunt e, String newStatut) {
        if (e == null) throw new IllegalArgumentException("Emprunt ne peut pas être null");
        if (newStatut == null || newStatut.trim().isEmpty())
            throw new IllegalArgumentException("Statut ne peut pas être vide");

        String sql = "UPDATE emprunt SET statut = ? WHERE codeEmprunt = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newStatut);
            stmt.setString(2, e.getCodeEmprunt());

            int rows = stmt.executeUpdate();
            if (rows == 0) throw new NoSuchElementException("Emprunt non trouvé: " + e.getCodeEmprunt());

            System.out.println("Emprunt mis à jour: " + e.getCodeEmprunt() + " -> " + newStatut);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Supprimer un emprunt
    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void deleteEmprunt(Emprunt e) {
        if (e == null) throw new IllegalArgumentException("Emprunt ne peut pas être null");

        String sql = "DELETE FROM emprunt WHERE codeEmprunt = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, e.getCodeEmprunt());
            int rows = stmt.executeUpdate();
            if (rows == 0) throw new NoSuchElementException("Emprunt non trouvé: " + e.getCodeEmprunt());

            System.out.println("Emprunt supprimé: " + e.getCodeEmprunt());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Trouver emprunt par code
    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public Emprunt findEmpruntByCode(String codeEmprunt) {
        if (codeEmprunt == null || codeEmprunt.trim().isEmpty())
            throw new IllegalArgumentException("Code ne peut pas être vide");

        String sql = "SELECT e.*, l.titre, l.auteur, l.nombreExemplaire, l.code AS codeLivre, " +
                     "a.nom, a.prenom, a.email, a.numAdherent " +
                     "FROM emprunt e " +
                     "JOIN livre l ON e.codeLivre = l.code " +
                     "JOIN adherents a ON e.numAdherent = a.numAdherent " +
                     "WHERE e.codeEmprunt = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, codeEmprunt);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    Livre livre = new Livre(
                            rs.getString("codeLivre"),
                            rs.getString("titre"),
                            rs.getString("auteur"),
                            rs.getInt("nombreExemplaire"),
                            null
                    );
                    Adherent adherent = new Adherent(
                            rs.getInt("numAdherent"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email")
                    );
                    Emprunt emprunt = new Emprunt();
                    emprunt.setCodeEmprunt(rs.getString("codeEmprunt"));
                    emprunt.setLivre(livre);
                    emprunt.setAdherent(adherent);
                    emprunt.setDateEmprunt(rs.getDate("dateEmprunt"));
                    emprunt.setDateRetour(rs.getDate("dateRetour"));
                    emprunt.setStatut(rs.getString("statut"));
                    emprunt.setCodeLivre(livre.getCode());
                    emprunt.setNumAdherent(adherent.getNumAdherent());
                    return emprunt;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        throw new NoSuchElementException("Emprunt non trouvé: " + codeEmprunt);
    }

    // Clôturer un emprunt
    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void cloturerEmprunt(Emprunt e) {
        if (e == null) throw new IllegalArgumentException("Emprunt ne peut pas être null");

        // Mettre à jour le statut
        updateEmprunt(e, "Retourné");

        // Incrémenter le nombre d'exemplaires du livre
        String sql = "UPDATE livre SET nombreExemplaire = nombreExemplaire + 1 WHERE code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, e.getCodeLivre());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

   @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public List<Emprunt> findEmpruntsByAdherent(int numAdherent) {
        List<Emprunt> result = new ArrayList<>();

        String sql = "SELECT e.*, l.code AS codeLivre, l.titre, l.auteur, l.nombreExemplaire, " +
                 "a.nomAdherent, a.prenom, a.email " +
                 "FROM emprunt e " +
                 "JOIN livre l ON e.codeLivre = l.code " +
                 "JOIN adherents a ON e.numAdherent = a.numAdherent " +
                 "WHERE e.numAdherent = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numAdherent);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Livre livre = new Livre(
                        rs.getString("codeLivre"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getInt("nombreExemplaire"),
                        null
                    );

                    Adherent adherent = new Adherent(
                        numAdherent,
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email")
                    );

                Emprunt emprunt = new Emprunt();
                emprunt.setCodeEmprunt(rs.getString("codeEmprunt"));
                emprunt.setLivre(livre);
                emprunt.setAdherent(adherent);
                emprunt.setDateEmprunt(rs.getDate("dateEmprunt"));
                emprunt.setDateRetour(rs.getDate("dateRetour"));
                emprunt.setStatut(rs.getString("statut"));
                emprunt.setCodeLivre(livre.getCode());
                emprunt.setNumAdherent(numAdherent);

                result.add(emprunt);
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    return result;
}

    @Override 
    @SuppressWarnings("CallToPrintStackTrace") 
    public List<Emprunt> findEmpruntsByStatut(String statut) { 
        if (statut == null || statut.trim().isEmpty()) 
            throw new IllegalArgumentException("Statut ne peut pas être vide"); 
        List<Emprunt> result = new ArrayList<>(); 
        String sql = "SELECT e.*, l.code AS codeLivre, l.titre, l.auteur, l.nombreExemplaire, "
                     + "a.numAdherent, a.nom, a.prenom, a.email " 
                     + "FROM emprunt e " + "JOIN livre l ON e.codeLivre = l.code " 
                     + "JOIN adherents a ON e.numAdherent = a.numAdherent "
                     + "WHERE e.statut = ?";
            try ( PreparedStatement stmt = connection.prepareStatement(sql); ){
                stmt.setString(1, statut); 
                ResultSet rs = stmt.executeQuery(); 
                while (rs.next()) { 

                    Livre livre = new Livre( rs.getString("codeLivre"),
                            rs.getString("titre"), 
                            rs.getString("auteur"),
                            rs.getInt("nombreExemplaire"), 
                            null 
                        );
                        
                        Adherent adherent = new Adherent( rs.getInt("numAdherent"),
                            rs.getString("nom"), 
                            rs.getString("prenom"),
                            rs.getString("email")
                         ); 
                         
                         Emprunt emprunt = new Emprunt();
                          emprunt.setCodeEmprunt(rs.getString("codeEmprunt")); 
                          emprunt.setLivre(livre); 
                          emprunt.setAdherent(adherent); 
                          emprunt.setDateEmprunt(new java.util.Date(rs.getDate("dateEmprunt").getTime()));
                          emprunt.setDateRetour(new java.util.Date(rs.getDate("dateRetour").getTime())); 
                          emprunt.setStatut(rs.getString("statut")); 
                          emprunt.setCodeLivre(rs.getString("codeLivre")); 
                          emprunt.setNumAdherent(rs.getInt("numAdherent")); 
                          result.add(emprunt); } }
                          
            catch (SQLException ex) { 
                ex.printStackTrace(); 
            }
             return result;
    }
}