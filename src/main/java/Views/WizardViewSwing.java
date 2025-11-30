package Views;

import Controllers.WizardController;
import Models.Wizard;
import Models.WizardListDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.sql.SQLException;

public class WizardViewSwing extends JFrame {
        private WizardController controller;
        private JTable table;
        private DefaultTableModel model;
        private JTextField txtName, txtAge, txtId,txtHouse,txtWood,txtCore,txtLength;

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
            model = new DefaultTableModel(new String[]{"ID", "Name", "Age","Magic-House","Wand-Wood","Wand-Core","Wand-Length"}, 0);
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
            inputPanel.add(new JLabel("Madera-Varita:"));
            txtWood = new JTextField(5);
            inputPanel.add(txtWood);
            inputPanel.add(new JLabel("Nucleo-Varita:"));
            txtCore = new JTextField(5);
            inputPanel.add(txtCore);
            inputPanel.add(new JLabel("Longitud:"));
            txtLength = new JTextField(5);
            inputPanel.add(txtLength);

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
        /* PRUEBAS
        List <Wizard> wizards= controller.ListWizardsJSwing();
        List <House> houses= controller.ListHouseJSwing();
        List <Wand> wands= controller.listWandJSwing();*/

       /* CON WizzadListDTO creamos un objeto con todos los datos que quiero mostrar de la base de datos
        llamamos a listWizardDataForTable() del WizzardDAO, para la carga de datos. y en el WizzardDAO creamos una lista
        de objetos con la consulta a la base de datos con LEFTJOIN */

        List<WizardListDTO> wizards = controller.listWizardDataForTable();

        if(wizards.isEmpty()){
            System.out.println("❌ No hay magos");
        }else{
            for (WizardListDTO w : wizards) {
                model.addRow(new Object[]{w.getId(), w.getName(), w.getAge(),w.getHouseName(),w.getWandWood(),w.getWandCore(),w.getWandLength()});
            }
        }
    }





}
