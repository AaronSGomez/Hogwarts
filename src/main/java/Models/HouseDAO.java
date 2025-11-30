package Models;

import Database.DBConnection;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HouseDAO {
        private Connection conn;

    public HouseDAO() {
        try {
            conn= DBConnection.getConnection();
            System.out.println("☑️☑️☑️☑️️☑️️Conectado a tabla Houses");
        } catch (SQLException e) {
            System.err.println("❌❌❌❌❌Error de conexion: " + e.getMessage());
        }
    }

    //CRUD  - CREATE | READ | UPDATE | DELETE
    public void create(House house)throws SQLException {
        String sql= "INSERT INTO house (name, founder) VALUES (?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, house.getName());
        ps.setString(2, house.getFounder());
        ps.executeUpdate();
        ps.close();
    }

    public List<House> getAll() throws SQLException{
        List<House> list = new ArrayList<>();

        String sql = "SELECT * FROM house";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()){
            list.add(
                    new House((rs.getInt("id")),(rs.getString("name")),(rs.getString("founder"))));
        }//endwhile
        rs.close();
        return list;
    }

    public void update(House house) throws SQLException {
        String sql = "update house set name=?, founder=? where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, house.getName());
        ps.setString(2, house.getFounder());
        ps.setInt(3, house.getId());
        ps.executeUpdate();
        ps.close();

    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM house WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();

    }


}
