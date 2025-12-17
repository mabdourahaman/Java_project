package ma.est.gestion.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ma.est.gestion.model.Emprunt;

public class EmpruntPanel extends JFrame {

        private JPanel pan, pantxt;
        private JButton modifier, supprimer;
        private JTable table;
        private final DefaultTableModel tableModel;
        private JTextField txtCodeEmprunt, txtNumAdherent, txtDateEmprunt, txtDateRetour, txtStatut, txtCodeLivre;

        public EmpruntPanel(){

            super("Liste des emprunts");

            tableModel = new DefaultTableModel(new Object[]{"Code Emprunt", "Num Adherent", "Date Emprunt", "Date Retour", "Status", "Code Livre"}, 0);

            // -------- View --------
            table = new JTable(tableModel);
            modifier = new JButton("Modifier"); supprimer = new JButton("Supprimer");

            txtCodeEmprunt = new JTextField();  txtNumAdherent = new JTextField();  txtDateEmprunt = new JTextField();
            txtDateRetour = new JTextField();  txtStatut = new JTextField();  txtCodeLivre = new JTextField();

            pan = new JPanel(); pan.setLayout(new FlowLayout()); pan.add(modifier); pan.add(supprimer);
            pantxt = new JPanel(); pantxt.setLayout(new FlowLayout());

            pantxt.add(txtCodeEmprunt); pantxt.add(txtNumAdherent); pantxt.add(txtDateEmprunt);
            pantxt.add(txtDateRetour); pantxt.add(txtStatut); pantxt.add(txtCodeLivre);
            

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

}
