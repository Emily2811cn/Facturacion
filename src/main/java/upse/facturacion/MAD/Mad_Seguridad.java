/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.facturacion.MAD;

import upse.facturacion.general.BD;
import upse.facturacion.modelo.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author EMILY CRUZ
 */
public class Mad_Seguridad {
    private final BD bd;

    public Mad_Seguridad() {
        this.bd = new BD();
    }


    public Usuario login(String usuario, String clave) {
        Usuario usu = null;
        String sql = "";
        sql = sql + " select usr_id,per_id,usr_nombres ";
        sql = sql + " from usuario";
        sql = sql + " where usr_usuario = ? ";
        sql = sql + " and usr_clave= ?  ";
        sql = sql + " and usr_estado= 'A' ";
        System.out.println(sql);
        try {
            bd.conectarBD();
            PreparedStatement ps = bd.getConexion().prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, clave);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usu = new Usuario(
                        rs.getInt("usr_id"),
                        rs.getInt("per_id"),
                        rs.getString("usr_nombres")
                );
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bd.conectarBD();
        }

        return usu;
    }
}
