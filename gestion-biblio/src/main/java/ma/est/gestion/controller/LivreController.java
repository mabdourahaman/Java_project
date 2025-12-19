package ma.est.gestion.controller;

import javax.swing.table.DefaultTableModel;
import ma.est.gestion.dao.LivreDao;
import ma.est.gestion.model.*;
import ma.est.gestion.view.LivrePanel;

public class LivreController {

    private LivrePanel view;
    private LivreDao dao;
    private DefaultTableModel model;

    public LivreController(LivrePanel view, LivreDao dao) {
        this.view = view;
        this.dao = dao;

        initTable();
        initActions();
        chargerLivres();
    }

    private void initTable() {
        model = new DefaultTableModel(
                new Object[]{"ID", "Code", "Titre", "Auteur", "Exemplaire", "Catégorie"}, 0
        );
        view.setTableModel(model);
    }

    private void initActions() {

        view.getBtnAjouter().addActionListener(e ->
                view.getFormPanel().setVisible(true)
        );

        view.getBtnValiderForm().addActionListener(e -> ajouterLivre());

        view.getBtnSupp().addActionListener(e -> supprimerSelection());

        view.getBtnGereAdh().addActionListener(e ->
                view.dispose()
        );
    }

    private void chargerLivres() {
        model.setRowCount(0);
        for (Livre l : dao.getAll()) {
            model.addRow(new Object[]{
                    l.getId(),
                    l.getCode(),
                    l.getTitre(),
                    l.getAuteur(),
                    l.getNombreExemplaire(),
                    l.getCategorie()
            });
        }
    }

    private void ajouterLivre() {

        Livre l = new Livre(
                Integer.parseInt(view.getTfId().getText()),
                view.getTfCode().getText(),
                view.getTfTitre().getText(),
                view.getTfAuteur().getText(),
                Integer.parseInt(view.getTfEx().getText()),
                new Categorie(view.getCbCategorie().getSelectedItem().toString())
        );

        dao.addLivre(l);
        chargerLivres();
        view.getFormPanel().setVisible(false);
    }

    private void supprimerSelection() {
        int row = view.getTable().getSelectedRow();
        if (row != -1) {
            model.removeRow(row);
        }
    }
}
