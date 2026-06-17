/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.facturacion.modelo;

public class DetFactura {

    private String codigo;
    private String prod_nombre;
    private Float cantidad;      // editable en la tabla
    private double precio;       // mismo tipo que Productos
    private boolean aplicaIva;
    private double total;        // usar double
    private double subtotal;     // usar double

    public DetFactura(String codigo, String prod_nombre, Float cantidad,
            double precio, boolean aplicaIva,
            double total, double subtotal) {
        this.codigo = codigo;
        this.prod_nombre = prod_nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.aplicaIva = aplicaIva;
        this.total = total;
        this.subtotal = subtotal;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProd_nombre() {
        return prod_nombre;
    }

    public void setProd_nombre(String prod_nombre) {
        this.prod_nombre = prod_nombre;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isAplicaIva() {
        return aplicaIva;
    }

    public void setAplicaIva(boolean aplicaIva) {
        this.aplicaIva = aplicaIva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
