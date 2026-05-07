/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.facturacion.modelo;

/**
 *
 * @author EMILY CRUZ
 */
public class Productos {
    private String nombre;
    private String categoria;
    private double precio;
    private String presentacion;
    private String descripcion;
    private String codigo;

    public Productos(String nombre, String categoria, double precio, String presentacion, String descripcion, String codigo) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.presentacion = presentacion;
        this.descripcion = descripcion;
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    
}
