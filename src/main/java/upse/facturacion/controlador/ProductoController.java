package upse.facturacion.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import upse.facturacion.general.BD;
import upse.facturacion.general.Mod_general;
import upse.facturacion.modelo.Productos;

public class ProductoController implements Initializable {

    @FXML private Button btn_cancelar;
    @FXML private AnchorPane apProducto;
    @FXML private Button btn_guardarProducto;
    @FXML private Button btn_limpiar;
    @FXML private TextField txt_nombreProducto;
    @FXML private TextField txt_codigo;
    @FXML private TextField txt_descripcion;
    @FXML private ComboBox<String> cbo_categoria;
    @FXML private TextField txt_precio;
    @FXML private ComboBox<String> cbo_presentacion;
    @FXML private ComboBox<String> cbo_estado;
    // Textos traducibles
    @FXML private Text lbl_tituloProductos;
    @FXML private Text lbl_agregarProducto;
    @FXML private Text lbl_nombreProducto;
    @FXML private Text lbl_precio;
    @FXML private Text lbl_categoria;
    @FXML private Text lbl_presentacion;
    @FXML private Text lbl_descripcion;
    @FXML private Text lbl_estado;
    @FXML private Text lbl_codigo;

    private ResourceBundle bundle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bundle = (rb != null) ? rb : Mod_general.getBundle();

        // Traducir etiquetas Text
        aplicarIdioma();

        // Categorías traducibles
        cbo_categoria.getItems().addAll(
            t("prod.cat.calientes", "Bebidas calientes"),
            t("prod.cat.frias",     "Bebidas frías"),
            t("prod.cat.reposteria","Repostería"),
            t("prod.cat.snacks",    "Snacks"),
            t("prod.cat.especiales","Especiales")
        );

        // Presentaciones traducibles
        cbo_presentacion.getItems().addAll(
            t("prod.pres.pequeno",  "Pequeño"),
            t("prod.pres.estandar", "Estándar"),
            t("prod.pres.grande",   "Grande")
        );

        // Estado del producto
        cbo_estado.getItems().addAll(
            t("prod.estado.activo",   "Activo"),
            t("prod.estado.inactivo", "Inactivo")
        );
    }

    private void aplicarIdioma() {
        try {
            if (lbl_tituloProductos != null)  lbl_tituloProductos.setText(t("productos.titulo", "PRODUCTOS"));
            if (lbl_agregarProducto != null)   lbl_agregarProducto.setText(t("productos.agregar", "Agregar Productos"));
            if (lbl_nombreProducto != null)    lbl_nombreProducto.setText(t("lbl_nombreProducto_field", "Nombre del Producto:"));
            if (lbl_precio != null)            lbl_precio.setText(t("lbl_precio_field", "Precio:"));
            if (lbl_categoria != null)         lbl_categoria.setText(t("lbl_categoria_field", "Categoría:"));
            if (lbl_presentacion != null)      lbl_presentacion.setText(t("lbl_presentacion", "Presentación:"));
            if (lbl_descripcion != null)       lbl_descripcion.setText(t("lbl_descripcion_field", "Descripción:"));
            if (lbl_estado != null)            lbl_estado.setText(t("lbl_estado", "Estado:"));
            if (lbl_codigo != null)            lbl_codigo.setText(t("lbl_codigo_field", "Código:"));
            if (btn_guardarProducto != null)   btn_guardarProducto.setText(t("btn_guardarProducto", "Guardar producto"));
            if (btn_limpiar != null)           btn_limpiar.setText(t("btn_limpiar", "Limpiar campos"));
            if (btn_cancelar != null)          btn_cancelar.setText(t("clientes.btn.cancelar", "Cancelar"));
        } catch (Exception e) {
            // ignorar
        }
    }

    private String t(String key, String fallback) {
        try { return bundle.getString(key); } catch (Exception e) { return fallback; }
    }

    @FXML
    private void acc_cancelar(ActionEvent event) {
        this.apProducto.setVisible(false);
    }

    @FXML
    private void acc_guardarProducto(ActionEvent event) {
        if (fun_validar()) {
            String categoria = cbo_categoria.getValue().toString();
            String codigo = generarCodigoPorCategoria(categoria);

            Productos nuevo = new Productos(
                txt_nombreProducto.getText(),
                categoria,
                Double.parseDouble(txt_precio.getText()),
                cbo_presentacion.getValue().toString(),
                txt_descripcion.getText(),
                codigo
            );

            BD.agregarProducto(nuevo);
            String msg = t("msg.producto.guardado", "Producto guardado con código")
                         + " " + nuevo.getCodigo();
            Mod_general.fun_mensajeInformacion(msg);
        }
    }

    @FXML
    private void acc_limpiar(ActionEvent event) {
        txt_nombreProducto.clear();
        txt_codigo.clear();
        txt_descripcion.clear();
        txt_precio.clear();
        cbo_categoria.setValue(null);
        cbo_presentacion.setValue(null);
        cbo_estado.setValue(null);
        txt_nombreProducto.requestFocus();
    }

    private String generarCodigoPorCategoria(String categoria) {
        // Mapeo por prefijo según valor en lista (independiente del idioma)
        int idx = cbo_categoria.getItems().indexOf(categoria);
        String[] prefijos = {"C", "F", "R", "S", "E"};
        String prefijo = (idx >= 0 && idx < prefijos.length) ? prefijos[idx] : "X";
        long count = BD.listaProductos.stream()
            .filter(p -> p.getCategoria().equals(categoria))
            .count();
        return String.format("%s%03d", prefijo, count + 1);
    }

    private boolean fun_validar() {
        if (txt_nombreProducto.getText().isEmpty()) {
            Mod_general.fun_mensajeError(t("val.nombre.requerido", "Debe ingresar el nombre del producto."));
            txt_nombreProducto.requestFocus();
            return false;
        }
        if (cbo_categoria.getValue() == null) {
            Mod_general.fun_mensajeError(t("lbl_categoria_field", "Categoría") + ": campo requerido.");
            cbo_categoria.requestFocus();
            return false;
        }
        if (txt_precio.getText().isEmpty() || !txt_precio.getText().matches("\\d+(\\.\\d{1,2})?")) {
            Mod_general.fun_mensajeError(t("lbl_precio_field", "Precio") + ": debe ser un valor válido (ej: 2.50).");
            txt_precio.requestFocus();
            return false;
        }
        return true;
    }
}
