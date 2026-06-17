/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.facturacion.modelo;

/**
 *
 * @author EMILY CRUZ
 */
public class Usuario {
    private int usr_id;
    private int per_id;
    private String usr_nombres;

    public Usuario() {
    }

    public Usuario(int usr_id, int per_id, String usr_nombres) {
        this.usr_id = usr_id;
        this.per_id = per_id;
        this.usr_nombres = usr_nombres;
    }

    public int getUsr_id() {
        return usr_id;
    }

    public int getPer_id() {
        return per_id;
    }

    public String getUsr_nombres() {
        return usr_nombres;
    }
    
    
}
