
package Modelo;

public class Establecimiento {
    private int id;
    private String serie;
    private String nombre;
    private String telefono;
    private String direccion;
    private String tienda;
    
    public Establecimiento(){
        
    }

    public Establecimiento(int id, String serie, String nombre, String telefono, String direccion, String tienda) {
        this.id = id;
        this.serie = serie;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.tienda = tienda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    

   
    
}
