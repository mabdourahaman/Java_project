package ma.est.gestion.model;

public class Categorie {

    private String nomCategorie;

    public Categorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    @Override
    public String toString() {
        return nomCategorie;
    }
}
