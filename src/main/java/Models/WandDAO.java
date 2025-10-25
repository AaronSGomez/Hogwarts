package Models;

import Database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WandDAO {
    private Connection conn;

    public WandDAO() {
        conn= DBConnection.getConnection();
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

}
