package ma.est.gestion.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	//Instance statique 
	
	private static DatabaseConnection instance;

	
	// Objet Connection

	private Connection connection;

	private final String URL = "jdbc:mysql://localhost:3306/bibliotheque";
	private final String USER = "root";
	private final String PASSWORD = "Your_password";


    @SuppressWarnings("CallToPrintStackTrace")
	private DatabaseConnection() {

		try{
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println(" Connection MySQL reussie ");
		} catch (SQLException e ) {
			e.printStackTrace();
		}
		
	}

	public static DatabaseConnection getInstance() {

		if (instance == null){
			instance = new DatabaseConnection();
		}
		return instance;
	}


	public Connection getConnection() {
		return connection;
		
	}
}
