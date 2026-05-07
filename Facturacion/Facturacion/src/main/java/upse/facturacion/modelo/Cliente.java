/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.facturacion.modelo;

/**
 * @author Miguel
 */
public class Cliente {
    private String cedula;
    private String nombres;
    private String direccion;
    private String telefono;
    private String correo;

    public Cliente() {
    }

    public Cliente(String cedula, String nombres, String direccion, String telefono, String correo) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
    }

    // Getters y Setters
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    /**
     * Método auxiliar para mantener compatibilidad si algún controlador 
     * llama a getNombre() en lugar de getNombres()
     */
    public String getNombre() {
        return nombres;
    }
}