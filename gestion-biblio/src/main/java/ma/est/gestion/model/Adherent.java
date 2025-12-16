package ma.est.gestion.model;

public class Adherent {
    private final int NumAdherent;
    private  String NomAdherent;
    private  String PrenomAdherent;
    private  String EmailAdherent ;

    public Adherent( int  NumAdherent, String EmailAdherent, String NomAdherent, String PrenomAdherent){
        this.EmailAdherent = EmailAdherent;
        this.NumAdherent = NumAdherent ;
        this.NomAdherent = NomAdherent ;
        this.PrenomAdherent = PrenomAdherent ;

    }
        public int getNumAdherent() {
        return NumAdherent;
    }

    public String getNomAdherent() {
        return NomAdherent;
    }

    public String getPrenomAdherent() {
        return PrenomAdherent;
    }

    public String getEmailAdherent() {
        return EmailAdherent;
    }
    
}