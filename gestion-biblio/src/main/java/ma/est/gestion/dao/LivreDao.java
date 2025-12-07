package ma.est.gestion.dao;

import ma.est.gestion.model.Livre;
import java.util.List;

public interface LivreDao {

    Livre findByCode(String code);  // On cherche un livre par son ISBN

    List<Livre> findAll();          // Lister tous les livres

    void save(Livre livre);      // Enregistrer un nouveau livre

    void update(Livre livre);

    void delete(String code);
             
}
