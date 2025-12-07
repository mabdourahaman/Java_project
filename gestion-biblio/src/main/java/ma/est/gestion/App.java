package ma.est.gestion;

import ma.est.gestion.controller.LivreController;
import ma.est.gestion.dao.impl.LivreDaoImpl;
import ma.est.gestion.view.LivrePanel;

import javax.swing.*;

public class App {
    public static void main(String[] args) {

        // DAO
        LivreDaoImpl dao = new LivreDaoImpl();

        // Controller créé d'abord (sans vue)
        LivreController controller = new LivreController(null, dao);

        // Vue créée ensuite (elle prend le controller)
        LivrePanel view = new LivrePanel(controller);

        // On lie la vue au controller
        controller.setView(view);

        // Affichage
        JFrame frame = new JFrame("Gestion de Bibliothèque Universitaire");
        frame.setContentPane(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
