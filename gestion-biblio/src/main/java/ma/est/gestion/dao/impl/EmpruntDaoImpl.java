package ma.est.gestion.dao.impl;

import java.util.*;

import ma.est.gestion.model.Emprunt;

public class EmpruntDaoImpl {

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

    public List<Emprunt> getAllEmprunts(){
        return emprunts;
    }

    public void updateEmprunt(Emprunt e, String newStatut){
        for (Emprunt existing : emprunts){
            if (existing.getDateEmprunt().equals(e.getDateEmprunt())){
                existing.setStatut(newStatut);
                System.out.println("Emprunt mis a jour: " + newStatut);
                return;
            }
        }
        throw new NoSuchElementException("Emprunt non trouve pour mise a jour");
    }

    public void deleteEmprunt(Emprunt e){
        Iterator<Emprunt> iterator = emprunts.iterator();
        while (iterator.hasNext()){
            Emprunt existing = iterator.next();
            if (existing.getDateEmprunt().equals(e.getDateEmprunt())){
                iterator.remove();
                System.out.println("Emprunt supprime");
                return;
            }
        }
        throw new NoSuchElementException("Emprunt non trouve pour suppression");
    }

    public Emprunt findEmpruntByDate(Date dateEmprunt){
        for (Emprunt existing : emprunts){
            if (existing.getDateEmprunt().equals(dateEmprunt)){
                return existing;
            }
        }
        throw new NoSuchElementException("Emprunt non trouve pour la date donnee");
    }

    public List<Emprunt> findEmpruntsByStatut(String statut){
        List<Emprunt> result = new ArrayList<>();
        for (Emprunt existing : emprunts){
            if (existing.getStatut().equalsIgnoreCase(statut)){
                result.add(existing);
            }
        }
        return result;
    }

    public void CloturerEmprunt(Emprunt e){
        updateEmprunt(e, "Cloture");
    }

}