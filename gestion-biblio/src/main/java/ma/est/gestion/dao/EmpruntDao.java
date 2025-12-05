package ma.est.gestion.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

import ma.est.gestion.model.Emprunt;

public class EmpruntDao {

    private final List<Emprunt> emprunts = new ArrayList<>();
    
    public void addEmprunt(Emprunt e ){
        for (Emprunt existing : emprunts){
            if (existing.getDateEmprunt().equals(e.getDateEmprunt()))
                throw new IllegalArgumentException("Cet emprunt est deja active");
        }
        emprunts.add(e);
        System.out.println("Emprunt enregistre " + e.getStatut());
    }

    public Date CalculerDateRetour(Date dateEmprunt){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateEmprunt);
        calendar.add(Calendar.DAY_OF_MONTH, 14);
        return calendar.getTime();
    }

}
