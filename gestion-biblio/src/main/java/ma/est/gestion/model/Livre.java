package ma.est.gestion.model;

public class Livre {

    private int id;
    private String code;
    private String titre;
    private String auteur;
    private int nombreExemplaire;
    private Categorie categorie;

    public Livre(int id, String code, String titre,
                 String auteur, int nombreExemplaire,
                 Categorie categorie) {

        if (nombreExemplaire < 1)
            throw new IllegalArgumentException("Exemplaire >= 1");

        this.id = id;
        this.code = code;
        this.titre = titre;
        this.auteur = auteur;
        this.nombreExemplaire = nombreExemplaire;
        this.categorie = categorie;
    }

    public int getId() { return id; }
    public String getCode() { return code; }
    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public int getNombreExemplaire() { return nombreExemplaire; }
    public Categorie getCategorie() { return categorie; }
}
