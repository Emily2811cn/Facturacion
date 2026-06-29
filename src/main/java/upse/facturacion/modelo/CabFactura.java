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
    private int fac_id;
    private String numFactura;
    private String fecha;
    private int cli_id;
    private String numdocumento;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String email;
    //falta el detalle de la venta 
    private ArrayList<DetFactura>detallefactura;
    private float subtotal;
    private float subtotalcero;
    private float iva;
    private float total;
    private String estado;
//aqui va lo de la clave de autorizacion de la factura 
    public CabFactura() {
    }

    public CabFactura(int fac_id, String numFactura, String fecha, int cli_id, String numdocumento, String nombres, String apellidos, String direccion, String telefono, String email, ArrayList<DetFactura> detallefactura, float subtotal, float subtotalcero, float iva, float total, String estado) {
        this.fac_id = fac_id;
        this.numFactura = numFactura;
        this.fecha = fecha;
        this.cli_id = cli_id;
        this.numdocumento = numdocumento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.detallefactura = detallefactura;
        this.subtotal = subtotal;
        this.subtotalcero = subtotalcero;
        this.iva = iva;
        this.total = total;
        this.estado = estado;
    }

    public int getFac_id() {
        return fac_id;
    }

    public void setFac_id(int fac_id) {
        this.fac_id = fac_id;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCli_id() {
        return cli_id;
    }

    public void setCli_id(int cli_id) {
        this.cli_id = cli_id;
    }

    public String getNumdocumento() {
        return numdocumento;
    }

    public void setNumdocumento(String numdocumento) {
        this.numdocumento = numdocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

 
    
}
