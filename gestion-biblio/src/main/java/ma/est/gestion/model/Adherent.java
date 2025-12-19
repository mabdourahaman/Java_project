package ma.est.gestion.model;

public class Adherent {
    private  int NumAdherent;
    private  String NomAdherent;
    private  String PrenomAdherent;
    private  String EmailAdherent ;

    public Adherent() {
    }

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
    
    public void setNomAdherent(String nomAdherent) {
        this.NomAdherent = nomAdherent;
    }
    public void setPrenomAdherent(String prenomAdherent) {
        this.PrenomAdherent = prenomAdherent;
    }
    public void setEmailAdherent(String emailAdherent) {
        this.EmailAdherent = emailAdherent;
    }
    public void setNumAdherent(int numAdherent) {
        this.NumAdherent = numAdherent;
    }
}


