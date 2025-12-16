package ma.est.gestion.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ma.est.gestion.model.Emprunt;

public class EmpruntPanel extends JFrame {

        private JPanel pan;
        private JButton modifier, supprimer;
        private JTable table;
        private final DefaultTableModel tableModel;

        public EmpruntPanel(){
            super("Liste des emprunts");

            tableModel = new DefaultTableModel(new Object[]{"Code Emprunt", "Num Adherent", "Date Emprunt", "Date Retour", "Status", "Code Livre"}, 0);

            // -------- View --------
            table = new JTable(tableModel);
            modifier = new JButton("Modifier"); supprimer = new JButton("Supprimer");
        
            pan = new JPanel(); pan.setLayout(new FlowLayout());
            pan.add(modifier); pan.add(supprimer);

            setLayout(new BorderLayout());
            add(pan, BorderLayout.NORTH);
            add(new JScrollPane(table), BorderLayout.CENTER);
            setTitle("Liste des emprunts");
            pack();
            setSize(750, 650);
            setLocationRelativeTo(null);
            setVisible(true);
        }

    public void refreshTable(List<Emprunt> liste) {
        tableModel.setRowCount(0);
        for (Emprunt e : liste) {
            tableModel.addRow(new Object[]{e.getCodeEmprunt(), e.getNumAdherent(), e.getDateEmprunt(), e.getDateRetour(), e.getStatut(), e.getCodeLivre()});
        }
    }


        public JButton getModifier(){
            return modifier;
        }

        public JButton getSupprimer(){
            return supprimer;
        }

        public JTable getTable(){
            return table;
        }

        public DefaultTableModel getTableModel(){
            return tableModel;
        }

}