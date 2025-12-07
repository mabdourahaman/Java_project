package ma.est.gestion.model;

public class Adherent {


    private final int NumAdherent;
    private final String NomAdherent;
    private final String PrenomAdherent;
    private final String EmailAdherent;


    public Adherent(int NumAdherent, String NomAdherent, String PrenomAdherent, String EmailAdherent) {
        this.NumAdherent = NumAdherent;
        this.NomAdherent = NomAdherent;
        this.PrenomAdherent = PrenomAdherent;
        this.EmailAdherent = EmailAdherent;
    }

    public int getNumAdherent() { return NumAdherent;}

    public String getNomAdherent() { return NomAdherent; }

    public String getPrenomAdherent() { return PrenomAdherent; }
    
    public String getEmailAdherent() { return EmailAdherent; }

    public String getFullName() {
        return this.PrenomAdherent + " " + this.NomAdherent;
    }

    @Override
    public String toString() {
        return "Adherent{" +
                "NumAdherent=" + NumAdherent +
                ", NomAdherent='" + NomAdherent + '\'' +
                ", PrenomAdherent='" + PrenomAdherent + '\'' +
                ", EmailAdherent='" + EmailAdherent + '\'' +
                '}';
    }
}
