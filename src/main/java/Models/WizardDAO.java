package Models;

import Database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WizardDAO {

    private Connection conn;

    public WizardDAO() {
        conn= DBConnection.getConnection();
    }


    //CRUD  - CREATE | READ | UPDATE | DELETE
    public void create(Wizard wizard)throws SQLException {
        String sql= "INSERT INTO wizard (name, edad, houseid, wandid) VALUES (?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, wizard.getName());
        ps.setInt(2, wizard.getEdad());
        ps.setInt(3, wizard.getHouseId());
        ps.setInt(4,wizard.getWandId());
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
                            (rs.getInt("houseId")),
                            (rs.getInt("wandId"))
                    ));
        }//endwhile
        rs.close();
        return list;
    }



}
