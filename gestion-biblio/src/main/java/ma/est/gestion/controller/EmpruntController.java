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
        // fetch count from DB and update label
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

            JOptionPane.showMessageDialog( view, "Veuillez sélectionner un emprunt à supprimer",
            "Information", JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }
        
        // Commit any active cell edit so updated values are in the model
        if (view.getTable().isEditing()) {
            var editor = view.getTable().getCellEditor();
            if (editor != null) editor.stopCellEditing();
        }

        String codeEmprunt = view.getTable().getValueAt(Row, 0).toString();
        int numAdherent = Integer.parseInt(view.getTable().getValueAt(Row, 1).toString());

        Object dateEmpObj = view.getTable().getValueAt(Row, 2);
        Object dateRetObj = view.getTable().getValueAt(Row, 3);
        
        String dateEmp = dateEmpObj == null ? "" : dateEmpObj.toString();
        String dateRet = dateRetObj == null ? "" : dateRetObj.toString();
        
        SimpleDateFormat sdf;
        if (dateEmp.length() == 10) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        Date dateEmprunt = null;
        Date dateRetour = null; 

        try {
            if (!dateEmp.isEmpty()) {
                dateEmprunt = sdf.parse(dateEmp);
            }
            if (!dateRet.isEmpty() && !dateRet.equalsIgnoreCase("null")) {
                dateRetour = sdf.parse(dateRet);
            } else {
                dateRetour = null;
            }
            
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(view, "Format de date invalide : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String statut = view.getTable().getValueAt(Row, 4).toString();
        String codeLivre = view.getTable().getValueAt(Row, 5).toString();

        int confirm = JOptionPane.showConfirmDialog( view, "Voulez-vous modifier l'emprunt " + codeEmprunt + " ?",
        "Confirmation", JOptionPane.YES_NO_OPTION   );

        if (confirm != JOptionPane.YES_OPTION) {
            view.refreshTable(dao.getAllEmprunts());
            return;
        }

        Emprunt emprunt = new Emprunt( codeEmprunt, numAdherent, dateEmprunt, dateRetour, statut, codeLivre);

        dao.modifierEmprunt(emprunt);
        JOptionPane.showMessageDialog(view, "Emprunt modifié avec succès !");
        view.refreshTable(dao.getAllEmprunts());
        updateCount();

    }

    // Update the total emprunt label by querying the database
    private void updateCount() {
        try {
            int total = dao.getEmpruntCount();
            view.getLabel4Emp().setText("Total Emprunts: " + total);
        } catch (Exception ex) {
            // don't crash the UI for a counting failure; log instead
            System.err.println("Erreur lors du calcul du nombre d'emprunts: " + ex.getMessage());
        }
    }

}
