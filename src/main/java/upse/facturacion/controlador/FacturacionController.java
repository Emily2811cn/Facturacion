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
import upse.facturacion.modelo.Cliente;
import upse.facturacion.modelo.DetFactura;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.FloatStringConverter;
import upse.facturacion.MAD.Mad_Clientes;
import upse.facturacion.MAD.Mad_Productos;
import upse.facturacion.general.Mod_VariablesGlobales;
import upse.facturacion.general.Mod_general;
import static upse.facturacion.general.Mod_general.fun_mensajeError;
import upse.facturacion.modelo.CabFactura;
import upse.facturacion.modelo.Productos;

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
    private TableColumn<DetFactura, Double> col_total;
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

    private int bandera = 0;
    boolean banderaCliente = false;
    private ObservableList<DetFactura> detallefac = FXCollections.observableArrayList();
    private ResourceBundle bundle;
    @FXML
    private CheckBox chk_validar;
    @FXML
    private TextField txt_subtotal;
    @FXML
    private TextField txt_subtotal0;
    @FXML
    private TextField txt_iva;
    @FXML
    private TextField txt_total;
    @FXML
    private TableColumn<DetFactura, Void> col_buscar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt_documento.setOnAction(event -> buscarYLlenarCliente());

        this.bundle = (rb != null) ? rb : Mod_general.getBundle();

        aplicarIdioma();

        txt_numFactura.setText(Mod_VariablesGlobales.generarNumeroFactura());
        txt_fecha.setText(Mod_VariablesGlobales.obtenerFechaHoy());

        txt_documento.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                buscarYLlenarCliente();
            }
        });

        // Inicializar tabla con una fila vacía
        detallefac.add(new DetFactura(0, "", "", 0f, 0.0, false, 0.0, 0.0));
        tbl_detalle.setItems(detallefac);

        configurarTabla();
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
        Mad_Clientes madClientes = new Mad_Clientes();
        Cliente cliente = madClientes.recuperarClientePorCedula(cedula);

        if (cliente != null) {
            bandera = 1;
            txt_nombres.setText(cliente.getCli_nombres());
            txt_telefono.setText(cliente.getCli_telefono());
            txt_email.setText(cliente.getCli_correo());
            txt_direccion.setText(cliente.getCli_direccion());
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
        Mad_Clientes madClientes = new Mad_Clientes();
        Cliente cliente = madClientes.recuperarClientePorCedula(cedula);

        if (cliente == null) {
            // Crear nuevo cliente
            cliente = new Cliente(
                    0, // cli_id → 0 para nuevo
                    cedula,
                    txt_nombres.getText(),
                    "", // apellidos (si no tienes campo)
                    txt_direccion.getText(),
                    txt_telefono.getText(),
                    txt_email.getText(),
                    "A" // estado activo
            );

            if (madClientes.mantCliente(cliente)) {
                Mod_general.fun_mensajeInformacion("Cliente registrado con éxito");
            } else {
                Mod_general.fun_mensajeError("Error al registrar cliente");
                return;
            }
        } else {
            // Actualizar datos si cambiaron
            cliente.setCli_nombres(txt_nombres.getText());
            cliente.setCli_direccion(txt_direccion.getText());
            cliente.setCli_telefono(txt_telefono.getText());
            cliente.setCli_correo(txt_email.getText());
            cliente.setCli_estado("A");

            madClientes.mantCliente(cliente);
        }

        // Aquí ya puedes continuar con la lógica de la factura
        float subtotal = 0, subtotalCero = 0, iva = 0, total = 0;
        for (DetFactura det : detallefac) {
            if (det.isAplicaIva()) {
                subtotal += det.getTotal();
            } else {
                subtotalCero += det.getTotal();
            }
        }
        iva = subtotal * 0.12f;
        total = subtotal + subtotalCero + iva;

        /* CabFactura factura = new CabFactura(
                0,
                Integer.parseInt(txt_numFactura.getText()),
                txt_fecha.getText(),
                cliente.getCli_id(), // ✅ ahora guardas el ID real
                cedula,
                txt_nombres.getText(),
                "",
                txt_direccion.getText(),
                txt_telefono.getText(),
                txt_email.getText(),
                detallefac,
                subtotal, subtotalCero, iva, total,
                "ACTIVA"
        );*/
        // Aquí llamas a tu método para guardar la factura en BD
        // madFactura.mantFactura(factura);
        Mod_general.fun_mensajeInformacion("Factura guardada correctamente");
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
        detallefac.clear();
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
        detallefac.clear();

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
        tbl_detalle.setEditable(true);

        col_codigo.setCellValueFactory(new PropertyValueFactory<>("prod_cod"));
        col_descripcion.setCellValueFactory(new PropertyValueFactory<>("prod_nombre"));
        col_cantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        col_pvp.setCellValueFactory(new PropertyValueFactory<>("precio"));
        col_subtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        col_aplicaiva.setCellValueFactory(new PropertyValueFactory<>("aplicaIva"));
        col_total.setCellValueFactory(cellData -> {
            DetFactura det = cellData.getValue();
            double subtotal = det.getSubtotal();
            double total = det.isAplicaIva() ? subtotal + subtotal * 0.15 : subtotal;
            return new javafx.beans.property.SimpleDoubleProperty(total).asObject();
        });
        // Código editable
        col_codigo.setCellFactory(TextFieldTableCell.forTableColumn());
        col_codigo.setOnEditCommit(event -> {
            DetFactura det = event.getRowValue();
            String nuevoCodigo = event.getNewValue();
            if (nuevoCodigo == null || nuevoCodigo.trim().isEmpty()) {
                return;
            }

            det.setProd_cod(nuevoCodigo);

            Mad_Productos madProductos = new Mad_Productos();
            Productos objProd = madProductos.buscaProductoxCod(nuevoCodigo);
            if (objProd != null) {
                det.setProd_nombre(objProd.getProd_nombre());
                det.setPrecio(objProd.getProd_precioCompra());
                det.setCantidad(1f);
                det.setSubtotal(det.getCantidad() * det.getPrecio());
                det.setTotal(det.getCantidad() * det.getPrecio());
                det.setAplicaIva(objProd.isProd_aplicaIva());
                tbl_detalle.refresh();
                this.agregarFilasiEsUltima(event.getTablePosition().getRow());
                sumarTotales(); // ✅ recalcula totales generales
            }
        });

        // Descripción editable
        col_descripcion.setCellFactory(TextFieldTableCell.forTableColumn());
        col_descripcion.setOnEditCommit(event -> event.getRowValue().setProd_nombre(event.getNewValue()));

        // Cantidad editable
        col_cantidad.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        col_cantidad.setOnEditCommit(event -> {
            DetFactura det = event.getRowValue();
            det.setCantidad(event.getNewValue());
            det.setSubtotal(det.getCantidad() * det.getPrecio());
            det.setTotal(det.getCantidad() * det.getPrecio());
            tbl_detalle.refresh();
            sumarTotales(); // ✅ recalcula totales generales
        });

        // 🔹 Columna de acción con botón "Buscar"
        col_buscar.setCellFactory(columna -> new TableCell<DetFactura, Void>() {
            private final Button btnBuscar = new Button("Buscar");

            {
                btnBuscar.setOnAction(evento -> {
                    DetFactura det = getTableView().getItems().get(getIndex());
                    abrirBuscarProductos(det);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnBuscar);
            }
        });

    }

    private void abrirBuscarProductos(DetFactura det) {
        try {
            ResourceBundle bundle = Mod_general.getBundle();
            FXMLLoader loader = new FXMLLoader(
                    App.class.getResource("/upse/facturacion/vistas/ProductosImagenes.fxml"),
                    bundle
            );
            Parent root = loader.load();

            ProductosImagenesController controlador = loader.getController();
            Scene scene = new Scene(root);
            Stage mystage = new Stage();
            mystage.initModality(Modality.APPLICATION_MODAL);
            mystage.initStyle(StageStyle.UNDECORATED);
            mystage.setScene(scene);
            mystage.setResizable(false);
            mystage.showAndWait();

            Productos prodSeleccionado = controlador.getProductoSeleccionado();
            if (prodSeleccionado != null) {
                det.setProd_cod(prodSeleccionado.getProd_cod());
                det.setProd_nombre(prodSeleccionado.getProd_nombre());
                det.setPrecio(prodSeleccionado.getProd_precioCompra());
                det.setCantidad(1f);
                det.setAplicaIva(prodSeleccionado.isProd_aplicaIva());

                // ✅ recalcular subtotal y total de la fila
                det.setSubtotal(det.getCantidad() * det.getPrecio());
                det.setTotal(det.getCantidad() * det.getPrecio());

                det.ActualizarTotales(); // si tu método ya hace esto, basta con llamarlo aquí

                tbl_detalle.refresh();
                this.agregarFilasiEsUltima(detallefac.indexOf(det));
                sumarTotales(); // ✅ ahora sí se llenan los campos de abajo
            }

        } catch (Exception e) {
            fun_mensajeError("Error al abrir productos: " + e.getMessage());
        }
    }

    private void agregarFilasiEsUltima(int filaActual) {
        if (filaActual == detallefac.size() - 1) {
            detallefac.add(new DetFactura());
            int posNuevaFila = detallefac.size() - 1;
            Platform.runLater(()
                    -> this.tbl_detalle.edit(posNuevaFila, col_codigo)
            );
        }
    }

    private void sumarTotales() {
        double fac_subtotal = 0;
        double fac_subtotalcero = 0;
        double fac_iva = 0;
        double fac_total = 0;

        for (DetFactura objDet : detallefac) {
            if (objDet == null || objDet.getProd_cod() == null || objDet.getProd_cod().isEmpty()) {
                continue;
            }
            objDet.ActualizarTotales(); // recalcula cada fila
            if (objDet.isAplicaIva()) {
                fac_subtotal += objDet.getSubtotal();
                fac_iva += objDet.getSubtotal() * 0.15;
            } else {
                fac_subtotalcero += objDet.getSubtotal();
            }
        }

        fac_total = fac_subtotal + fac_subtotalcero + fac_iva;

        txt_subtotal.setText(String.format("%.2f", fac_subtotal));
        txt_subtotal0.setText(String.format("%.2f", fac_subtotalcero));
        txt_iva.setText(String.format("%.2f", fac_iva));
        txt_total.setText(String.format("%.2f", fac_total));
    }

}
