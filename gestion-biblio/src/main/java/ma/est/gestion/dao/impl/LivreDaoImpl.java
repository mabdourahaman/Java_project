package ma.est.gestion.dao.impl;

import java.util.ArrayList;
import java.util.List;

import ma.est.gestion.dao.LivreDao;
import ma.est.gestion.model.Livre;

public class LivreDaoImpl implements LivreDao {
	private List<Livre> lv = new ArrayList<>();   // Simule une base de données
	
    @Override
    public Livre findByCode(String code) {
        return lv.stream()
                .filter(l -> l.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Livre> findAll() {
        return new ArrayList<>(lv);
    }

    @Override
    public void save(Livre livre) {
    	lv.add(livre);
    }

    @Override
    public void update(Livre livre) {
        delete(livre.getCode());  // On supprime l’ancien
        save(livre);               // On ajoute le nouveau
    }

    @Override
    public void delete(String code) {
    	lv.removeIf(l -> l.getCode().equals(code));
    }
}
