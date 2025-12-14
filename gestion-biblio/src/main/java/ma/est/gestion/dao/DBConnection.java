package ma.est.gestion.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;

    private final String URL = "jdbc:mysql://localhost:3306/gestion_biblio";
    private final String USER = "root";
    private final String PASSWORD = "password";

    private DBConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion à la base de données réussie.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        if(instance == null) {
            instance =  new DBConnection();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
