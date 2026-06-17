/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.facturacion.modelo;

/**
 *
 * @author EMILY CRUZ
 */
public class Cliente {

    private int cli_id;
    private String cli_cedula;
    private String cli_nombres;
    private String cli_apellidos;
    private String cli_direccion;
    private String cli_telefono;
    private String cli_correo;
    private String cli_estado;

    public Cliente(int cli_id, String cli_cedula, String cli_nombres, String cli_apellidos, String cli_direccion, String cli_telefono, String cli_correo, String cli_estado) {
        this.cli_id = cli_id;
        this.cli_cedula = cli_cedula;
        this.cli_nombres = cli_nombres;
        this.cli_apellidos = cli_apellidos;
        this.cli_direccion = cli_direccion;
        this.cli_telefono = cli_telefono;
        this.cli_correo = cli_correo;
        this.cli_estado = cli_estado;
    }

    public int getCli_id() {
        return cli_id;
    }

    public void setCli_id(int cli_id) {
        this.cli_id = cli_id;
    }

    public String getCli_cedula() {
        return cli_cedula;
    }

    public void setCli_cedula(String cli_cedula) {
        this.cli_cedula = cli_cedula;
    }

    public String getCli_nombres() {
        return cli_nombres;
    }

    public void setCli_nombres(String cli_nombres) {
        this.cli_nombres = cli_nombres;
    }

    public String getCli_apellidos() {
        return cli_apellidos;
    }

    public void setCli_apellidos(String cli_apellidos) {
        this.cli_apellidos = cli_apellidos;
    }

    public String getCli_direccion() {
        return cli_direccion;
    }

    public void setCli_direccion(String cli_direccion) {
        this.cli_direccion = cli_direccion;
    }

    public String getCli_telefono() {
        return cli_telefono;
    }

    public void setCli_telefono(String cli_telefono) {
        this.cli_telefono = cli_telefono;
    }

    public String getCli_correo() {
        return cli_correo;
    }

    public void setCli_correo(String cli_correo) {
        this.cli_correo = cli_correo;
    }

    public String getCli_estado() {
        return cli_estado;
    }

    public void setCli_estado(String cli_estado) {
        this.cli_estado = cli_estado;
    }
}
    