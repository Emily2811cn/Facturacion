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

    private ArrayList<DetFactura> detalleFactura = new ArrayList<>();

    // Bandera: 0 = cliente no encontrado, 1 = cliente recuperado
    private int bandera = 0;
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
 
    boolean banderaCliente = false;
    private ObservableList<DetFactura> detallefac=
            FXCollections.observableArrayList();
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        txt_numFactura.setText(Mod_VariablesGlobales.generarNumeroFactura());
        txt_fecha.setText(Mod_VariablesGlobales.obtenerFechaHoy());
        txt_documento.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                buscarYLlenarCliente();
            }
        });
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
            bandera = 0;
            cliente = new Cliente(
                    cedula,
                    txt_nombres.getText(),
                    txt_telefono.getText(),
                    txt_email.getText(),
                    txt_direccion.getText()
            );
            BD.guardarCliente(cliente);
        } else {
            bandera = 1;
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
        Mod_general.fun_mensajeInformacion("Factura guardada correctamente");
    }

    @FXML
    private void acc_anular(ActionEvent event) {
        // Ejemplo: limpiar campos y detalle
        txt_numFactura.clear();
        txt_fecha.clear();
        txt_documento.clear();
        txt_nombres.clear();
        txt_telefono.clear();
        txt_email.clear();
        txt_direccion.clear();
        detalleFactura.clear();
        bandera = 0;
        Mod_general.fun_mensajeInformacion("Factura anulada.");
    }

    @FXML
    private void acc_cerrar(ActionEvent event) {
        cerrarFormulario();
    }

    private void cerrarFormulario() {
        try {
            // Oculta únicamente el panel de facturación
            datapaneFac.setVisible(false);
        } catch (Exception e) {
            fun_mensajeError(e.getMessage());
        }
    }

    @FXML
    private void acc_nuevo(ActionEvent event) {
        // Generar número y fecha automática
        txt_numFactura.setText(Mod_VariablesGlobales.generarNumeroFactura());
        txt_fecha.setText(Mod_VariablesGlobales.obtenerFechaHoy());

        // Limpiar campos de cliente
        txt_documento.clear();
        txt_nombres.clear();
        txt_telefono.clear();
        txt_email.clear();
        txt_direccion.clear();
        detalleFactura.clear();

        Mod_general.fun_mensajeInformacion(
                "Nueva factura lista con número: " + txt_numFactura.getText()
                + " y fecha: " + txt_fecha.getText()
        );
    }

    @FXML
    private void acc_buscarCliente(ActionEvent event) {
        buscarYLlenarCliente();
        Mod_general.fun_mensajeInformacion("Acción buscar cliente ejecutada.");
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
