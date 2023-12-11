
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDAO {
    //CONEXIONES
    Connection Conexion;
    PreparedStatement Consulta;
    ResultSet Resultado;
    Conexion Conectar = new Conexion();
    //-----------------

    
    
    //SERVICIO DE CREADOR DE ADMINS
    public Login log(String correo, String pass){
        Login admin = new Login();
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contraseña = ?";
        try {
            Conexion = Conectar.getConnection();
            Consulta = Conexion.prepareStatement(sql);
            Consulta.setString(1, correo);
            Consulta.setString(2, pass);
            Resultado= Consulta.executeQuery();
            if (Resultado.next()) {
                admin.setId(Resultado.getInt("id"));
                admin.setNombre(Resultado.getString("nombre"));
                admin.setCorreo(Resultado.getString("correo"));
                admin.setContraseña(Resultado.getString("contraseña"));
                admin.setRol(Resultado.getString("rol"));
                
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return admin;
    }
    
    //REGISTROS
    public boolean Registrar(Login reg){
        String Almacenar = "INSERT INTO usuarios (nombre, correo, contraseña, rol) VALUES (?,?,?,?)";
        try {
            Conexion = Conectar.getConnection();
            Consulta = Conexion.prepareStatement(Almacenar);
            Consulta.setString(1, reg.getNombre());
            Consulta.setString(2, reg.getCorreo());
            Consulta.setString(3, reg.getContraseña());
            Consulta.setString(4, reg.getRol());
            Consulta.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    
    //LISTADO DE ADMIN
   
    public List ListarUsuarios(){
       List<Login> Lista = new ArrayList();
       String sql = "SELECT * FROM usuarios";
       try {
           Conexion = Conectar.getConnection();
           Consulta = Conexion.prepareStatement(sql);
           Resultado = Consulta.executeQuery();
           while (Resultado.next()) {               
               Login lg = new Login();
               lg.setId(Resultado.getInt("id"));
               lg.setNombre(Resultado.getString("nombre"));
               lg.setCorreo(Resultado.getString("correo"));
               lg.setRol(Resultado.getString("rol"));
               Lista.add(lg);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return Lista;
   }
}
