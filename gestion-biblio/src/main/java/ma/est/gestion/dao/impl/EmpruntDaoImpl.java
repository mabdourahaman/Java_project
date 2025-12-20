package ma.est.gestion.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import ma.est.gestion.dao.EmpruntDao;
import ma.est.gestion.model.Adherent;
import ma.est.gestion.model.Emprunt;
import ma.est.gestion.model.Livre;

public class EmpruntDaoImpl implements EmpruntDao {

    
    public EmpruntDaoImpl() {

    // ===================== AJOUT =====================
    @Override
    public void addEmprunt(Emprunt e) {

        if (e == null)
            throw new IllegalArgumentException("Emprunt null");

        String checkEx =
                "SELECT nombreExemplaire FROM livre WHERE code=?";
        String insert =
                "INSERT INTO emprunt (codeEmprunt, numAdherent, dateEmprunt, dateRetour, statut, codeLivre) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        String updateLivre =
                "UPDATE livre SET nombreExemplaire = nombreExemplaire - 1 WHERE code=?";

        try (
            PreparedStatement psCheck = connection.prepareStatement(checkEx);
            PreparedStatement psInsert = connection.prepareStatement(insert);
            PreparedStatement psUpdate = connection.prepareStatement(updateLivre)
        ) {
            psCheck.setString(1, e.getCodeLivre());
            ResultSet rs = psCheck.executeQuery();

            if (!rs.next() || rs.getInt("nombreExemplaire") <= 0)
                throw new IllegalStateException("Aucun exemplaire disponible");

            psInsert.setString(1, e.getCodeEmprunt());
            psInsert.setInt(2, e.getNumAdherent());
            psInsert.setDate(3, new java.sql.Date(e.getDateEmprunt().getTime()));
            psInsert.setDate(4, new java.sql.Date(e.getDateRetour().getTime()));
            psInsert.setString(5, e.getStatut());
            psInsert.setString(6, e.getCodeLivre());
            psInsert.executeUpdate();

            psUpdate.setString(1, e.getCodeLivre());
            psUpdate.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // ===================== GET ALL =====================
    @Override
    public List<Emprunt> getAllEmprunts() {

        List<Emprunt> list = new ArrayList<>();

        String sql =
            "SELECT e.*, l.code AS codeLivre, l.titre, l.auteur, l.nombreExemplaire, " +
            "a.numAdherent, a.nom, a.prenom, a.email " +
            "FROM emprunt e " +
            "JOIN livre l ON e.codeLivre = l.code " +
            "JOIN adherents a ON e.numAdherent = a.numAdherent";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Livre livre = new Livre(
                        rs.getString("codeLivre"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getInt("nombreExemplaire")
                );

                Adherent adh = new Adherent(
                        rs.getInt("numAdherent"),
                        rs.getString("email"),
                        rs.getString("nom"),
                        rs.getString("prenom")
                );

                Emprunt e = new Emprunt();
                e.setCodeEmprunt(rs.getString("codeEmprunt"));
                e.setLivre(livre);
                e.setAdherent(adh);
                e.setDateEmprunt(rs.getDate("dateEmprunt"));
                e.setDateRetour(rs.getDate("dateRetour"));
                e.setStatut(rs.getString("statut"));
                e.setCodeLivre(livre.getCode());
                e.setNumAdherent(adh.getNumAdherent());

                list.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // ===================== UPDATE STATUT =====================
    @Override
    public void updateEmprunt(Emprunt e, String statut) {

        String sql = "UPDATE emprunt SET statut=? WHERE codeEmprunt=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, statut);
            ps.setString(2, e.getCodeEmprunt());

            if (ps.executeUpdate() == 0)
                throw new NoSuchElementException("Emprunt introuvable");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // ===================== DELETE =====================
    @Override
    public void deleteEmprunt(Emprunt e) {

        String sql = "DELETE FROM emprunt WHERE codeEmprunt=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, e.getCodeEmprunt());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // ===================== FIND BY CODE =====================
    @Override
    public Emprunt findEmpruntByCode(String code) {

        String sql =
            "SELECT e.*, l.code AS codeLivre, l.titre, l.auteur, l.nombreExemplaire, " +
            "a.numAdherent, a.nom, a.prenom, a.email " +
            "FROM emprunt e " +
            "JOIN livre l ON e.codeLivre = l.code " +
            "JOIN adherents a ON e.numAdherent = a.numAdherent " +
            "WHERE e.codeEmprunt=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();



    // ===================== PAR ADHERENT =====================
    @Override
    public List<Emprunt> findEmpruntsByAdherent(int numAdherent) {

        List<Emprunt> list = new ArrayList<>();

        String sql = "SELECT * FROM emprunt WHERE numAdherent=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, numAdherent);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Emprunt e = new Emprunt();
                e.setCodeEmprunt(rs.getString("codeEmprunt"));
                e.setNumAdherent(numAdherent);
                e.setCodeLivre(rs.getString("codeLivre"));
                e.setDateEmprunt(rs.getDate("dateEmprunt"));
                e.setDateRetour(rs.getDate("dateRetour"));
                e.setStatut(rs.getString("statut"));
                list.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // ===================== PAR STATUT =====================
    @Override
    public List<Emprunt> findEmpruntsByStatut(String statut) {

        List<Emprunt> list = new ArrayList<>();

        String sql = "SELECT * FROM emprunt WHERE statut=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, statut);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Emprunt e = new Emprunt();
                e.setCodeEmprunt(rs.getString("codeEmprunt"));
                e.setNumAdherent(rs.getInt("numAdherent"));
                e.setCodeLivre(rs.getString("codeLivre"));
                e.setDateEmprunt(rs.getDate("dateEmprunt"));
                e.setDateRetour(rs.getDate("dateRetour"));
                e.setStatut(statut);
                list.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // ===================== CLOTURER =====================
    @Override
    public void cloturerEmprunt(Emprunt e) {

        String sql = "UPDATE emprunt SET statut='RETURNE' WHERE codeEmprunt=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, e.getCodeEmprunt());
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // ===================== COUNT =====================
    @Override
    public int getEmpruntCount() {

        String sql = "SELECT COUNT(*) FROM emprunt";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next())
                return rs.getInt(1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
