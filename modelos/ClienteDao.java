/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Jordy
 */
public class ClienteDao {
    
    //CONEXIONES
    Conexion Conectar = new Conexion();
    Connection Conexion;
    PreparedStatement Consulta;
    ResultSet Resultado;
    //-----------------
   
    
    
    
    //REGISTRO CLIENTES
    public boolean RegistrarCliente(Cliente LIS){
        String registros_cl = "INSERT INTO clientes (identificacion, nombre, telefono, direccion, genero) VALUES (?,?,?,?,?)";
        try {
            Conexion = Conectar.getConnection();
            Consulta = Conexion.prepareStatement(registros_cl);
            Consulta.setString(1, LIS.getIdentificacion());
            Consulta.setString(2, LIS.getNombre());
            Consulta.setString(3, LIS.getTelefono());
            Consulta.setString(4, LIS.getDireccion());
            Consulta.setInt(5, LIS.getGenero());
            Consulta.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally{
            try {
                Conexion.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    
    //LISTADO DE CLIENTES
   public List ListarCliente(){
       List<Cliente> ListaCl = new ArrayList();
       String listado_cl = "SELECT * FROM clientes";
       try {
           Conexion = Conectar.getConnection();
           Consulta = Conexion.prepareStatement(listado_cl);
           Resultado = Consulta.executeQuery();
           while (Resultado.next()) {               
               Cliente LIS = new Cliente();
               LIS.setId(Resultado.getInt("id"));
               LIS.setIdentificacion(Resultado.getString("identificacion"));
               LIS.setNombre(Resultado.getString("nombre"));
               LIS.setTelefono(Resultado.getString("telefono"));
               LIS.setDireccion(Resultado.getString("direccion"));
               LIS.setGenero(Resultado.getInt("genero"));
               ListaCl.add(LIS);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return ListaCl;
   }
   
   
   
   //BORRAR CLIENTE
   public boolean EliminarCliente(int id){
       String eliminar_cl = "DELETE FROM clientes WHERE id = ?";
       try {
           Consulta = Conexion.prepareStatement(eliminar_cl);
           Consulta.setInt(1, id);
           Consulta.execute();
           return true;
       } catch (SQLException e) {
           System.out.println(e.toString());
           return false;
       }finally{
           try {
               Conexion.close();
           } catch (SQLException ex) {
               System.out.println(ex.toString());
           }
       }
   }
   
   
   
   //UPDATE CLIENTE
   public boolean ModificarCliente(Cliente LIS){
       String update_cl = "UPDATE clientes SET identificacion=?, nombre=?, telefono=?, direccion=?, genero=? WHERE id=?";
       try {
           Consulta = Conexion.prepareStatement(update_cl);   
           Consulta.setString(1, LIS.getIdentificacion());
           Consulta.setString(2, LIS.getNombre());
           Consulta.setString(3, LIS.getTelefono());
           Consulta.setString(4, LIS.getDireccion());
           Consulta.setInt(5, LIS.getGenero());
           Consulta.setInt(6, LIS.getId());
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
   
   
   //BUSQUEDA CLIENTE
   public Cliente Buscarcliente(int identificacion){
       Cliente Buscar_cliente = new Cliente();
       String busqueda_cl = "SELECT * FROM clientes WHERE identificacion = ?";
       try {
           Conexion = Conectar.getConnection();
           Consulta = Conexion.prepareStatement(busqueda_cl);
           Consulta.setInt(1, identificacion);
           Resultado = Consulta.executeQuery();
           if (Resultado.next()) {
               Buscar_cliente.setId(Resultado.getInt("id"));
               Buscar_cliente.setNombre(Resultado.getString("nombre"));
               Buscar_cliente.setTelefono(Resultado.getString("telefono"));
               Buscar_cliente.setDireccion(Resultado.getString("direccion"));
               Buscar_cliente.setGenero(Resultado.getInt("genero"));
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return Buscar_cliente;
   }
   
}
