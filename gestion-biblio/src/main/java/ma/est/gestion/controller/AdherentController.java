package ma.est.gestion.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import ma.est.gestion.dao.AdherentDao;
import ma.est.gestion.model.Adherent;
import ma.est.gestion.view.AdherentPanel;

public class AdherentController {
    private final AdherentDao dao;
    private final AdherentPanel panel;

    public AdherentController(AdherentDao dao, AdherentPanel panel) {
        this.dao = dao;
        this.panel = panel;

        initController();
    }

    private void initController() {
        panel.getBtnAddAdherent().addActionListener(e -> ajouter());
        panel.getBtnUpdateAdherent().addActionListener(e -> modifier());
        panel.getBtnDeleteAdherent().addActionListener(e -> supprimer());

        panel.getAdherentTable().addMouseListener(new MouseAdapter() {
    	    @Override
    	    public void mouseClicked(MouseEvent e) {
    	        int row = panel.getAdherentTable().getSelectedRow();
    	        if (row >= 0) {
    	            int id = (int) panel.getAdherentTable().getValueAt(row, 0);
    	            String nom = panel.getAdherentTable().getValueAt(row, 1).toString();
                    String prenom = panel.getAdherentTable().getValueAt(row, 2).toString();
                    String email = panel.getAdherentTable().getValueAt(row, 3).toString();
    	            panel.setForm(id, nom, prenom, email);
    	        }
    	    }
    	});

    
    }

    private void ajouter() {
        try{
            String nom = panel.getNomAdherent();
            String prenom = panel.getPrenomAdherent();
            String email = panel.getEmailAdherent(); 

            Adherent adherent = new Adherent(0, email, nom, prenom);
            dao.ajouter(adherent);
            List<Adherent> adherents = dao.getAll();
            panel.refreshAdherentTable(adherents);
        } catch (Exception e) {
            e.printStackTrace();
    }
}

    private void modifier() {
        try{
            int id = Integer.parseInt(panel.getAdherentTable().getValueAt(panel.getAdherentTable().getSelectedRow(), 0).toString());
            String nom = panel.getNomAdherent();
            String prenom = panel.getPrenomAdherent();
            String email = panel.getEmailAdherent(); 

            Adherent adherent = new Adherent(id, email, nom, prenom);
            dao.modifier(adherent);
            List<Adherent> adherents = dao.getAll();
            panel.refreshAdherentTable(adherents);
        } catch (Exception e) {
            e.printStackTrace();
    }

}

    private void supprimer() {
        try{
            int id = Integer.parseInt(panel.getAdherentTable().getValueAt(panel.getAdherentTable().getSelectedRow(), 0).toString());

            dao.supprimer(id);
            List<Adherent> adherents = dao.getAll();
            panel.refreshAdherentTable(adherents);
        } catch (Exception e) {
            e.printStackTrace();
    }
}
}
