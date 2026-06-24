package upse.facturacion.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import upse.facturacion.MAD.Mad_Productos;
import upse.facturacion.general.Mod_general;
import upse.facturacion.modelo.Productos;
import java.nio.file.Files;
import java.io.File;
import java.io.ByteArrayInputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProductoController implements Initializable {

    @FXML
    private Button btn_cancelar;
    @FXML
    private AnchorPane apProducto;
    @FXML
    private Button btn_guardarProducto;
    @FXML
    private TextField txt_nombreProducto;
    @FXML
    private TextField txt_codigo;
    // Textos traducibles
    @FXML
    private Text lbl_tituloProductos;
    @FXML
    private Text lbl_nombreProducto;
    @FXML
    private Text lbl_precio;

    @FXML
    private Text lbl_codigo;

    private ResourceBundle bundle;
    int bandera;
    Mad_Productos madProductos = new Mad_Productos();
    @FXML
    private TextField txt_precioCompra;
    @FXML
    private ImageView img_prod;
    @FXML
    private CheckBox chk_estado;
    @FXML
    private TextField txt_precioMenor;
    @FXML
    private TextField txt_precioMayor;
    @FXML
    private TextField txt_stock;
    @FXML
    private CheckBox chk_iva;
    @FXML
    private Button cargar_imagen;
    private byte[] imagenBytes = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bundle = (rb != null) ? rb : Mod_general.getBundle();

        // Traducir etiquetas Text
        aplicarIdioma();
        txt_codigo.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                txt_codigo.setText(newVal.replaceAll("[^\\d]", ""));
            }
            if (txt_codigo.getText().length() > 5) {
                txt_codigo.setText(txt_codigo.getText().substring(0, 5));
            }
        });

    }

    private void aplicarIdioma() {
        try {
            if (lbl_tituloProductos != null) {
                lbl_tituloProductos.setText(t("productos.titulo", "PRODUCTOS"));
            }

            if (lbl_nombreProducto != null) {
                lbl_nombreProducto.setText(t("lbl_nombreProducto_field", "Nombre del Producto:"));
            }
            if (lbl_precio != null) {
                lbl_precio.setText(t("lbl_precio_field", "Precio:"));
            }

            if (lbl_codigo != null) {
                lbl_codigo.setText(t("lbl_codigo_field", "Código:"));
            }
            if (btn_guardarProducto != null) {
                btn_guardarProducto.setText(t("btn_guardarProducto", "Guardar producto"));
            }

            if (btn_cancelar != null) {
                btn_cancelar.setText(t("clientes.btn.cancelar", "Cancelar"));
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

    @FXML
    private void acc_guardarProducto(ActionEvent event) {
        try {

            String estado = this.chk_estado.isSelected() ? "A" : "E";

            Productos obj = new Productos(
                    this.bandera, // 0 = insert, >0 = update
                    this.txt_codigo.getText(),
                    this.txt_nombreProducto.getText(),
                    Double.parseDouble(this.txt_precioCompra.getText()),
                    Double.parseDouble(this.txt_precioMenor.getText()),
                    Double.parseDouble(this.txt_precioMayor.getText()),
                    Double.parseDouble(this.txt_stock.getText()),
                    this.chk_iva.isSelected(),
                    imagenBytes, // byte[] que cargaste desde un FileChooser
                    estado
            );

            if (this.madProductos.mantProducto(obj)) {
                Mod_general.fun_mensajeInformacion(
                        bandera == 0 ? "Producto registrado con éxito" : "Producto actualizado con éxito"
                );
            } else {
                Mod_general.fun_mensajeError(
                        bandera == 0 ? "Error al registrar producto" : "Error al actualizar producto"
                );
                return; // ← no cerrar si falló
            }

            this.cerrarFormulario();
        } catch (Exception e) {
            Mod_general.fun_mensajeError(e.getMessage());
        }
    }

    @FXML
    private void acc_cancelar(ActionEvent event) {
        this.cerrarFormulario();
    }

    private void cerrarFormulario() {
        try {
            Stage stage = (Stage) this.btn_cancelar.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            Mod_general.fun_mensajeError(e.getMessage());
        }
    }

    public void recuperarProducto(String id) {
        if (id.equals("")) {
            bandera = 0;
            fun_limpiar(); // limpia los campos para nuevo producto
        } else {
            bandera = 1;
            recuperarproducto(id);
        }
    }

    private void fun_limpiar() {
        txt_codigo.clear();
        txt_nombreProducto.clear();
        txt_precioCompra.clear();
        txt_precioMenor.clear();
        txt_precioMayor.clear();
        txt_stock.clear();
        chk_iva.setSelected(false);
        chk_estado.setSelected(false);
        img_prod.setImage(null); // limpia la imagen
        txt_codigo.requestFocus();  // foco en el primer campo
    }

    private void recuperarproducto(String cod) {
        if (cod == null || cod.isEmpty()) {
            bandera = 0;
            imagenBytes = null;
            img_prod.setImage(null);
        } else {
            Productos objProducto = madProductos.buscaProductoxCod(cod); // ✅ usar el parámetro
            if (objProducto != null) {
                bandera = objProducto.getProd_id();

                txt_codigo.setText(objProducto.getProd_cod());
                txt_nombreProducto.setText(objProducto.getProd_nombre());
                txt_precioCompra.setText(String.valueOf(objProducto.getProd_precioCompra()));
                txt_precioMenor.setText(String.valueOf(objProducto.getProd_pvpxmenor()));
                txt_precioMayor.setText(String.valueOf(objProducto.getProd_pvpxmayor()));
                txt_stock.setText(String.valueOf(objProducto.getProd_stock()));
                chk_iva.setSelected(objProducto.isProd_aplicaIva());
                txt_codigo.setDisable(true);

                // Imagen
                imagenBytes = objProducto.getPod_imagen();
                if (imagenBytes != null && imagenBytes.length > 0) {
                    img_prod.setImage(new Image(new ByteArrayInputStream(imagenBytes)));
                } else {
                    img_prod.setImage(null);
                }

                // Estado
                chk_estado.setSelected(objProducto.getProd_estado().equalsIgnoreCase("A"));
            }
        }
    }

    @FXML
    private void acc_cargarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen del producto");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(btn_guardarProducto.getScene().getWindow());
        if (file != null) {
            try {
                // Convertir archivo a byte[]
                imagenBytes = Files.readAllBytes(file.toPath());

                // Mostrar en el ImageView
                Image image = new Image(file.toURI().toString());
                img_prod.setImage(image);

            } catch (Exception e) {
                Mod_general.fun_mensajeError("Error al cargar imagen: " + e.getMessage());
            }
        }
    }

}
