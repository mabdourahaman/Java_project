package ma.est.gestion.controller;

import ma.est.gestion.dao.EmpruntDao;
import ma.est.gestion.view.EmpruntPanel;

/**
 * Contrôleur pour gérer les opérations d'emprunt.
 * Gère la création, la mise à jour, la suppression et la récupération des emprunts.
 */
public class EmpruntController {

    private EmpruntDao dao;
    private EmpruntPanel view;

    public EmpruntController( EmpruntDao dao, EmpruntPanel view){
        this.dao = dao;
        this.view = view;

        view.refreshTable(dao.getAllEmprunts());
        view.getSupprimer().addActionListener(e -> supprimerEmprunt());
        view.getModifier().addActionListener(e -> modifierEmprunt());

    }

    public void supprimerEmprunt(){

    }

    public void modifierEmprunt(){

    }
    

}