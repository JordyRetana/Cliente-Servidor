
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductosDao {
    
    //CONEXIONES
    Connection Conexion;
    Conexion Conectar = new Conexion();
    PreparedStatement Consulta;
    ResultSet Resultado;
    //-----------------
    
    
    //REGISTROS DE PRODUCTOS
    public boolean RegistrarProductos(Productos productos){
        String Registro_cl = "INSERT INTO productos (codigo, nombre, proveedor, stock, precio) VALUES (?,?,?,?,?)";
        try {
            Conexion = Conectar.getConnection();
            Consulta = Conexion.prepareStatement(Registro_cl);
            Consulta.setString(1, productos.getCodigo());
            Consulta.setString(2, productos.getNombre());
            Consulta.setInt(3, productos.getProveedor());
            Consulta.setInt(4, productos.getStock());
            Consulta.setDouble(5, productos.getPrecio());
            Consulta.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    
    
    //LISTADO DE PRODUCTOS
    public List ListarProductos(){
       List<Productos> ListarPro = new ArrayList();
       String LISTAR = "SELECT pr.id AS id_proveedor, pr.nombre AS nombre_proveedor, p.* FROM proveedor pr INNER JOIN productos p ON pr.id = p.proveedor ORDER BY p.id DESC";
       try {
           Conexion = Conectar.getConnection();
           Consulta = Conexion.prepareStatement(LISTAR);
           Resultado = Consulta.executeQuery();
           while (Resultado.next()) {               
               Productos pro = new Productos();
               pro.setId(Resultado.getInt("id"));
               pro.setCodigo(Resultado.getString("codigo"));
               pro.setNombre(Resultado.getString("nombre"));
               pro.setProveedor(Resultado.getInt("id_proveedor"));
               pro.setProveedorPro(Resultado.getString("nombre_proveedor"));
               pro.setStock(Resultado.getInt("stock"));
               pro.setPrecio(Resultado.getDouble("precio"));
               ListarPro.add(pro);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return ListarPro;
   }
    
    
    
    
    //ELIMINAR PRODUCTOS
    public boolean EliminarProductos(int id){
       String BORRAR_PRO = "DELETE FROM productos WHERE id = ?";
       try {
           Consulta = Conexion.prepareStatement(BORRAR_PRO);
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
    
    
    
    //UPDATE PRODUCTOS
    public boolean ModificarProductos(Productos pro){
       String Update_pro = "UPDATE productos SET codigo=?, nombre=?, proveedor=?, stock=?, precio=? WHERE id=?";
       try {
           Consulta = Conexion.prepareStatement(Update_pro);
           Consulta.setString(1, pro.getCodigo());
           Consulta.setString(2, pro.getNombre());
           Consulta.setInt(3, pro.getProveedor());
           Consulta.setInt(4, pro.getStock());
           Consulta.setDouble(5, pro.getPrecio());
           Consulta.setInt(6, pro.getId());
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
    
    
    
    //BUSCAR PRODUCTOS
    public Productos BuscarPro(String Produc){
        Productos producto = new Productos();
        String buscar_produc = "SELECT * FROM productos WHERE codigo = ?";
        try {
            Conexion = Conectar.getConnection();
            Consulta = Conexion.prepareStatement(buscar_produc);
            Consulta.setString(1, Produc);
            Resultado = Consulta.executeQuery();
            if (Resultado.next()) {
                producto.setId(Resultado.getInt("id"));
                producto.setNombre(Resultado.getString("nombre"));
                producto.setPrecio(Resultado.getDouble("precio"));
                producto.setStock(Resultado.getInt("stock"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return producto;
    }
    
    
    
    
    //BUSQUEDA POR COMPRA
    public Productos BuscarId(int id){
        Productos PRODUCT = new Productos();
        String CONSULTA_VENTAS = "SELECT pr.id AS id_proveedor, pr.nombre AS nombre_proveedor, p.* FROM proveedor pr INNER JOIN productos p ON p.proveedor = pr.id WHERE p.id = ?";
        try {
            Conexion = Conectar.getConnection();
            Consulta = Conexion.prepareStatement(CONSULTA_VENTAS);
            Consulta.setInt(1, id);
            Resultado = Consulta.executeQuery();
            if (Resultado.next()) {
                PRODUCT.setId(Resultado.getInt("id"));
                PRODUCT.setCodigo(Resultado.getString("codigo"));
                PRODUCT.setNombre(Resultado.getString("nombre"));
                PRODUCT.setProveedor(Resultado.getInt("proveedor"));
                PRODUCT.setProveedorPro(Resultado.getString("nombre_proveedor"));
                PRODUCT.setStock(Resultado.getInt("stock"));
                PRODUCT.setPrecio(Resultado.getDouble("precio"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return PRODUCT;
    }
    
    
    
    //BUSCAR PROVVEDOR
    public Establecimiento BuscarProveedor(String nombre){
        Establecimiento Tienda_prod = new Establecimiento();
        String Prod = "SELECT * FROM proveedor WHERE nombre = ?";
        try {
            Conexion = Conectar.getConnection();
            Consulta = Conexion.prepareStatement(Prod);
            Consulta.setString(1, nombre);
            Resultado = Consulta.executeQuery();
            if (Resultado.next()) {
                Tienda_prod.setId(Resultado.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Tienda_prod;
    }
    
    
    
    
    //BUSQUEDA DATOS
    public Informacion BuscarDatos(){
        Informacion CONFIGURACION = new Informacion();
        String CONFIG = "SELECT * FROM config";
        try {
            Conexion = Conectar.getConnection();
            Consulta = Conexion.prepareStatement(CONFIG);
            Resultado = Consulta.executeQuery();
            if (Resultado.next()) {
                CONFIGURACION.setId(Resultado.getInt("id"));
                CONFIGURACION.setSerial(Resultado.getString("serial"));
                CONFIGURACION.setNombre(Resultado.getString("nombre"));
                CONFIGURACION.setTelefono(Resultado.getString("telefono"));
                CONFIGURACION.setDireccion(Resultado.getString("direccion"));
                CONFIGURACION.setMensaje(Resultado.getString("mensaje"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return CONFIGURACION;
    }
    
    
    
    
    //UPDATE DATOS
    public boolean ModificarDatos(Informacion conf){
       String UPDATE_DATOS = "UPDATE config SET serial=?, nombre=?, telefono=?, direccion=?, mensaje=? WHERE id=?";
       try {
           Consulta = Conexion.prepareStatement(UPDATE_DATOS);
           Consulta.setString(1, conf.getSerial());
           Consulta.setString(2, conf.getNombre());
           Consulta.setString(3, conf.getTelefono());
           Consulta.setString(4, conf.getDireccion());
           Consulta.setString(5, conf.getMensaje());
           Consulta.setInt(6, conf.getId());
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
