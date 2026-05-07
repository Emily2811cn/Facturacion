/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.facturacion.modelo;
import java.util.ArrayList;

/**
 *
 * @author EMILY CRUZ
 */
public class CabFactura {
    private int numFactura;
    private String fecha;
    private String numDocumento;
    private String nombres;
    private String direccion;
    private String telefono;
    private String email;
    //falta el detalle de la venta 
    private ArrayList<DetFactura>detallefactura;
    private float subtotal;
    private float subtotalcero;
    private float iva;
    private float total;

    public CabFactura(int numFactura, String fecha, String numDocumento, String nombres, String direccion, String telefono, String email, ArrayList<DetFactura> detallefactura, float subtotal, float subtotalcero, float iva, float total) {
        this.numFactura = numFactura;
        this.fecha = fecha;
        this.numDocumento = numDocumento;
        this.nombres = nombres;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.detallefactura = detallefactura;
        this.subtotal = subtotal;
        this.subtotalcero = subtotalcero;
        this.iva = iva;
        this.total = total;
    }

    public int getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(int numFactura) {
        this.numFactura = numFactura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<DetFactura> getDetallefactura() {
        return detallefactura;
    }

    public void setDetallefactura(ArrayList<DetFactura> detallefactura) {
        this.detallefactura = detallefactura;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getSubtotalcero() {
        return subtotalcero;
    }

    public void setSubtotalcero(float subtotalcero) {
        this.subtotalcero = subtotalcero;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

   
    
}
