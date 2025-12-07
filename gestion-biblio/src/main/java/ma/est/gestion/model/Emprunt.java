package ma.est.gestion.model;

import java.util.Date;

public class Emprunt {

    @SuppressWarnings("FieldMayBeFinal")
    private String codeEmprunt;
    private Date dateEmprunt;
    private Date dateRetour;
    private String statut; 
    private final String livreEmprunt;
    private Livre livre;


    

    public Emprunt(Date dateEmprunt, Date dateRetour, String statut) {

        this.codeEmprunt = livre.getCode() + "-" + dateEmprunt.getTime();
        this.livreEmprunt = livre.getTitre();
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
        this.statut = statut;

    }

    public String getcodeEmprunt() {
        return codeEmprunt;
    }

    public String getLivreEmprunt() {
        return livreEmprunt;
    }

    public Date getDateEmprunt(){
        return dateEmprunt;
    }

    public Date getDateRetour(){
        return dateRetour;
    }

    public String getStatut(){
        return statut;

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
