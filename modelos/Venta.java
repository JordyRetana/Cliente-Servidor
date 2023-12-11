
package Modelo;

public class Venta {
    private int id;
    private int cliente;
    private String nombre_cli;
    private String vendedor;
    private double total;
    private String fecha;
    private String tienda;
    
    public Venta(){
        
    }

    public Venta(int id, int cliente, String nombre_cli, String vendedor, double total, String fecha, String tienda) {
        this.id = id;
        this.cliente = cliente;
        this.nombre_cli = nombre_cli;
        this.vendedor = vendedor;
        this.total = total;
        this.fecha = fecha;
        this.tienda = tienda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public String getNombre_cli() {
        return nombre_cli;
    }

    public void setNombre_cli(String nombre_cli) {
        this.nombre_cli = nombre_cli;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

   
    

    
}
