/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.facturacion.MAD;

import java.sql.PreparedStatement;
import javafx.collections.ObservableList;
import upse.facturacion.general.BD;
import upse.facturacion.modelo.Cliente;
import java.sql.ResultSet;
import upse.facturacion.modelo.Usuario;

/**
 *
 * @author EMILY CRUZ
 */
public class Mad_Clientes {

    private final BD bd;

    public Mad_Clientes() {
        this.bd = new BD();
    }

    public ObservableList<Cliente> getClientes() {
        String cadenaSql = "exec ObtenerClientesActivos";
        /*cadenaSql = cadenaSql + " select c.cli_id, c.cli_cedula, c.cli_nombres,c.cli_apellidos, c.cli_direccion,c.cli_telefono, c.cli_correo,c.cli_estado ";
        cadenaSql = cadenaSql + " from Cliente c ";
        cadenaSql = cadenaSql + " where c.cli_estado = 'A' ";*/
        System.out.println(cadenaSql);
        return bd.getListaConsulta(cadenaSql, (ResultSet rs) -> {
            try {
                return new Cliente(
                        rs.getInt("cli_id"),
                        rs.getString("cli_cedula"),
                        rs.getString("cli_nombres"),
                        rs.getString("cli_apellidos"),
                        rs.getString("cli_direccion"),
                        rs.getString("cli_telefono"),
                        rs.getString("cli_correo"),
                        rs.getString("cli_estado")
                );
            } catch (Exception e) {
                return null;
            }
        });
    }

    //funcion que retorna un cliente por id 
    public Cliente buscaClientexId(int id) {
        Cliente cli = null;
        String sql = "exec sp_selClientexid ?";
        try {
            bd.conectarBD();
            PreparedStatement ps = bd.getConexion().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cli = new Cliente(
                        rs.getInt("cli_id"),
                        rs.getString("cli_cedula"),
                        rs.getString("cli_nombres"),
                        rs.getString("cli_apellidos"),
                        rs.getString("cli_direccion"),
                        rs.getString("cli_telefono"),
                        rs.getString("cli_correo"),
                        rs.getString("cli_estado")
                );
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bd.conectarBD();
        }

        return cli;
    }
}//fin clase
