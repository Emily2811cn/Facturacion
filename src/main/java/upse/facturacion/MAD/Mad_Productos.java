/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.facturacion.MAD;

import java.sql.ResultSet;
import javafx.collections.ObservableList;
import upse.facturacion.general.BD;
import upse.facturacion.modelo.Productos;
import java.sql.PreparedStatement;

/**
 *
 * @author EMILY CRUZ
 */
public class Mad_Productos {

    private final BD bd;

    public Mad_Productos() {
        this.bd = new BD();

    }

    public ObservableList<Productos> getProductos() {
        String cadenaSql = "exec ObtenerProductosActivos";
        System.out.println(cadenaSql);
        return bd.getListaConsulta(cadenaSql, (ResultSet rs) -> {
            try {
                return new Productos(
                        rs.getInt("prod_id"),
                        rs.getString("prod_cod"),
                        rs.getString("prod_nombre"),
                        rs.getDouble("prod_precioCompra"),
                        rs.getDouble("prod_pvpxmenor"),
                        rs.getDouble("prod_pvpxmayor"),
                        rs.getDouble("prod_stock"),
                        rs.getBoolean("prod_aplicaIva"),
                        rs.getBytes("pod_imagen"),
                        rs.getString("prod_estado")
                );
            } catch (Exception e) {
                return null;
            }
        });
    }

    public Productos buscaProductoxCod(String cod) {
        Productos prod = null;
        String sql = "exec sp_selProductoxCod ?";
        try {
            bd.conectarBD();
            PreparedStatement ps = bd.getConexion().prepareStatement(sql);
            ps.setString(1, cod);   // asigna el parámetro @prod_cod
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                prod = new Productos(
                        rs.getInt("prod_id"),
                        rs.getString("prod_cod"),
                        rs.getString("prod_nombre"),
                        rs.getDouble("prod_precioCompra"),
                        rs.getDouble("prod_pvpxmenor"),
                        rs.getDouble("prod_pvpxmayor"),
                        rs.getDouble("prod_stock"),
                        rs.getBoolean("prod_aplicaIva"),
                        rs.getBytes("pod_imagen"),
                        rs.getString("prod_estado")
                );
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bd.desconectarBD();
        }
        return prod;
    }

    public boolean mantProducto(Productos objproducto) {
        String sql = "EXEC sp_mantProducto ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
        try {
            bd.conectarBD();
            PreparedStatement ps = bd.getConexion().prepareStatement(sql);

            ps.setInt(1, objproducto.getProd_id());
            ps.setString(2, objproducto.getProd_cod());
            ps.setString(3, objproducto.getProd_nombre());
            ps.setDouble(4, objproducto.getProd_precioCompra());
            ps.setDouble(5, objproducto.getProd_pvpxmenor());
            ps.setDouble(6, objproducto.getProd_pvpxmayor());
            ps.setDouble(7, objproducto.getProd_stock());
            ps.setBoolean(8, objproducto.isProd_aplicaIva());

            // Imagen
            if (objproducto.getPod_imagen() != null) {
                ps.setBytes(9, objproducto.getPod_imagen());
            } else {
                ps.setNull(9, java.sql.Types.VARBINARY);
            }

            ps.setString(10, objproducto.getProd_estado());

            int filas = ps.executeUpdate();
            ps.close();
            return filas > 0;
        } catch (Exception e) {
            bd.rollback();
            e.printStackTrace();
            return false;
        } finally {
            bd.desconectarBD();
        }
    }

}
