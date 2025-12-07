package ma.est.gestion.model;

public class Livre {
	private String code;
	private String titre;
	private String auteur;
	private int nombreExemplaire;
	private Categorie categorie;
	
	 // Constructeur
	public Livre(String code, String titre, String auteur, int nombreExemplaire, Categorie categorie){
		this.code = code;
		this.titre = titre;
		this.auteur = auteur;
		this.nombreExemplaire = nombreExemplaire;
		this.categorie = categorie;
	}
	
    // Getters et Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public int getNombreExemplaire() { return nombreExemplaire; }
    public void setNombreExemplaire(int nombreExemplaire) { this.nombreExemplaire = nombreExemplaire; }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
	
    public void decrementerExemplaire() {
      	if (nombreExemplaire <= 0) {
    		    throw new IllegalStateException("Aucun exemplaire disponible!");
    	    }
      	else { this.nombreExemplaire--;}
    }
    
    public void incrementerExemplaire() {
    	    this.nombreExemplaire++;
    }
    
    //{un livre ne peut être supprimé que s'il n'a aucun exemplaire en prêt}
    public boolean supprimer() {
    	   return this.nombreExemplaire > 0;
    }
}