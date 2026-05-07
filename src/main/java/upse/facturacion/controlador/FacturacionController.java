package upse.facturacion.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import upse.facturacion.general.BD;
import upse.facturacion.modelo.Cliente;
import upse.facturacion.modelo.DetFactura;
import java.util.ArrayList;
import upse.facturacion.general.Mod_general;
import upse.facturacion.modelo.CabFactura;

public class FacturacionController implements Initializable {

    @FXML
    private Button btn_grabar, btn_anular, btn_cerrar, btn_nuevo;
    @FXML
    private TextField txt_numFactura, txt_fecha, txt_documento, txt_nombres, txt_telefono, txt_email;
    @FXML
    private TextArea txt_direccion;

    private ArrayList<DetFactura> detalleFactura = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt_documento.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {  // cuando PIERDE el foco
                buscarYLlenarCliente();
            }
        });
    }

    private void buscarYLlenarCliente() {
        String cedula = txt_documento.getText().trim();
        Cliente cliente = BD.recuperarCliente(cedula);

        if (cliente != null) {
            txt_nombres.setText(cliente.getNombres());
            txt_telefono.setText(cliente.getTelefono());
            txt_email.setText(cliente.getEmail());
            txt_direccion.setText(cliente.getDireccion());
        } else {
            txt_nombres.clear();
            txt_telefono.clear();
            txt_email.clear();
            txt_direccion.clear();
        }
    }


    @FXML
    private void acc_grabar(ActionEvent event) {
        String cedula = txt_documento.getText().trim();
        Cliente cliente = BD.recuperarCliente(cedula);

        if (cliente == null) {
            cliente = new Cliente(
                    cedula,
                    txt_nombres.getText(),
                    txt_telefono.getText(),
                    txt_email.getText(),
                    txt_direccion.getText()
            );
            BD.guardarCliente(cliente);
        }

        float subtotal = 0, subtotalCero = 0, iva = 0, total = 0;
        for (DetFactura det : detalleFactura) {
            if (det.isAplicaIva()) {
                subtotal += det.getTotal();
            } else {
                subtotalCero += det.getTotal();
            }
        }
        iva = subtotal * 0.12f;
        total = subtotal + subtotalCero + iva;

        CabFactura factura = new CabFactura(
                Integer.parseInt(txt_numFactura.getText()),
                txt_fecha.getText(),
                cedula,
                txt_nombres.getText(),
                txt_direccion.getText(),
                txt_telefono.getText(),
                txt_email.getText(),
                detalleFactura,
                subtotal,
                subtotalCero,
                iva,
                total
        );

        BD.guardarFactura(factura);
        Mod_general.fun_mensajeInformacion("Factura guardada correctamente.");
    }

    @FXML
    private void acc_anular(ActionEvent event) {
    }

    @FXML
    private void acc_cerrar(ActionEvent event) {
    }

    @FXML
    private void acc_nuevo(ActionEvent event) {
    }

    @FXML
    private void acc_buscarCliente(ActionEvent event) {
        buscarYLlenarCliente();
        String cedula = txt_documento.getText().trim();
        Cliente cliente = BD.recuperarCliente(cedula);

        if (cliente != null) {
            txt_nombres.setText(cliente.getNombres());
            txt_telefono.setText(cliente.getTelefono());
            txt_email.setText(cliente.getEmail());
            txt_direccion.setText(cliente.getDireccion());
        } else {
            txt_nombres.clear();
            txt_telefono.clear();
            txt_email.clear();
            txt_direccion.clear();
        }
    }
}
