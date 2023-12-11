/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Jordy
 */
public class Cliente {
    private int id;
    private String identificacion;
    private String nombre;
    private String telefono;
    private String direccion;
    private int genero;

    public Cliente() {
    }

    public Cliente(int id, String identificacion, String nombre, String telefono, String direccion, int genero) {
        this.id = id;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.genero = genero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

   
    
   
}
