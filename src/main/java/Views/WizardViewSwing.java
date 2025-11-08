package Views;

import Controllers.WizardController;
import Models.Wizard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;

public class WizardViewSwing extends JFrame {
        private WizardController controller;
        private JTable table;
        private DefaultTableModel model;
        private JTextField txtName, txtAge, txtId;

        public WizardViewSwing() throws SQLException {
            controller = new WizardController();
            setTitle("🏰 Hogwarts - Gestión de Magos");
            setSize(600, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            //Panel principal
            JPanel panel = new JPanel(new BorderLayout());
            add(panel);

            // Tabla
            model = new DefaultTableModel(new String[]{"ID", "Nombre", "Edad"}, 0);
            table = new JTable(model);
            panel.add(new JScrollPane(table));

            JButton btnAdd = new JButton("Agregar ➕️");
            JButton btnDelete = new JButton("Eliminar ❌");
            JButton btnRefresh = new JButton("Actualizar 🌀");

            JPanel inputPanel = new JPanel();
            inputPanel.add(btnAdd);
            inputPanel.add(btnDelete);
            inputPanel.add(btnRefresh);

            panel.add(inputPanel, BorderLayout.SOUTH);

            inputPanel.add(new JLabel("ID:"));
            txtId = new JTextField(5);
            inputPanel.add(txtId);
            inputPanel.add(new JLabel("Nombre:"));
            txtName = new JTextField(10);
            inputPanel.add(txtName);
            inputPanel.add(new JLabel("Edad:"));
            txtAge = new JTextField(5);
            inputPanel.add(txtAge);


            loadData();

        }

    private void loadData() {
        model.setRowCount(0);
        List<Wizard> wizards= controller.ListWizardsJSwing();
        if(wizards.isEmpty()){
            System.out.println("❌ No hay magos");
        }else{
            for (Wizard w : wizards) {
                model.addRow(new Object[]{w.getId(), w.getName(), w.getAge()});
            }
        }
    }


}
