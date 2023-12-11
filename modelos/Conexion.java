package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    Connection Conexion;

    public Connection getConnection() {
        try {
            String myBD = "jdbc:mysql://localhost:3315/proyecto_java?serverTimezone=UTC";
            Conexion = DriverManager.getConnection(myBD, "root", "");
            return Conexion;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

}
