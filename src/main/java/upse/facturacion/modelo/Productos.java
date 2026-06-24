/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.facturacion.modelo;

public class Productos {

    private int prod_id;                // PK autoincremental
    private String prod_cod;            // Código del producto
    private String prod_nombre;         // Nombre
    private double prod_precioCompra;   // Precio de compra
    private double prod_pvpxmenor;      // Precio venta por menor
    private double prod_pvpxmayor;      // Precio venta por mayor
    private double prod_stock;          // Stock
    private boolean prod_aplicaIva;     // Aplica IVA
    private byte[] pod_imagen;          // Imagen en binario
    private String prod_estado;         // Estado (ej. 'A' activo, 'I' inactivo)

    public Productos() {
    }

    public Productos(int prod_id, String prod_cod, String prod_nombre, double prod_precioCompra, double prod_pvpxmenor, double prod_pvpxmayor, double prod_stock, boolean prod_aplicaIva, byte[] pod_imagen, String prod_estado) {
        this.prod_id = prod_id;
        this.prod_cod = prod_cod;
        this.prod_nombre = prod_nombre;
        this.prod_precioCompra = prod_precioCompra;
        this.prod_pvpxmenor = prod_pvpxmenor;
        this.prod_pvpxmayor = prod_pvpxmayor;
        this.prod_stock = prod_stock;
        this.prod_aplicaIva = prod_aplicaIva;
        this.pod_imagen = pod_imagen;
        this.prod_estado = prod_estado;
    }

    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
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

    public double getProd_precioCompra() {
        return prod_precioCompra;
    }

    public void setProd_precioCompra(double prod_precioCompra) {
        this.prod_precioCompra = prod_precioCompra;
    }

    public double getProd_pvpxmenor() {
        return prod_pvpxmenor;
    }

    public void setProd_pvpxmenor(double prod_pvpxmenor) {
        this.prod_pvpxmenor = prod_pvpxmenor;
    }

    public double getProd_pvpxmayor() {
        return prod_pvpxmayor;
    }

    public void setProd_pvpxmayor(double prod_pvpxmayor) {
        this.prod_pvpxmayor = prod_pvpxmayor;
    }

    public double getProd_stock() {
        return prod_stock;
    }

    public void setProd_stock(double prod_stock) {
        this.prod_stock = prod_stock;
    }

    public boolean isProd_aplicaIva() {
        return prod_aplicaIva;
    }

    public void setProd_aplicaIva(boolean prod_aplicaIva) {
        this.prod_aplicaIva = prod_aplicaIva;
    }

    public byte[] getPod_imagen() {
        return pod_imagen;
    }

    public void setPod_imagen(byte[] pod_imagen) {
        this.pod_imagen = pod_imagen;
    }

    public String getProd_estado() {
        return prod_estado;
    }

    public void setProd_estado(String prod_estado) {
        this.prod_estado = prod_estado;
    }

  
}
