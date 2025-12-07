package ma.est.gestion.model;


public class Utilisateur {

		private int id;
		private String nom;
		private String email;
		private String password;

		public Utilisateur() {}

		public Utilisateur(int id, String nom, String email, String password) {
			this.id = id;
			this.nom = nom;
			this.email = email;
			this.password = password;
		}
		public Utilisateur(String nom, String email, String password) {
			this.nom = nom;
			this.email = email;
			this.password = password;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}


		public String toString() {
			return "Utilisateur{" +
					"id=" + id +
					", nom='" + nom + '\'' +
					", email='" + email + '\'' +
					", password='" + password + '\'' +
					'}';
		}
}

