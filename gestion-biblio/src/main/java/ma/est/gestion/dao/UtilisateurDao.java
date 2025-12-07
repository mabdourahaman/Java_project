package ma.est.gestion.dao;

import java.util.List;

import ma.est.gestion.model.Utilisateur;

public interface UtilisateurDao {

	void ajouter(Utilisateur utilisateur);

	void modifier(Utilisateur utilisateur);

	void supprimer(int id);

	Utilisateur getById(int id);

	List<Utilisateur> getAll();
}

