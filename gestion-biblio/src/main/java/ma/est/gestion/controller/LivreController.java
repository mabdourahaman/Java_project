package ma.est.gestion.controller;

import ma.est.gestion.model.Categorie;
import ma.est.gestion.model.Livre;
import ma.est.gestion.service.LivreService;
import ma.est.gestion.view.LivrePanel;

import javax.swing.*;
import java.util.List;

public class LivreController {

    private LivreService livreService;
    private LivrePanel view;

    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    public void setView(LivrePanel view) {
        this.view = view;
    }

    // Charger table
    public void chargerLivres() {
        List<Livre> livres = livreService.listerLivres();
        view.afficherLivres(livres);
    }

    public void ajouterLivre(String code, String titre, String auteur, String nbEx, String categorie) {
        try {
            int nb = Integer.parseInt(nbEx);

            Livre livre = new Livre(
                    code, titre, auteur, nb, new Categorie(categorie)
            );

            livreService.ajouterLivre(livre);
            chargerLivres();
            JOptionPane.showMessageDialog(null, "Livre ajouté avec succès !");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void modifierLivre(String code, String titre, String auteur, String nbEx, String categorie) {
        try {
            int nb = Integer.parseInt(nbEx);

            Livre livre = new Livre(
                    code, titre, auteur, nb, new Categorie(categorie)
            );

            livreService.modifierLivre(livre);
            chargerLivres();
            JOptionPane.showMessageDialog(null, "Livre modifié !");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void supprimerLivre(String code) {
        try {
            livreService.supprimerLivre(code);
            chargerLivres();
            JOptionPane.showMessageDialog(null, "Livre supprimé !");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
