package Views;

import Controllers.HouseController;
import Controllers.WandController;
import Controllers.WizardController;
import Models.House;
import Models.Wand;
import Models.Wizard;
import Models.WizardListDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.sql.SQLException;

public class WizardViewSwing extends JFrame {
        private WizardController controllerWI;
        private WandController controllerWD;
        private HouseController controllerHO;
        private JTable table;
        private DefaultTableModel model;
        private JTextField txtName, txtAge, txtId,txtHouse,txtWood,txtCore,txtLength;

        private List<Wand> wandList;
        private List<Wizard> wizardList;
        private List<House> houseList;

        //indices
        int indexHouse;
        int indexWand;


        public WizardViewSwing() throws SQLException {
            controllerWI = new WizardController();
            controllerHO = new HouseController();
            controllerWD = new WandController();
            setTitle("🏰 Hogwarts - Gestión de Magos");
            setSize(1000, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            //Panel principal
            JPanel panel = new JPanel(new BorderLayout(5,5));

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

            //boton edit dialog con opciones
            btnEdit.addActionListener(e -> {
                funcionEditar();
            });


            btnAdd.addActionListener(e-> {
                String name = txtName.getText();
                int age = Integer.parseInt(txtAge.getText());
                controllerWI.addWizardSwing(name,age);
                loadData();
            });

            btnDelete.addActionListener(e->{
                int id = Integer.parseInt(txtId.getText());
                controllerWI.deleteWizard(id);
                loadData();
            });

            btnRefresh.addActionListener(e->{
               loadData();
            });


            loadData();

        }

    private void loadData() {
        model.setRowCount(0);
        //PRUEBAS
        wizardList= controllerWI.ListWizardsJSwing();
        houseList= controllerHO.ListHouseJSwing();
        wandList= controllerWD.ListWandJSwing();

       /* CON WizzadListDTO creamos un objeto con todos los datos que quiero mostrar de la base de datos
        llamamos a listWizardDataForTable() del WizzardDAO, para la carga de datos. y en el WizzardDAO creamos una lista
        de objetos con la consulta a la base de datos con LEFTJOIN */

        List<WizardListDTO> datos = controllerWI.listWizardDataForTable();

        if(datos.isEmpty()){
            System.out.println("❌ No hay magos");
        }else{
            for (WizardListDTO w : datos) {
                model.addRow(new Object[]{w.getId(), w.getName(), w.getAge(),w.getHouseName(),w.getWandWood(),w.getWandCore(),w.getWandLength()});
            }
        }
    }

//editar campo tabla
    public void funcionEditar(){
        int fila = table.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Selecciona un mago para editar");
            return;
        }
        JPanel panelDialogo = new JPanel(new GridLayout(0, 2, 5, 5));

        // Precargar valores del mago seleccionado
        int id = Integer.parseInt(model.getValueAt(fila, 0).toString());
        txtName = new JTextField(model.getValueAt(fila, 1).toString(), 10);
        txtAge = new JTextField(model.getValueAt(fila, 2).toString(), 5);

        // --- Campos editables del mago ---
        panelDialogo.add(new JLabel("ID:")); panelDialogo.add(new JLabel(model.getValueAt(fila, 0).toString(), 10));
        panelDialogo.add(new JLabel("Nombre:")); panelDialogo.add(txtName);
        panelDialogo.add(new JLabel("Edad:")); panelDialogo.add(txtAge);

        // Crear combo con nombres de casas
        JComboBox<String> comboHouse = new JComboBox<>();
        // guardamos la lista
        for (House h : houseList) {
            comboHouse.addItem(h.getName());
        }

        // Seleccionar la casa actual del mago
        String currentHouse = model.getValueAt(fila, 3).toString();
        comboHouse.setSelectedItem(currentHouse);

        panelDialogo.add(new JLabel("Casa:"));
        panelDialogo.add(comboHouse);

        // Panel para mostrar el fundador
        JLabel lblFounder = new JLabel();
        panelDialogo.add(new JLabel("Fundador:"));
        panelDialogo.add(lblFounder);

        // Inicializar fundador con la casa actual
            //guardar id de la casa
        House selectedHouse = houseList.stream()
                .filter(h -> h.getName().equals(currentHouse))
                .findFirst()
                .orElse(null);
        indexHouse = selectedHouse.getId();
        if (selectedHouse != null) {
            lblFounder.setText(selectedHouse.getFounder());
        }

        // Listener para actualizar fundador al cambiar de casa
        comboHouse.addActionListener(ev -> {
            String selectedName = (String) comboHouse.getSelectedItem();
            House house = houseList.stream()
                    .filter(h -> h.getName().equals(selectedName))
                    .findFirst()
                    .orElse(null);

            indexHouse=selectedHouse.getId();
            if (house != null) {
                lblFounder.setText(house.getFounder());
            }
        });

        // --- Campos de varita --- SI ES NULL, carga vista vacia para completar
        try {
            txtWood = new JTextField(model.getValueAt(fila, 4).toString(), 10);
            txtCore = new JTextField(model.getValueAt(fila, 5).toString(), 10);
            txtLength = new JTextField(model.getValueAt(fila, 6).toString(), 5);
        }catch (Exception e){
            System.out.println("no hay campos");
            txtWood = new JTextField();
            txtCore = new JTextField();
            txtLength = new JTextField();
        }

        panelDialogo.add(new JLabel("Madera-Varita:"));
        panelDialogo.add(txtWood);
        panelDialogo.add(new JLabel("Núcleo-Varita:"));
        panelDialogo.add(txtCore);
        panelDialogo.add(new JLabel("Longitud:"));
        panelDialogo.add(txtLength);

        // Mostrar diálogo
        int resultado = JOptionPane.showConfirmDialog(
                this,
                panelDialogo,
                "🧙 Editar mago",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String name = txtName.getText().trim();
                int age = Integer.parseInt(txtAge.getText().trim());

                String wood = txtWood.getText().trim();
                String core = txtCore.getText().trim();
                double length = Double.parseDouble(txtLength.getText().trim());

                // --- Si la varita no existe, crearla ---
                        boolean exists = wandList.stream()
                                .anyMatch(w -> w.getWood().equalsIgnoreCase(wood)
                                        && w.getCore().equalsIgnoreCase(core)
                                        && w.getLength() == length);

                       if (!exists) {
                            controllerWD.addWand(wood,core,length);
                        }

                //indexWand= controllerWD.obtenerIDWand();
                // --- Actualizar mago --- usando id
                //si hay casa pero no barita
                controllerWI.updateWizard(id, name, age,indexHouse);
                //TODO si hay barita pero no casa

                //TODO si solo hay mago

                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error al procesar los datos: " + ex.getMessage());
            }
        }
    }




}
