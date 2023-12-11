
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstablecimientoDao {
    
    //CONEXIONES
    Connection Conexion;
    Conexion Conectar = new Conexion();
    PreparedStatement Consulta;
    ResultSet Resultado;
    //-----------------
    
    
    //Forma de registro de Tienda
    public boolean RegistrarTienda(Establecimiento registro){
        String Registro_TD = "INSERT INTO tienda(serie, nombre, tienda,telefono, direccion) VALUES (?,?,?,?,?)";
        try {
           Conexion = Conectar.getConnection();
           Consulta = Conexion.prepareStatement(Registro_TD);
           Consulta.setString(1, registro.getSerie());
           Consulta.setString(2, registro.getNombre());
           Consulta.setString(3, registro.getTienda());
           Consulta.setString(4, registro.getTelefono());
           Consulta.setString(5, registro.getDireccion());
           Consulta.execute();
           return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                Conexion.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    //LLAMA A LA LISTA PARA PRESENTAR
    public List ListarTienda(){
        List<Establecimiento> Listapr = new ArrayList();
        String Listado_TD = "SELECT * FROM tienda";
        try {
            Conexion = Conectar.getConnection();
            Consulta = Conexion.prepareStatement(Listado_TD);
            Resultado = Consulta.executeQuery();
            while (Resultado.next()) {                
                Establecimiento T_PRO = new Establecimiento();
                T_PRO.setId(Resultado.getInt("id"));
                T_PRO.setSerie(Resultado.getString("serie"));
                T_PRO.setNombre(Resultado.getString("nombre"));
                T_PRO.setTienda(Resultado.getString("tienda"));
                T_PRO.setTelefono(Resultado.getString("telefono"));
                T_PRO.setDireccion(Resultado.getString("direccion"));
                Listapr.add(T_PRO);
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Listapr;
    }
    
    
    //ELIMINACION DE TIENDA
    public boolean EliminarTienda(int id){
        String Eliminar_TD = "DELETE FROM proveedor WHERE id = ? ";
        try {
            Conexion = Conectar.getConnection();
            Consulta = Conexion.prepareStatement(Eliminar_TD);
            Consulta.setInt(1, id);
            Consulta.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                Conexion.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    //MODIFICACION DE DATOS
    public boolean ModificarTienda(Establecimiento registros){
        String Updte_TD = "UPDATE proveedor SET serie=?, nombre=?, telefono=?, direccion=? WHERE id=?";
        try {
            Conexion = Conectar.getConnection();
            Consulta = Conexion.prepareStatement(Updte_TD);
            Consulta.setString(1, registros.getSerie());
            Consulta.setString(2, registros.getNombre());
            Consulta.setString(3, registros.getTelefono());
            Consulta.setString(4, registros.getDireccion());
            Consulta.setInt(5, registros.getId());
            Consulta.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                Conexion.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
}
