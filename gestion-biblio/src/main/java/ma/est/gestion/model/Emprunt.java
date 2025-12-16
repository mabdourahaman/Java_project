package ma.est.gestion.model;

import java.util.Calendar;
import java.util.Date;

public class Emprunt {

    private String codeEmprunt;
    private int numAdherent;
    private Date dateEmprunt;
    private Date dateRetour;
    private String statut = "Actif";
    private Livre livre;
    private Adherent adherent;
    private String codeLivre;

    /*
        Creee un Emprunt a partir d'un Livre et d'un Adherent donnes. Le constructeur
        remplira automatiquement les informations id/titre/adherent.
     */

    public Emprunt(Livre livre, Adherent adherent) {
        if (livre == null) throw new IllegalArgumentException("livre ne peut pas être null");
        if (adherent == null) throw new IllegalArgumentException("adherent ne peut pas être null");
        if (livre.getNombreExemplaire() <= 0 ) throw new IllegalArgumentException("Le livre n'a pas d'exemplaire disponible");

        this.livre = livre;
        this.adherent = adherent;
        this.dateEmprunt = new Date(); // Ajout de la date courante

        // Ajout de 14 jours a la date d'emprunt pour definir la date de retour

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.dateEmprunt);
        calendar.add(Calendar.DAY_OF_MONTH, 14);
        this.dateRetour = calendar.getTime();
        this.statut = "Actif";

        // Construire un codeEmprunt unique

        long time = this.dateEmprunt.getTime();
        this.codeEmprunt = livre.getCode() + "-" + time;

        // Enrichir les informations de l'adherent et du livre

        this.numAdherent = adherent.getNumAdherent();
        this.codeLivre = livre.getCode();

    }

    public Emprunt() {

    }


    // Getters

    public int getNumAdherent() { return numAdherent; }

    public String getCodeEmprunt() { return codeEmprunt; }

    public Date getDateEmprunt(){ return dateEmprunt; }

    public Date getDateRetour(){ return dateRetour; }

    public String getStatut(){ return statut;}

    public Livre getLivre() { return livre; }

    public Adherent getAdherent() { return adherent; }

    public String getCodeLivre() { return codeLivre; }


    // Setters
      
    public void setDateEmprunt(Date dateEmprunt){ this.dateEmprunt = dateEmprunt; }

    public void setDateRetour(Date dateRetour){ this.dateRetour = dateRetour; }

    public void setStatut(String statut){ this.statut = statut; }

    public void setNumAdherent(int numAdherent) { this.numAdherent = numAdherent; }

    public void setCodeEmprunt(String codeEmprunt) { this.codeEmprunt = codeEmprunt; }

    public void setLivre(Livre livre) { this.livre = livre; }

    public void setAdherent(Adherent adherent) { this.adherent = adherent; }

    public void setCodeLivre(String codeLivre) { this.codeLivre = codeLivre; }


}