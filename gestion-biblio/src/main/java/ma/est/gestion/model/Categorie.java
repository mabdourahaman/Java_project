package ma.est.gestion.model;

public class Categorie {
	private String nomCategorie;
	
	public Categorie(String nomCategorie){
		this.nomCategorie = nomCategorie;
	}
	
	public String getCategorie() {return nomCategorie;}
	public void setCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}
}
