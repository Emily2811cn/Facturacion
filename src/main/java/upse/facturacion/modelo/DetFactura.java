/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.facturacion.modelo;

public class DetFactura {

    private int fac_id;
    private String prod_cod;
    private String prod_nombre;
    private Float cantidad;      // editable en la tabla
    private double precio;       // mismo tipo que Productos
    private boolean aplicaIva;
    private double subtotal;     // usar double

    private double total;        // usar double

    public DetFactura() {
        //this.cantidad=1;
    }

    public DetFactura(int fac_id, String prod_cod, String prod_nombre, Float cantidad, double precio, boolean aplicaIva, double subtotal, double total) {
        this.fac_id = fac_id;
        this.prod_cod = prod_cod;
        this.prod_nombre = prod_nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.aplicaIva = aplicaIva;
        this.subtotal = subtotal;
        this.total = total;
        
    }

    public int getFac_id() {
        return fac_id;
    }

    public void setFac_id(int fac_id) {
        this.fac_id = fac_id;
    }

    public String getProd_cod() {
        return prod_cod;
    }

    public void setProd_cod(String prod_cod) {
        this.prod_cod = prod_cod;
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

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


}
