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
import upse.facturacion.MAD.Mad_factura;
import upse.facturacion.general.Mod_VariablesGlobales;
import upse.facturacion.general.Mod_general;
import static upse.facturacion.general.Mod_general.fun_mensajeError;
import upse.facturacion.modelo.CabFactura;
import upse.facturacion.modelo.Productos;
import java.io.File;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.Locale;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    @FXML
    private Button btn_imprimir;
    LocalDate fecha;

    private Mad_factura madFac = new Mad_factura();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        System.out.println("Secuencia: " + madFac.recuperarSecuencia());
        txt_documento.setOnAction(event -> buscarYLlenarCliente());

        this.bundle = (rb != null) ? rb : Mod_general.getBundle();

        aplicarIdioma();

        txt_numFactura.setText(Mod_VariablesGlobales.generarNumeroFactura(madFac.recuperarSecuencia()));
        txt_fecha.setText(Mod_VariablesGlobales.obtenerFechaHoy());

        txt_documento.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                buscarYLlenarCliente();
            }
        });

        // Fila vacía inicial
        detallefac.add(new DetFactura());
        tbl_detalle.setItems(detallefac);

        configurarTabla();
    }

    private void aplicarIdioma() {
        try {
            if (lbl_facturaTitulo != null) lbl_facturaTitulo.setText(t("fac.titulo", "FACTURA #"));
            if (lbl_fecha != null)         lbl_fecha.setText(t("fac.fecha", "Fecha:"));
            if (lbl_cedula != null)        lbl_cedula.setText(t("fac.cedula", "CEDULA/RUC:"));
            if (lbl_nombres != null)       lbl_nombres.setText(t("fac.nombres", "NOMBRES:"));
            if (lbl_telefono != null)      lbl_telefono.setText(t("fac.telefono", "TELÉFONO:"));
            if (lbl_correo != null)        lbl_correo.setText(t("fac.correo", "CORREO:"));
            if (lbl_direccion != null)     lbl_direccion.setText(t("fac.direccion", "DIRECCIÓN:"));
            if (lbl_subtotal != null)      lbl_subtotal.setText(t("fac.subtotal", "SUBTOTAL"));
            if (lbl_subtotal0 != null)     lbl_subtotal0.setText(t("fac.subtotal0", "SUBTOTAL 0%"));
            if (lbl_iva != null)           lbl_iva.setText(t("fac.iva", "IVA 15%"));
            if (lbl_total != null)         lbl_total.setText(t("fac.total", "TOTAL"));
            if (btn_grabar != null)        btn_grabar.setText(t("fac.btn.grabar", "Grabar"));
            if (btn_anular != null)        btn_anular.setText(t("fac.btn.anular", "Anular"));
            if (btn_cerrar != null)        btn_cerrar.setText(t("fac.btn.cerrar", "Cerrar"));
            if (btn_nuevo != null)         btn_nuevo.setText(t("fac.btn.nuevo", "Nuevo"));
            if (col_codigo != null)        col_codigo.setText(t("fac.col.codigo", "CÓDIGO"));
            if (col_descripcion != null)   col_descripcion.setText(t("fac.col.descripcion", "DESCRIPCIÓN"));
            if (col_cantidad != null)      col_cantidad.setText(t("fac.col.cantidad", "CANTIDAD"));
            if (col_pvp != null)           col_pvp.setText(t("fac.col.pvp", "PVP"));
            if (col_subtotal != null)      col_subtotal.setText(t("fac.col.subtotal", "SUBTOTAL"));
            if (col_aplicaiva != null)     col_aplicaiva.setText(t("fac.col.aplicaiva", "APLICA IVA"));
            if (col_total != null)         col_total.setText(t("fac.col.total", "TOTAL"));
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
            cliente = new Cliente(0, cedula, txt_nombres.getText(), "",
                    txt_direccion.getText(), txt_telefono.getText(), txt_email.getText(), "A");
            madClientes.mantCliente(cliente);
        } else {
            cliente.setCli_nombres(txt_nombres.getText());
            cliente.setCli_direccion(txt_direccion.getText());
            cliente.setCli_telefono(txt_telefono.getText());
            cliente.setCli_correo(txt_email.getText());
            cliente.setCli_estado("A");
            madClientes.mantCliente(cliente);
        }

        LocalDate fechaFactura = Mod_VariablesGlobales.parsearFecha(txt_fecha.getText());
        if (fechaFactura == null) {
            fun_mensajeError("Formato de fecha inválido: " + txt_fecha.getText());
            return;
        }

        // Calcular totales
        float subtotal = 0, subtotalCero = 0, iva = 0, total = 0;
        for (DetFactura det : detallefac) {
            // Saltar filas vacías
            if (det.getProd_cod() == null || det.getProd_cod().trim().isEmpty()) continue;

            det.ActualizarTotales();
            if (det.isAplicaIva()) {
                subtotal += det.getSubtotal();
            } else {
                subtotalCero += det.getSubtotal();
            }
            System.out.println("Producto → ID: " + det.getProd_cod()
                    + ", Nombre: " + det.getProd_nombre()
                    + ", Cantidad: " + det.getCantidad()
                    + ", Subtotal: " + det.getSubtotal());
        }
        iva   = subtotal * 0.15f;
        total = subtotal + subtotalCero + iva;

        CabFactura factura = new CabFactura(
                0,
                txt_numFactura.getText(),
                fechaFactura,
                cliente.getCli_id(),
                txt_documento.getText(),
                txt_nombres.getText(),
                "",
                txt_direccion.getText(),
                txt_telefono.getText(),
                txt_email.getText(),
                FXCollections.observableArrayList(detallefac),
                subtotal,
                subtotalCero,
                iva,
                total,
                "A"
        );

        if (madFac.registrarFactura(factura)) {
            System.out.println("✅ Factura registrada: " + factura.getNumFactura());
            Mod_general.fun_mensajeInformacion("Factura guardada correctamente");
        } else {
            fun_mensajeError("Error al registrar la factura en la BD");
        }
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
        txt_numFactura.setText(Mod_VariablesGlobales.generarNumeroFactura(madFac.recuperarSecuencia()));
        txt_fecha.setText(Mod_VariablesGlobales.obtenerFechaHoy());
        txt_documento.clear();
        txt_nombres.clear();
        txt_telefono.clear();
        txt_email.clear();
        txt_direccion.clear();
        detallefac.clear();
        detallefac.add(new DetFactura()); // fila vacía inicial
        txt_subtotal.clear();
        txt_subtotal0.clear();
        txt_iva.clear();
        txt_total.clear();
        Mod_general.fun_mensajeInformacion(
                t("msg.factura.nueva", "Nueva factura lista con número: ") + txt_numFactura.getText());
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
        col_pvp.setCellValueFactory(new PropertyValueFactory<>("precio"));        // getPrecio() → prod_pvp
        col_subtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        col_aplicaiva.setCellValueFactory(new PropertyValueFactory<>("aplicaIva")); // getAplicaIva()
        col_total.setCellValueFactory(cellData -> {
            DetFactura det = cellData.getValue();
            return new javafx.beans.property.SimpleDoubleProperty(det.getTotal()).asObject();
        });

        // Código editable
        col_codigo.setCellFactory(TextFieldTableCell.forTableColumn());
        col_codigo.setOnEditCommit(event -> {
            DetFactura det = event.getRowValue();
            String nuevoCodigo = event.getNewValue();
            if (nuevoCodigo == null || nuevoCodigo.trim().isEmpty()) return;

            det.setProd_cod(nuevoCodigo);

            Mad_Productos madProductos = new Mad_Productos();
            Productos objProd = madProductos.buscaProductoxCod(nuevoCodigo);
            if (objProd != null) {
                det.setProd_id(objProd.getProd_id());          // ← asignar ID para el INSERT
                det.setProd_nombre(objProd.getProd_nombre());
                det.setPrecio(objProd.getProd_precioCompra());
                det.setCantidad(1f);
                det.setAplicaIva(objProd.isProd_aplicaIva());
                det.ActualizarTotales();
                tbl_detalle.refresh();
                agregarFilasiEsUltima(event.getTablePosition().getRow());
                sumarTotales();
            }
        });

        // Descripción editable
        col_descripcion.setCellFactory(TextFieldTableCell.forTableColumn());
        col_descripcion.setOnEditCommit(event -> event.getRowValue().setProd_nombre(event.getNewValue()));

        // Cantidad editable
        col_cantidad.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        col_cantidad.setOnEditCommit(event -> {
            DetFactura det = event.getRowValue();
            det.setCantidad(event.getNewValue()); // recalcula automáticamente en el setter
            tbl_detalle.refresh();
            sumarTotales();
        });

        // Botón Buscar
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
                det.setProd_id(prodSeleccionado.getProd_id());      // ← ID para el INSERT
                det.setProd_cod(prodSeleccionado.getProd_cod());
                det.setProd_nombre(prodSeleccionado.getProd_nombre());
                det.setPrecio(prodSeleccionado.getProd_precioCompra());
                det.setCantidad(1f);
                det.setAplicaIva(prodSeleccionado.isProd_aplicaIva());
                det.ActualizarTotales();
                tbl_detalle.refresh();
                agregarFilasiEsUltima(detallefac.indexOf(det));
                sumarTotales();
            }

        } catch (Exception e) {
            fun_mensajeError("Error al abrir productos: " + e.getMessage());
        }
    }

    private void agregarFilasiEsUltima(int filaActual) {
        if (filaActual == detallefac.size() - 1) {
            detallefac.add(new DetFactura());
            int posNuevaFila = detallefac.size() - 1;
            Platform.runLater(() -> tbl_detalle.edit(posNuevaFila, col_codigo));
        }
    }

    private void sumarTotales() {
        double fac_subtotal = 0, fac_subtotalcero = 0, fac_iva = 0, fac_total = 0;

        for (DetFactura objDet : detallefac) {
            if (objDet == null || objDet.getProd_cod() == null || objDet.getProd_cod().isEmpty()) continue;
            objDet.ActualizarTotales();
            if (objDet.isAplicaIva()) {
                fac_subtotal += objDet.getSubtotal();
                fac_iva      += objDet.getSubtotal() * 0.15;
            } else {
                fac_subtotalcero += objDet.getSubtotal();
            }
        }

        fac_total = fac_subtotal + fac_subtotalcero + fac_iva;

        txt_subtotal.setText(String.format(Locale.US, "%.2f", fac_subtotal));
        txt_subtotal0.setText(String.format(Locale.US, "%.2f", fac_subtotalcero));
        txt_iva.setText(String.format(Locale.US, "%.2f", fac_iva));
        txt_total.setText(String.format(Locale.US, "%.2f", fac_total));
    }

    @FXML
    private void acc_imprimir(ActionEvent event) {
        try {
            String cedula = txt_documento.getText().trim();
            Mad_Clientes madClientes = new Mad_Clientes();
            Cliente cliente = madClientes.recuperarClientePorCedula(cedula);

            LocalDate fechaFactura;
            try {
                fechaFactura = LocalDate.parse(txt_fecha.getText());
            } catch (Exception e) {
                fun_mensajeError("Formato de fecha inválido: " + txt_fecha.getText());
                return;
            }

            CabFactura factura = new CabFactura(
                    0, txt_numFactura.getText(), fechaFactura,
                    cliente.getCli_id(), txt_documento.getText(), txt_nombres.getText(), "",
                    txt_direccion.getText(), txt_telefono.getText(), txt_email.getText(),
                    FXCollections.observableArrayList(detallefac),
                    Float.parseFloat(txt_subtotal.getText()),
                    Float.parseFloat(txt_subtotal0.getText()),
                    Float.parseFloat(txt_iva.getText()),
                    Float.parseFloat(txt_total.getText()),
                    "A"
            );

            generarPDF(factura);
            java.awt.Desktop.getDesktop().open(new File("factura_" + factura.getNumFactura() + ".pdf"));

        } catch (Exception e) {
            fun_mensajeError("Error al imprimir factura: " + e.getMessage());
        }
    }

    private void generarPDF(CabFactura factura) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("factura_" + factura.getNumFactura() + ".pdf"));
            document.open();

            document.add(new Paragraph("CAFETERÍA OASIS COFFEE"));
            document.add(new Paragraph("FACTURA #" + factura.getNumFactura()));
            document.add(new Paragraph("Fecha: " + factura.getFecha()));
            document.add(new Paragraph("Cliente: " + factura.getNombres()));
            document.add(new Paragraph("Cedula: " + factura.getNumdocumento()));
            document.add(new Paragraph("Direccion: " + factura.getDireccion()));
            document.add(new Paragraph("Telefono: " + factura.getTelefono()));
            document.add(new Paragraph("Correo: " + factura.getEmail()));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Detalle de productos:"));
            for (DetFactura det : factura.getDetallefactura()) {
                if (det.getProd_cod() == null || det.getProd_cod().trim().isEmpty()) continue;
                document.add(new Paragraph(
                        det.getProd_cod() + " - " + det.getProd_nombre()
                        + " x" + det.getCantidad()
                        + " PVP: " + det.getPrecio()
                        + " Subtotal: " + det.getSubtotal()
                ));
            }

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Subtotal: " + factura.getSubtotal()));
            document.add(new Paragraph("Subtotal 0%: " + factura.getSubtotalcero()));
            document.add(new Paragraph("IVA: " + factura.getIva()));
            document.add(new Paragraph("TOTAL: " + factura.getTotal()));

            document.close();
        } catch (Exception e) {
            fun_mensajeError("Error al generar PDF: " + e.getMessage());
        }
    }
}