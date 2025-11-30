package Models;

import Database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WizardDAO {

    private Connection conn;

    public WizardDAO() {
        try {
            conn= DBConnection.getConnection();
            System.out.println("☑️☑️☑️☑️️☑️️Conectado a tabla Wizards");
        } catch (SQLException e) {
            System.err.println("❌❌❌❌❌Error de conexion: " + e.getMessage());
        }
    }


    //CRUD  - CREATE | READ | UPDATE | DELETE
    public void create(Wizard wizard)throws SQLException {
        String sql= "INSERT INTO wizard (name, age, house_id, wand_id) VALUES (?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, wizard.getName());
        ps.setInt(2, wizard.getAge());
        ps.setInt(3, wizard.getHouseId());
        ps.setInt(4,wizard.getWandId());
        ps.executeUpdate();
        ps.close();
    }

    public void createSwing(Wizard wizard)throws SQLException {
        String sql= "INSERT INTO wizard (name, age) VALUES (?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, wizard.getName());
        ps.setInt(2, wizard.getAge());
        ps.executeUpdate();
        ps.close();
    }


    public List<Wizard> getAll() throws SQLException{
        List<Wizard> list = new ArrayList<>();

        String sql = "SELECT * FROM wizard";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()){
            list.add(
                    new Wizard(
                            (rs.getInt("id")),
                            (rs.getString("name")),
                            (rs.getInt("age")),
                            (rs.getInt("house_id")),
                            (rs.getInt("wand_id"))
                    ));
        }//endwhile
        rs.close();
        return list;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM wizard WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();

    }

    public void update(Wizard wizard) throws SQLException {
        String sql = "update wizard set name=?, age=?, house_id=?, wand_id=? where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, wizard.getName());
        ps.setInt(2, wizard.getAge());
        ps.setInt(3, wizard.getHouseId());
        ps.setInt(4, wizard.getWandId());
        ps.setInt(5, wizard.getId());
        ps.executeUpdate();
        ps.close();

    }


    public List<WizardListDTO> listAllWizardsWithDetails() throws SQLException{
        List<WizardListDTO> listDatos = new ArrayList<>();

        String sql = "SELECT w.id, w.name, w.age, h.name AS houseName, w2.wood AS wandWood, w2.core AS wandCore," +
                "w2.`length` AS wandLength \n" +
                "FROM wizard w \n" +
                "LEFT JOIN house h ON w.house_id  = h.id \n" +
                "LEFT JOIN wand w2 ON w2.id = w.wand_id ";

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()){
            listDatos.add(new WizardListDTO(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("houseName"),
                    rs.getString("wandWood"),
                    rs.getString("wandCore"),
                    rs.getDouble("wandLength")
            ));
        }//endwhile
        rs.close();
        return listDatos;
    }





}
