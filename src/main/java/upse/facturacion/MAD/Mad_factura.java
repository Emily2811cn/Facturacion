package upse.facturacion.MAD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import upse.facturacion.general.BD;
import upse.facturacion.modelo.CabFactura;
import upse.facturacion.modelo.DetFactura;

public class Mad_factura {

    private final BD bd;

    public Mad_factura() {
        this.bd = new BD();
    }

    public float recuperarValorIva() {
        float valIva = 0;
        String sql = "SELECT valornumerico FROM ParametroGeneral WHERE clave='iva' AND estado=1";
        try {
            bd.conectarBD();
            PreparedStatement ps = bd.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                valIva = rs.getFloat("valornumerico") / 100;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bd.desconectarBD();
        }
        return valIva;
    }

    public int recuperarSecuencia() {
        int secuencia = 0;
        String sql = "SELECT valornumerico FROM ParametroGeneral WHERE clave='SECUENCIAL' AND estado=1";
        try {
            bd.conectarBD();
            PreparedStatement ps = bd.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                secuencia = rs.getInt("valornumerico");
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bd.desconectarBD();
        }
        return secuencia;
    }

    public boolean registrarFactura(CabFactura fac) {
        try {
            bd.conectarBD();
            bd.iniciarTransaccion();

            int idCliente = fac.getCli_id();

            // 1. Insertar cliente si es nuevo
            if (idCliente == 0) {
                String sqlCliente = "INSERT INTO Cliente (cli_cedula, cli_nombres, cli_apellidos, cli_direccion, cli_telefono, cli_correo, cli_estado) "
                        + "VALUES (?,?,?,?,?,?,'A')";

                PreparedStatement psCli = bd.getConexion().prepareStatement(sqlCliente, Statement.RETURN_GENERATED_KEYS);
                psCli.setString(1, fac.getNumdocumento());
                psCli.setString(2, fac.getNombres());
                psCli.setString(3, fac.getApellidos());
                psCli.setString(4, fac.getDireccion());
                psCli.setString(5, fac.getTelefono());
                psCli.setString(6, fac.getEmail());
                psCli.executeUpdate();

                ResultSet rsCli = psCli.getGeneratedKeys();
                if (rsCli.next()) {
                    idCliente = rsCli.getInt(1);
                    System.out.println("Id cliente generado: " + idCliente);
                }
                rsCli.close();
                psCli.close();
            }

            // 2. Insertar cabecera factura
            String sqlCab = "INSERT INTO Factura (fac_numero, fac_fecha, cli_id, cli_cedula, cli_nombres, "
                    + "cli_apellidos, cli_direccion, cli_telefono, cli_email, "
                    + "fac_subtotal, fac_subtotalcero, fac_iva, fac_descuento, fac_total, fac_estado) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement psCab = bd.getConexion().prepareStatement(sqlCab, Statement.RETURN_GENERATED_KEYS);
            psCab.setString(1, fac.getNumFactura());
            psCab.setDate(2, java.sql.Date.valueOf(fac.getFecha()));
            psCab.setInt(3, idCliente);
            psCab.setString(4, fac.getNumdocumento());
            psCab.setString(5, fac.getNombres());
            psCab.setString(6, fac.getApellidos());
            psCab.setString(7, fac.getDireccion());
            psCab.setString(8, fac.getTelefono());
            psCab.setString(9, fac.getEmail());
            psCab.setFloat(10, fac.getSubtotal());
            psCab.setFloat(11, fac.getSubtotalcero());
            psCab.setFloat(12, fac.getIva());
            psCab.setFloat(13, 0);
            psCab.setFloat(14, fac.getTotal());
            psCab.setString(15, fac.getEstado());
            psCab.executeUpdate();

            ResultSet rsCab = psCab.getGeneratedKeys();
            int idFactura = 0;
            if (rsCab.next()) {
                idFactura = rsCab.getInt(1);
                System.out.println("Id factura generado: " + idFactura);
            }
            rsCab.close();
            psCab.close();

            // 3. Insertar detalle factura
            // Columnas: fac_id, prod_id, prod_nombre, cantidad, prod_pvp, iva, total
            String sqlDet = "INSERT INTO DetalleFactura "
                    + "(fac_id, prod_id, prod_nombre, cantidad, prod_pvp, iva, total) "
                    + "VALUES (?,?,?,?,?,?,?)";

            PreparedStatement psDet = bd.getConexion().prepareStatement(sqlDet);

            for (DetFactura d : fac.getDetallefactura()) {
                // Saltar filas vacías (sin código de producto)
                if (d.getProd_cod() == null || d.getProd_cod().trim().isEmpty()) {
                    continue;
                }

                d.ActualizarTotales(); // asegurar que subtotal, iva y total estén actualizados

                psDet.setInt(1, idFactura);           // fac_id  → FK de Factura
                psDet.setInt(2, d.getProd_id());      // prod_id → ID del producto
                psDet.setString(3, d.getProd_nombre()); // prod_nombre
                psDet.setFloat(4, d.getCantidad());   // cantidad
                psDet.setDouble(5, d.getPrecio());    // prod_pvp (precio unitario)
                psDet.setDouble(6, d.getIva());       // iva
                psDet.setDouble(7, d.getTotal());     // total
                psDet.addBatch();
            }

            psDet.executeBatch();
            psDet.close();

            // 4. Actualizar secuencial
            String sqlSeq = "UPDATE ParametroGeneral SET valornumerico = valornumerico + 1 "
                    + "WHERE clave = 'SECUENCIAL' AND estado = 1";
            PreparedStatement psSeq = bd.getConexion().prepareStatement(sqlSeq);
            psSeq.executeUpdate();
            psSeq.close();

            bd.commit();
            return true;

        } catch (Exception e) {
            try {
                if (bd.getConexion() != null) {
                    bd.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            bd.desconectarBD();
        }
    }
}