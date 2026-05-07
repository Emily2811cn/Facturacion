/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import upse.facturacion.general.BD;
import upse.facturacion.general.Mod_general;
import upse.facturacion.modelo.Productos;

/**
 * FXML Controller class
 *
 * @author EMILY CRUZ
 */
public class ProductoController implements Initializable {

    @FXML
    private Button btn_cancelar;
    @FXML
    private AnchorPane apProducto;
    @FXML
    private Button btn_guardarProducto;
    @FXML
    private Button btn_limpiar;
    @FXML
    private TextField txt_nombreProducto;
    @FXML
    private TextField txt_codigo;
    @FXML
    private TextField txt_descripcion;
    @FXML
    private ComboBox<String> cbo_categoria;
    @FXML
    private TextField txt_precio;
    @FXML
    private ComboBox<String> cbo_presentacion;
    @FXML
    private ComboBox<String> cbo_estado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 🔹 Categorías
        cbo_categoria.getItems().addAll(
                "Bebidas calientes",
                "Bebidas frías",
                "Repostería",
                "Snacks",
                "Especiales"
        );

        // 🔹 Presentaciones
        cbo_presentacion.getItems().addAll(
                "Pequeño",
                "Estándar",
                "Grande"
        );

        // 🔹 Estado del producto
        cbo_estado.getItems().addAll(
                "Activo",
                "Inactivo"
        );
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
            Mod_general.fun_mensajeInformacion("Producto " + nuevo.getNombre() + " guardado con código " + nuevo.getCodigo());
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
        String prefijo;
        switch (categoria) {
            case "Bebidas calientes":
                prefijo = "C";
                break;
            case "Bebidas frías":
                prefijo = "F";
                break;
            case "Repostería":
                prefijo = "R";
                break;
            case "Snacks":
                prefijo = "S";
                break;
            case "Especiales":
                prefijo = "E";
                break;
            default:
                prefijo = "X"; // categoría desconocida
        }

        long count = BD.listaProductos.stream()
                .filter(p -> p.getCategoria().equals(categoria))
                .count();

        return String.format("%s%03d", prefijo, count + 1);
    }

    private boolean fun_validar() {
        if (txt_nombreProducto.getText().isEmpty()) {
            Mod_general.fun_mensajeError("Debe ingresar el nombre del producto.");
            txt_nombreProducto.requestFocus();
            return false;
        }
        if (cbo_categoria.getValue() == null) {
            Mod_general.fun_mensajeError("Debe seleccionar una categoría.");
            cbo_categoria.requestFocus();
            return false;
        }
        if (txt_precio.getText().isEmpty() || !txt_precio.getText().matches("\\d+(\\.\\d{1,2})?")) {
            Mod_general.fun_mensajeError("Debe ingresar un precio válido (ejemplo: 2.50).");
            txt_precio.requestFocus();
            return false;
        }
        if (txt_codigo.getText().isEmpty()) {
            Mod_general.fun_mensajeError("Debe generarse un código para el producto.");
            txt_codigo.requestFocus();
            return false;
        }

// Validar que el código tenga 1 letra + 3 dígitos
        if (!txt_codigo.getText().matches("[A-Z]\\d{3}")) {
            Mod_general.fun_mensajeError("El código debe tener el formato Letra + 3 dígitos (ejemplo: C001, F002).");
            txt_codigo.requestFocus();
            return false;
        }

        return true;
    }

}//fin
