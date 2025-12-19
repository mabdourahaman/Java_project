package ma.est.gestion.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import ma.est.gestion.dao.EmpruntDao;
import ma.est.gestion.model.Emprunt;
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
        updateCount();

        initController();
    }

    private void initController() {

        view.getSupprimer().addActionListener(e -> supprimerEmprunt());
        view.getModifier().addActionListener(e -> modifierEmprunt());

    }

    public void supprimerEmprunt() {

        int Row = view.getTable().getSelectedRow();

        if (Row == -1) {

            JOptionPane.showMessageDialog( view, "Veuillez sélectionner un emprunt à supprimer",
            "Information", JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        String codeEmprunt = view.getTable().getValueAt(Row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog( view, "Voulez-vous supprimer l'emprunt " + codeEmprunt + " ?",
        "Confirmation", JOptionPane.YES_NO_OPTION   );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        var emprunt = dao.findEmpruntByCode(codeEmprunt);

        dao.deleteEmprunt(emprunt);
        view.refreshTable(dao.getAllEmprunts());
        updateCount();
    }


    @SuppressWarnings("CallToPrintStackTrace")
    public void modifierEmprunt(){

        int Row = view.getTable().getSelectedRow();

        if (Row == -1) {

            JOptionPane.showMessageDialog( view, "Veuillez sélectionner un emprunt à Modifier",
            "Information", JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }
        
        if (view.getTable().isEditing()) {
            var editor = view.getTable().getCellEditor();
            if (editor != null) editor.stopCellEditing();
        }

        String codeEmprunt = view.getTable().getValueAt(Row, 0).toString();

        Object dateRetObj = view.getTable().getValueAt(Row, 5);
        String dateRet = dateRetObj == null ? "" : dateRetObj.toString();

        Date dateRetour = null;
        if (!dateRet.isEmpty() && !dateRet.equalsIgnoreCase("null")) {
            try {
                SimpleDateFormat sdf = dateRet.length() == 10
                        ? new SimpleDateFormat("yyyy-MM-dd")
                        : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateRetour = sdf.parse(dateRet);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(view, "Format de date invalide : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String statut = view.getTable().getValueAt(Row, 6).toString().trim();

        int confirm = JOptionPane.showConfirmDialog( view, "Voulez-vous modifier l'emprunt " + codeEmprunt + " ?",
        "Confirmation", JOptionPane.YES_NO_OPTION   );

        if (confirm != JOptionPane.YES_OPTION) {
            view.refreshTable(dao.getAllEmprunts());
            return;
        }

        Emprunt emprunt = new Emprunt();
        emprunt.setCodeEmprunt(codeEmprunt);
        emprunt.setDateRetour(dateRetour);
        emprunt.setStatut(statut);

        dao.modifierEmprunt(emprunt);
        JOptionPane.showMessageDialog(view, "Emprunt modifié avec succès !");
        view.refreshTable(dao.getAllEmprunts());
        updateCount();

    }

    private void updateCount() {
        try {
            int total = dao.getEmpruntCount();
            view.getLabel4Emp().setText("Total Emprunts: " + total);
        } catch (Exception ex) {
            System.err.println("Erreur lors du calcul du nombre d'emprunts: " + ex.getMessage());
        }
    }

}
