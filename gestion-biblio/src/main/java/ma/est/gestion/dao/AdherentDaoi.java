package ma.est.gestion.dao;
import java.util.List;
import ma.est.gestion.model.Adherent;

public interface AdherentDaoi {

    public void ajouter(Adherent adherent);
    public void modifier(Adherent adherent);
    public void supprimer(int numAdherent); 
    public Adherent trouverParId(int numAdherent);
    public List<Adherent> getAll();
}
