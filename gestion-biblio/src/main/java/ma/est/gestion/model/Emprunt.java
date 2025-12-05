package ma.est.gestion.model;

import java.util.Date;

public class Emprunt {

    private Date dateEmprunt;
    private Date dateRetour;
    private String statut; 

    public Emprunt(Date dateEmprunt, Date dateRetour, String statut) {

        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
        this.statut = statut;

    }

    public Date getDateEmprunt(){
        return this.dateEmprunt;
    }

    public Date getDateRetour(){
        return this.dateRetour;
    }

    public String getStatut(){
        return this.statut;

    }
      
    public void setDateEmprunt(Date dateEmprunt){
        this.dateEmprunt = dateEmprunt;
    }

    public void setDateRetour(Date dateRetour){
        this.dateRetour = dateRetour;
    }

    public void setStatut(String statut){
        this.statut = statut;
    }
    
}
