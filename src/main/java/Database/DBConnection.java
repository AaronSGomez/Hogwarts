package Database;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/hp";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() {
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("☑️☑️☑️☑️️☑️️Bienvenido a howarts");
        } catch (SQLException e) {
            System.err.println("❌❌❌❌❌Error de conexion: "+e.getMessage());
        }
        return conn;
    }
}
