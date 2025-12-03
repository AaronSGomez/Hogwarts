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
    public Integer create(Wand wand)throws SQLException {
        Integer indexWand=0;
        String sql= "INSERT INTO wand (wood, core, length) VALUES (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, wand.getWood());
        ps.setString(2, wand.getCore());
        ps.setDouble(3, wand.getLength());
        ps.executeUpdate();
        ResultSet id = ps.getGeneratedKeys();
        while(id.next()){
            indexWand=id.getInt(1);
        }
        ps.close();
        return indexWand;
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

    public Integer getId(String wood, String core, double length) throws SQLException {
       Integer indexWand= null;
        String sql= "SELECT id FROM wand WHERE wood=? AND core=? AND length=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, wood);
        ps.setString(2, core);
        ps.setDouble(3, length);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            indexWand=rs.getInt("id");
        }
        return indexWand;
    }


}
