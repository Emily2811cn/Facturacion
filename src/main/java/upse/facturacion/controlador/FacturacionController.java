package upse.facturacion.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import upse.facturacion.general.BD;
import upse.facturacion.modelo.Cliente;
import upse.facturacion.modelo.DetFactura;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import upse.facturacion.general.Mod_VariablesGlobales;
import upse.facturacion.general.Mod_general;
import static upse.facturacion.general.Mod_general.fun_mensajeError;
import upse.facturacion.modelo.CabFactura;

public class FacturacionController implements Initializable {

    @FXML
    private Button btn_grabar, btn_anular, btn_cerrar, btn_nuevo;
    @FXML
    private TextField txt_numFactura, txt_fecha, txt_documento, txt_nombres, txt_telefono, txt_email;
    @FXML
    private TextArea txt_direccion;
    @FXML
    private AnchorPane datapaneFac;
    @FXML
    private TableView<DetFactura> tbl_detalle;
    @FXML
    private TableColumn<DetFactura, String> col_codigo;
    @FXML
    private TableColumn<DetFactura, String> col_descripcion;
    @FXML
    private TableColumn<DetFactura, Float> col_cantidad;
    @FXML
    private TableColumn<DetFactura, Float> col_pvp;
    @FXML
    private TableColumn<DetFactura, Float> col_subtotal;
    @FXML
    private TableColumn<DetFactura, Boolean> col_aplicaiva;
    @FXML
    private TableColumn<DetFactura, Float> col_total;
    // Labels traducibles (los que tienen fx:id en el FXML)
    @FXML
    private Label lbl_facturaTitulo;
    @FXML
    private Label lbl_fecha;
    @FXML
    private Label lbl_cedula;
    @FXML
    private Label lbl_nombres;
    @FXML
    private Label lbl_telefono;
    @FXML
    private Label lbl_correo;
    @FXML
    private Label lbl_direccion;
    @FXML
    private Label lbl_subtotal;
    @FXML
    private Label lbl_subtotal0;
    @FXML
    private Label lbl_iva;
    @FXML
    private Label lbl_total;

    private ArrayList<DetFactura> detalleFactura = new ArrayList<>();
    private int bandera = 0;
    boolean banderaCliente = false;
    private ObservableList<DetFactura> detallefac = FXCollections.observableArrayList();
    private ResourceBundle bundle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bundle = (rb != null) ? rb : Mod_general.getBundle();

        // Traducir etiquetas
        aplicarIdioma();

        txt_numFactura.setText(Mod_VariablesGlobales.generarNumeroFactura());
        txt_fecha.setText(Mod_VariablesGlobales.obtenerFechaHoy());

        txt_documento.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                buscarYLlenarCliente();
            }
        });
    }

    private void aplicarIdioma() {
        try {
            if (lbl_facturaTitulo != null) {
                lbl_facturaTitulo.setText(t("fac.titulo", "FACTURA #"));
            }
            if (lbl_fecha != null) {
                lbl_fecha.setText(t("fac.fecha", "Fecha:"));
            }
            if (lbl_cedula != null) {
                lbl_cedula.setText(t("fac.cedula", "CEDULA/RUC:"));
            }
            if (lbl_nombres != null) {
                lbl_nombres.setText(t("fac.nombres", "NOMBRES:"));
            }
            if (lbl_telefono != null) {
                lbl_telefono.setText(t("fac.telefono", "TELÉFONO:"));
            }
            if (lbl_correo != null) {
                lbl_correo.setText(t("fac.correo", "CORREO:"));
            }
            if (lbl_direccion != null) {
                lbl_direccion.setText(t("fac.direccion", "DIRECCIÓN:"));
            }
            if (lbl_subtotal != null) {
                lbl_subtotal.setText(t("fac.subtotal", "SUBTOTAL"));
            }
            if (lbl_subtotal0 != null) {
                lbl_subtotal0.setText(t("fac.subtotal0", "SUBTOTAL 0%"));
            }
            if (lbl_iva != null) {
                lbl_iva.setText(t("fac.iva", "IVA 15%"));
            }
            if (lbl_total != null) {
                lbl_total.setText(t("fac.total", "TOTAL"));
            }
            if (btn_grabar != null) {
                btn_grabar.setText(t("fac.btn.grabar", "Grabar"));
            }
            if (btn_anular != null) {
                btn_anular.setText(t("fac.btn.anular", "Anular"));
            }
            if (btn_cerrar != null) {
                btn_cerrar.setText(t("fac.btn.cerrar", "Cerrar"));
            }
            if (btn_nuevo != null) {
                btn_nuevo.setText(t("fac.btn.nuevo", "Nuevo"));
            }
            if (col_codigo != null) {
                col_codigo.setText(t("fac.col.codigo", "CÓDIGO"));
            }
            if (col_descripcion != null) {
                col_descripcion.setText(t("fac.col.descripcion", "DESCRIPCIÓN"));
            }
            if (col_cantidad != null) {
                col_cantidad.setText(t("fac.col.cantidad", "CANTIDAD"));
            }
            if (col_pvp != null) {
                col_pvp.setText(t("fac.col.pvp", "PVP"));
            }
            if (col_subtotal != null) {
                col_subtotal.setText(t("fac.col.subtotal", "SUBTOTAL"));
            }
            if (col_aplicaiva != null) {
                col_aplicaiva.setText(t("fac.col.aplicaiva", "APLICA IVA"));
            }
            if (col_total != null) {
                col_total.setText(t("fac.col.total", "TOTAL"));
            }
        } catch (Exception e) {
            // ignorar
        }
    }

    private String t(String key, String fallback) {
        try {
            return bundle.getString(key);
        } catch (Exception e) {
            return fallback;
        }
    }

    private void buscarYLlenarCliente() {
        String cedula = txt_documento.getText().trim();
        Cliente cliente = BD.recuperarCliente(cedula);
        if (cliente != null) {
            bandera = 1;
            txt_nombres.setText(cliente.getNombres());
            txt_telefono.setText(cliente.getTelefono());
            txt_email.setText(cliente.getEmail());
            txt_direccion.setText(cliente.getDireccion());
        } else {
            bandera = 0;
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
            cliente = new Cliente(cedula, txt_nombres.getText(), txt_telefono.getText(),
                    txt_email.getText(), txt_direccion.getText());
            BD.listaClientes.add(cliente);
        } else {
            cliente.setNombres(txt_nombres.getText());
            cliente.setTelefono(txt_telefono.getText());
            cliente.setEmail(txt_email.getText());
            cliente.setDireccion(txt_direccion.getText());
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
                Integer.parseInt(txt_numFactura.getText()), txt_fecha.getText(),
                cedula, txt_nombres.getText(), txt_direccion.getText(),
                txt_telefono.getText(), txt_email.getText(), detalleFactura,
                subtotal, subtotalCero, iva, total
        );

        BD.guardarFactura(factura);
        Mod_general.fun_mensajeInformacion(t("msg.factura.guardada", "Factura guardada correctamente"));
    }

    @FXML
    private void acc_anular(ActionEvent event) {
        txt_numFactura.clear();
        txt_fecha.clear();
        txt_documento.clear();
        txt_nombres.clear();
        txt_telefono.clear();
        txt_email.clear();
        txt_direccion.clear();
        detalleFactura.clear();
        bandera = 0;
        Mod_general.fun_mensajeInformacion(t("msg.factura.anulada", "Factura anulada."));
    }

    @FXML
    private void acc_cerrar(ActionEvent event) {
        try {
            datapaneFac.setVisible(false);
        } catch (Exception e) {
            fun_mensajeError(e.getMessage());
        }
    }

    @FXML
    private void acc_nuevo(ActionEvent event) {
        txt_numFactura.setText(Mod_VariablesGlobales.generarNumeroFactura());
        txt_fecha.setText(Mod_VariablesGlobales.obtenerFechaHoy());
        txt_documento.clear();
        txt_nombres.clear();
        txt_telefono.clear();
        txt_email.clear();
        txt_direccion.clear();
        detalleFactura.clear();
        String msg = t("msg.factura.nueva", "Nueva factura lista con número: ")
                + txt_numFactura.getText();
        Mod_general.fun_mensajeInformacion(msg);
    }

    @FXML
    private void acc_buscarCliente(ActionEvent event) {
        buscarYLlenarCliente();
        Mod_general.fun_mensajeInformacion(t("clientes.buscar", "Buscar cliente") + "...");
    }

    public void configurarTabla() {
        this.col_codigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        this.col_descripcion.setCellValueFactory(new PropertyValueFactory("prod_nombre"));
        this.col_cantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        this.col_pvp.setCellValueFactory(new PropertyValueFactory("pvp"));
        this.col_aplicaiva.setCellValueFactory(new PropertyValueFactory("aplicaIva"));
        this.col_subtotal.setCellValueFactory(new PropertyValueFactory("subtotal"));
    }
}
