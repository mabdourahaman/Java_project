package ma.est.gestion.service;

import java.util.List;

import ma.est.gestion.dao.LivreDao;
import ma.est.gestion.model.Livre;

public class LivreService {

    private LivreDao livreDao;

    public LivreService(LivreDao livreDao) {
        this.livreDao = livreDao;
    }

    // Ajouter un livre : {nombreExemplaire >= 1}
    public void ajouterLivre(Livre livre) {
        if (livre.getNombreExemplaire() < 1)
            throw new IllegalArgumentException("Le nombre d'exemplaires doit être >= 1");

        if (livreDao.findByCode(livre.getCode()) != null)
            throw new IllegalStateException("Un livre avec cet ISBN existe déjà.");

        livreDao.save(livre);
    }

    // Supprimer : interdit si exemplaires en prêt
    public void supprimerLivre(String code) {
        Livre livre = livreDao.findByCode(code);

        if (livre == null) 
            throw new IllegalStateException("Livre introuvable.");

        if (!livre.supprimer())
            throw new IllegalStateException("Impossible de supprimer : exemplaires en prêt.");

        livreDao.delete(code);
    }

    // Mettre à jour un livre
    public void modifierLivre(Livre livre) {
        if (livre.getNombreExemplaire() < 1)
            throw new IllegalArgumentException("Le nombre d'exemplaires doit être >= 1");

        livreDao.update(livre);
    }

    public List<Livre> listerLivres() {
        return livreDao.findAll();
    }
}
