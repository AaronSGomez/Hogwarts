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

        //Indices, integer permite null
        Integer indexHouse;
        Integer indexWand;


        public WizardViewSwing() throws SQLException {

            this.indexWand=null;
            this.indexHouse=null;

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

            //boton EDIT Y ADD dialog con opciones
            btnEdit.addActionListener(e -> {
                funcionEditar();
            });

            btnAdd.addActionListener(e-> {
                funcionAdd();
            });

            //PARA BORRAR SOLO NECESITAMOS EL ID
            btnDelete.addActionListener(e->{
                int id = Integer.parseInt(txtId.getText());
                controllerWI.deleteWizard(id);
                loadData();
            });

            //LOAD DATA
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

//AÑADIR
    public void funcionAdd() {
        JPanel panelDialogo = new JPanel(new GridLayout(0, 2, 5, 5));
        txtWood = new JTextField();
        txtCore = new JTextField();
        txtLength = new JTextField();
        txtName = new JTextField();
        txtAge = new JTextField();

        //Campos mago (name, age) el id se genera en la bd, no lo incluimos
        panelDialogo.add(new JLabel("Nombre:"));
        panelDialogo.add(txtName);
        panelDialogo.add(new JLabel("Edad:"));
        panelDialogo.add(txtAge);

        // Crear combo con nombres de casas
        JComboBox<String> comboHouse = new JComboBox<>();
        // guardamos la lista
        for (House h : houseList) {
            comboHouse.addItem(h.getName());
        }
        panelDialogo.add(new JLabel("House:"));
        panelDialogo.add(comboHouse);

        // Panel para mostrar el fundador
        JLabel lblFounder = new JLabel();
        panelDialogo.add(new JLabel("Founder:"));
        panelDialogo.add(lblFounder);

            // el alumno no tiene casa (null) asigno la 50 creada NO HOUSE / NO FOUNDER por defecto
            House casaDefault = houseList.stream()
                    .filter(h -> h.getId() == 50)
                    .findFirst()
                    .orElse(null);
            if (casaDefault != null) {
                // Visualmente seleccionamos "NO HOUSE"
                comboHouse.setSelectedItem(casaDefault.getName());
                // Asignamos el ID 50 para guardar
                indexHouse = casaDefault.getId();
                lblFounder.setText(casaDefault.getFounder()); // Pondrá "NO FOUNDER"
            }

        // Listener para actualizar fundador al cambiar de casa (Tiempo real)
        comboHouse.addActionListener(ev -> {
            String selectedName = (String) comboHouse.getSelectedItem();
            //casa seleccionada ahora de la lista
            House house = houseList.stream()
                    .filter(h -> h.getName().equals(selectedName))
                    .findFirst()
                    .orElse(null);

            if (house != null) {
                indexHouse = house.getId();
                lblFounder.setText(house.getFounder());
            }
        });
        // datos de la nueva varita (Se pueden quedar en blanco)
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
                // 1. LEER DATOS BÁSICOS DEL MAGO
                String name = txtName.getText().trim();
                int age = Integer.parseInt(txtAge.getText().trim());

                // 2. LEER DATOS DE LA VARITA (Texto crudo primero)
                String wood = txtWood.getText().trim();
                String core = txtCore.getText().trim();
                String lengthText = txtLength.getText().trim();
                // CASO A: El usuario dejó los campos VACÍOS varita = null
                if (wood.isEmpty() && core.isEmpty() && lengthText.isEmpty()) {
                    indexWand = null;
                } else {
                    // Validaciones
                    if (wood.isEmpty() || core.isEmpty()) {
                        throw new Exception("⚠️ Para tener varita, faltan Madera o Núcleo");
                    }
                    if (lengthText.isEmpty()) {
                        throw new Exception("⚠️ Falta la longitud de la varita");
                    }

                    // Convertimos el número ahora que sabemos que existe

                    double length = Double.parseDouble(lengthText);
                    indexWand = controllerWD.addWand(wood, core, length); // Obtenemos nuevo ID

                }

                // 5. FINAL - crear mago nuevo
                controllerWI.addWizard(name, age, indexHouse, indexWand);

                loadData(); // Recargar tabla

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: La edad o longitud deben ser números.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error al procesar: " + ex.getMessage());
            }
        }
    }

//EDITAR
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
        Object rowHouse = model.getValueAt(fila, 3);
        String currentHouse= (rowHouse != null) ? rowHouse.toString() : "NO HOUSE"; //controlamos el null

        comboHouse.setSelectedItem(currentHouse);

        panelDialogo.add(new JLabel("House:"));
        panelDialogo.add(comboHouse);

        // Panel para mostrar el fundador
        JLabel lblFounder = new JLabel();
        panelDialogo.add(new JLabel("Founder:"));
        panelDialogo.add(lblFounder);

        // Inicializar fundador con la casa actual
        House selectedHouse = houseList.stream()
                .filter(h -> h.getName().equals(currentHouse))
                .findFirst()
                .orElse(null);

        if (selectedHouse != null) {
            indexHouse = selectedHouse.getId();
            lblFounder.setText(selectedHouse.getFounder());
        }else{
            // el alumno no tiene casa (null) asigno la 50 creada NO HOUSE / NO FOUNDER
            House casaDefault = houseList.stream()
                    .filter(h -> h.getId() == 50)
                    .findFirst()
                    .orElse(null);
            if (casaDefault != null) {
                // Visualmente seleccionamos "NO HOUSE"
                comboHouse.setSelectedItem(casaDefault.getName());
                // Asignamos el ID 50 para guardar
                indexHouse = casaDefault.getId();
                lblFounder.setText(casaDefault.getFounder()); // Pondrá "NO FOUNDER"
            }

        }

        // Listener para actualizar fundador al cambiar de casa
        comboHouse.addActionListener(ev -> {
            String selectedName = (String) comboHouse.getSelectedItem();
            //casa seleccionada ahora de la lista
            House house = houseList.stream()
                    .filter(h -> h.getName().equals(selectedName))
                    .findFirst()
                    .orElse(null);

            if (house != null) {
                indexHouse = house.getId();
                lblFounder.setText(house.getFounder());
            }
        });

        // --- Campos de varita --- SI ES NULL, carga vista vacia para completar
        try {
            txtWood = new JTextField(model.getValueAt(fila, 4).toString(), 10);
            txtCore = new JTextField(model.getValueAt(fila, 5).toString(), 10);
            txtLength = new JTextField(model.getValueAt(fila, 6).toString(), 5);
        }catch (Exception e){
            System.out.println("no hay campos  -wand: " + indexWand +"  -house: "+ indexHouse);
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

        //EXPLICACIONES
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                // 1. LEER DATOS DEL MAGO
                String name = txtName.getText().trim();
                int age = Integer.parseInt(txtAge.getText().trim());

                // 2. LEER DATOS DE LA VARITA
                String wood = txtWood.getText().trim();
                String core = txtCore.getText().trim();
                String lengthText = txtLength.getText().trim();

                // 3. RECUPERAR ESTADO ORIGINAL (editamos un mago que puede tener casa y varita)
                // Buscamos si este mago ya tenía varita antes de empezar //esto me lo enseño Copilot
                Wizard magoOriginal = wizardList.stream()
                        .filter(m -> m.getId() == id)
                        .findFirst()
                        .orElse(null);

                Integer idVaritaOriginal = null;  //creo variable por si no existe varita dejarlo en null (Integer acepta null, int no)
                if (magoOriginal != null && magoOriginal.getWandId() != null) {
                    idVaritaOriginal = magoOriginal.getWandId(); // Guardamos el ID de su varita
                }

                // 4. LÓGICA DE VARITA
                // CASO A: El usuario dejó los campos VACÍOS o Quiere quitar la varita (quitamos, no elimino)
                if (wood.isEmpty() && core.isEmpty() && lengthText.isEmpty()) {
                    indexWand = null; // Marcamos que el mago se quedará sin varita (NULL)
                }
                else {
                    // CASO B: queremos poner/actualizar varita
                    // Validaciones
                    if (wood.isEmpty() && core.isEmpty()) {
                        throw new Exception("⚠️ Para tener varita, faltan Madera y Núcleo ⚠️");
                    }
                    if (lengthText.isEmpty()) {
                        throw new Exception("⚠️ Falta la longitud de la varita ⚠️");
                    }

                    // Convertimos el número ahora que sabemos que existe
                    double length = Double.parseDouble(lengthText);

                    // Ya tenía varita -> La actualizamos (UPDATE wand)
                    if (idVaritaOriginal != null) {
                        controllerWD.updateWand(idVaritaOriginal, wood, core, length);
                        indexWand = idVaritaOriginal; // Mantenemos el mismo ID
                    }
                    // No tenía varita -> Creamos una nueva (INSERT wand)
                    else {
                        indexWand = controllerWD.addWand(wood, core, length); // Obtenemos nuevo ID para poder añadir a mago
                    }
                }

                // FINAL - Actualizar mago con el indexWand calculado (sea null o un número)
                controllerWI.updateWizard(id, name, age, indexHouse, indexWand);

                loadData(); // Recargar tabla para mostrar los resultados en tiempo real

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: La edad o longitud deben ser números.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error al procesar: " + ex.getMessage());
            }
        }
    }




}
