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
        
        String codeEmprunt = view.getTable().getValueAt(Row, 0).toString();
        int numAdherent = Integer.parseInt(view.getTable().getValueAt(Row, 1).toString());

        String dateEmp = view.getTable().getValueAt(Row, 2).toString();
        String dateRet = view.getTable().getValueAt(Row, 3).toString();
        
        SimpleDateFormat sdf;
        if (dateEmp.length() == 10) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        Date dateEmprunt = null;
        Date dateRetour = null; 

        try {
            dateEmprunt = sdf.parse(dateEmp); 
            dateRetour = sdf.parse(dateRet); 
            
        } catch (ParseException e) {
            e.printStackTrace();
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

    }

}
