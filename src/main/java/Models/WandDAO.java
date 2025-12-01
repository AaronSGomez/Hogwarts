package Models;

import Database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WandDAO {
    private Connection conn;

    public WandDAO() {
        try {
            conn= DBConnection.getConnection();
            System.out.println("☑️☑️☑️☑️️☑️️Conectado a tabla Wands");
        } catch (SQLException e) {
            System.err.println("❌❌❌❌❌Error de conexion: " + e.getMessage());
        }
    }


    //CRUD  - CREATE | READ | UPDATE | DELETE
    public void create(Wand wand)throws SQLException {
        String sql= "INSERT INTO wand (wood, core, length) VALUES (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, wand.getWood());
        ps.setString(2, wand.getCore());
        ps.setDouble(3, wand.getLength());
        ps.executeUpdate();
        ps.close();
    }

    public List<Wand> getAll() throws SQLException{
        List<Wand> list = new ArrayList<>();

        String sql = "SELECT * FROM wand";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()){
            list.add(new Wand(
                            (rs.getInt("id")),
                            (rs.getString("wood")),
                            (rs.getString("core")),
                            rs.getDouble(("length"))
                    ));
        }//endwhile
        rs.close();
        return list;
    }

    public void update(Wand wand) throws SQLException {
        String sql = "update wand set wood=?, core=?,length=? where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, wand.getWood());
        ps.setString(2, wand.getCore());
        ps.setDouble(3, wand.getLength());
        ps.setInt(4, wand.getId());
        ps.executeUpdate();
        ps.close();

    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM wand WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();

    }

    public int getId(String wood, String core, double length) throws SQLException {
       int id=0;

        return id;
    }


}
