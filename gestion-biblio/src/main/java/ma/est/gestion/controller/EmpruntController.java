package ma.est.gestion.controller;

import ma.est.gestion.dao.EmpruntDao;
import ma.est.gestion.dao.impl.EmpruntDaoImpl;
import ma.est.gestion.dao.LivreDao;
import ma.est.gestion.dao.impl.LivreDaoImpl;
import ma.est.gestion.model.Emprunt;
import ma.est.gestion.model.Livre;
import ma.est.gestion.model.Adherent;

import java.util.List;

/**
 * Contrôleur pour gérer les opérations d'emprunt.
 * Gère la création, la mise à jour, la suppression et la récupération des emprunts.
 */
public class EmpruntController {

    private final EmpruntDao empruntDao;
    private final LivreDao livreDao;

    /**
     * Initialiser le contrôleur avec les instances DAO.
     */
    public EmpruntController() {
        this.livreDao = new LivreDaoImpl();
        this.empruntDao = new EmpruntDaoImpl(this.livreDao);
    }

    /**
     * Créer un nouvel emprunt pour un adhérent.
     * Capture automatiquement les détails du livre et de l'adhérent.
     * 
     * @param codeLivre Le code du livre à emprunter
     * @param adherent L'adhérent qui emprunte le livre
     * @param statut Le statut de l'emprunt (ex: "EMPRUNTE")
     * @return L'emprunt créé avec tous les détails remplis
     */
    public Emprunt creerEmprunt(String codeLivre, Adherent adherent, String statut) {
        // Obtenir le livre
        Livre livre = livreDao.findByCode(codeLivre);
        if (livre == null) {
            throw new IllegalArgumentException("Livre non trouvé avec le code: " + codeLivre);
        }

        if (adherent == null) {
            throw new IllegalArgumentException("L'adhérent ne peut pas être null");
        }

        // Vérifier si le livre a des exemplaires disponibles
        if (livre.getNombreExemplaire() <= 0) {
            throw new IllegalArgumentException("Aucun exemplaire disponible pour: " + livre.getTitre());
        }

        // Vérifier si l'adhérent a atteint le maximum d'emprunts actifs
        List<Emprunt> adherentLoans = empruntDao.findEmpruntsByAdherent(adherent.getNumAdherent());
        if (adherentLoans.size() >= 3) {
            throw new IllegalArgumentException("L'adhérent a atteint le maximum d'emprunts (3 emprunts actifs)");
        }

        // Créer l'emprunt (le constructeur calcule automatiquement dateEmprunt/dateRetour)
        Emprunt emprunt = new Emprunt(livre, adherent, statut);

        // Ajouter au DAO
        empruntDao.addEmprunt(emprunt);

        // Réduire le stock du livre
        livre.setNombreExemplaire(livre.getNombreExemplaire() - 1);
        livreDao.update(livre);

        return emprunt;
    }

    /**
     * Retourner un livre (fermer l'emprunt).
     * Décrémente le nombre d'emprunts actifs et restaure le stock du livre.
     * 
     * @param codeEmprunt Le code de l'emprunt à fermer
     */
    public void retournerLivre(String codeEmprunt) {
        // Trouver l'emprunt
        Emprunt emprunt = empruntDao.findEmpruntByCode(codeEmprunt);
        if (emprunt == null) {
            throw new IllegalArgumentException("Emprunt non trouvé avec le code: " + codeEmprunt);
        }

        // Mettre à jour le statut de l'emprunt à fermé
        empruntDao.cloturerEmprunt(emprunt);

        // Restaurer le stock du livre
        Livre livre = emprunt.getLivre();
        livre.setNombreExemplaire(livre.getNombreExemplaire() + 1);
        livreDao.update(livre);
    }

    /**
     * Obtenir tous les emprunts.
     * 
     * @return Liste de tous les emprunts
     */
    public List<Emprunt> getAllEmprunts() {
        return empruntDao.getAllEmprunts();
    }

    /**
     * Obtenir tous les emprunts actifs pour un adhérent spécifique.
     * 
     * @param numAdherent Le numéro de l'adhérent
     * @return Liste des emprunts pour cet adhérent
     */
    public List<Emprunt> getEmpruntsByAdherent(int numAdherent) {
        return empruntDao.findEmpruntsByAdherent(numAdherent);
    }

    /**
     * Obtenir tous les emprunts avec un statut spécifique (ex: "EMPRUNTE", "RETOURNE").
     * 
     * @param statut Le statut à filtrer
     * @return Liste des emprunts avec le statut donné
     */
    public List<Emprunt> getEmpruntsByStatut(String statut) {
        return empruntDao.findEmpruntsByStatut(statut);
    }

    /**
     * Mettre à jour le statut d'un emprunt.
     * 
     * @param codeEmprunt Le code de l'emprunt
     * @param newStatut Le nouveau statut
     */
    public void updateEmpruntStatus(String codeEmprunt, String newStatut) {
        Emprunt emprunt = empruntDao.findEmpruntByCode(codeEmprunt);
        if (emprunt == null) {
            throw new IllegalArgumentException("Emprunt non trouvé avec le code: " + codeEmprunt);
        }
        empruntDao.updateEmprunt(emprunt, newStatut);
    }

    /**
     * Supprimer un emprunt du système.
     * 
     * @param codeEmprunt Le code de l'emprunt à supprimer
     */
    public void deleteEmprunt(String codeEmprunt) {
        Emprunt emprunt = empruntDao.findEmpruntByCode(codeEmprunt);
        if (emprunt == null) {
            throw new IllegalArgumentException("Emprunt non trouvé avec le code: " + codeEmprunt);
        }
        empruntDao.deleteEmprunt(emprunt);
    }

}