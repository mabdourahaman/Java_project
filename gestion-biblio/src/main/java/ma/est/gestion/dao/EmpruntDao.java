package ma.est.gestion.dao;

import java.sql.Connection;
import java.util.*;

import ma.est.gestion.model.Emprunt;
import ma.est.gestion.util.DatabaseConnection;


public interface EmpruntDao {

    Connection conn = DatabaseConnection.getInstance();

    void addEmprunt(Emprunt e);

    List<Emprunt> getAllEmprunts();

    void updateEmprunt(Emprunt e, String newStatut);

    void deleteEmprunt(Emprunt e);

    Emprunt findEmpruntByCode(String codeEmprunt);

    List<Emprunt> findEmpruntsByAdherent(int numAdherent);

    List<Emprunt> findEmpruntsByStatut(String statut);

    void cloturerEmprunt(Emprunt e);

}
