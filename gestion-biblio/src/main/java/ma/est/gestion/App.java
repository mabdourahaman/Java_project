package ma.est.gestion;

import javax.swing.SwingUtilities;

import ma.est.gestion.controller.EmpruntController;
import ma.est.gestion.dao.impl.EmpruntDaoImpl;
import ma.est.gestion.view.EmpruntPanel;

public class App {


    public static void main(String[] args) {

        // Exécution de la calsse Adherent panel

       /* 
        AdherentPanel view = new AdherentPanel();
        AdherentDao dao = new AdherentDao();

        new AdherentController(dao, view);
        view.setVisible(true);
*/

        
        
        EmpruntDaoImpl dao = new EmpruntDaoImpl();
        EmpruntPanel view = new EmpruntPanel();
        new EmpruntController(dao, view);
        view.setVisible(true); 
             
        SwingUtilities.invokeLater(() -> new App());
        /*
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
         */
        // DAO

    }
}
