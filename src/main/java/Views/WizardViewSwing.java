package Views;

import Controllers.WizardController;
import Models.House;
import Models.Wand;
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
        private JTextField txtName, txtAge, txtId,txtHouse,txtFounder,txtWood,txtCore;

        public WizardViewSwing() throws SQLException {
            controller = new WizardController();
            setTitle("🏰 Hogwarts - Gestión de Magos");
            setSize(1000, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            //Panel principal
            JPanel panel = new JPanel(new BorderLayout());
            add(panel);

            // Tabla
            model = new DefaultTableModel(new String[]{"ID", "Nombre", "Edad","Casa","Fundador","Madera-Varita","Nucleo-Varita"}, 0);
            table = new JTable(model);
            panel.add(new JScrollPane(table));

            JButton btnAdd = new JButton("Agregar ➕️");
            JButton btnDelete = new JButton("Eliminar ❌");
            JButton btnEdit = new JButton("Editar 💫");
            JButton btnRefresh = new JButton("Actualizar 🌀");

            JPanel inputPanel = new JPanel();
            inputPanel.add(btnAdd);
            inputPanel.add(btnDelete);
            inputPanel.add(btnEdit);
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
            inputPanel.add(new JLabel("House:"));
            txtHouse = new JTextField(5);
            inputPanel.add(txtHouse);
            inputPanel.add(new JLabel("Fundador:"));
            txtFounder = new JTextField(5);
            inputPanel.add(txtFounder);
            inputPanel.add(new JLabel("Madera-Varita:"));
            txtWood = new JTextField(5);
            inputPanel.add(txtWood);
            inputPanel.add(new JLabel("Nucleo-Varita:"));
            txtCore = new JTextField(5);
            inputPanel.add(txtCore);

            //TODO: TERMINAR EL EDIT.

            btnAdd.addActionListener(e-> {
                String name = txtName.getText();
                int age = Integer.parseInt(txtAge.getText());
                controller.addWizardSwing(name,age);
                loadData();
            });

            btnDelete.addActionListener(e->{
                int id = Integer.parseInt(txtId.getText());
                controller.deleteWizard(id);
                loadData();
            });

            btnRefresh.addActionListener(e->{
               loadData();
            });

            btnEdit.addActionListener(e->{
                int id = Integer.parseInt(txtId.getText());
                String name = txtName.getText();
                int age = Integer.parseInt(txtAge.getText());

            });

            loadData();

        }

    private void loadData() {
        model.setRowCount(0);
        List <Wizard> wizards= controller.ListWizardsJSwing();
       /* List <House> houses= controller.ListHouseJSwing();
        List <Wand> wands= controller.listWandJSwing();*/

        if(wizards.isEmpty()){
            System.out.println("❌ No hay magos");
        }else{
            for (Wizard w : wizards) {
                model.addRow(new Object[]{w.getId(), w.getName(), w.getAge(),w.getHouseId(),w.getWandId()});
            }
        }
    }




}
