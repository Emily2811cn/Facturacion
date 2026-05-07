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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static upse.facturacion.general.BD.productos;
import static upse.facturacion.general.Mod_general.fun_mensajeError;
import static upse.facturacion.general.Mod_general.fun_mensajeInformacion;
import upse.facturacion.modelo.Producto;

/**
 * FXML Controller class
 *
 * @author Miguel
 */
public class ProductoController implements Initializable {

    @FXML private Button btn_cerrar;
    @FXML private TextField txt_codigo;
    @FXML private TextField txt_nombre;
    @FXML private TextField txt_precio;
    @FXML private TextField txt_stock;

    private int bandera;
    private ResourceBundle rb;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
    }

    @FXML
    private void acc_grabar(ActionEvent event) {
        try {
            // Conversión de datos numéricos
            double precio = Double.parseDouble(this.txt_precio.getText());
            int stock = Integer.parseInt(this.txt_stock.getText());
            
            Producto obj = new Producto(
                this.txt_codigo.getText(), 
                this.txt_nombre.getText(), 
                precio, 
                stock
            );

            if (bandera == 0) {
                // Registro nuevo
                productos.add(obj);
            } else {
                // Actualización de registro existente
                for (int i = 0; i < productos.size(); i++) {
                    if (productos.get(i).getCodigo().equals(this.txt_codigo.getText())) {
                        productos.set(i, obj);
                        break;
                    }
                }
            }
            
            fun_mensajeInformacion(rb.getString("msg.exito.guardar"));
            acc_cerrar(event);
            
        } catch (NumberFormatException e) {
            fun_mensajeError(rb.getString("msg.error.numero"));
        } catch (Exception e) {
            fun_mensajeError(e.getMessage());
        }
    }

    /**
     * Prepara el formulario para nuevo ingreso o edición
     * @param id Código del producto (vacío para nuevo)
     */
    public void recuperarProducto(String id) {
        if (id.equals("")) {
            bandera = 0;
            txt_codigo.setEditable(true);
            txt_codigo.clear();
            txt_nombre.clear();
            txt_precio.clear();
            txt_stock.clear();
        } else {
            bandera = 1;
            txt_codigo.setEditable(false);
            for (Producto p : productos) {
                if (p.getCodigo().equals(id)) {
                    txt_codigo.setText(p.getCodigo());
                    txt_nombre.setText(p.getNombre());
                    txt_precio.setText(String.valueOf(p.getPrecio()));
                    txt_stock.setText(String.valueOf(p.getStock()));
                    break;
                }
            }
        }
    }

    @FXML
    private void acc_cerrar(ActionEvent event) {
        Stage stage = (Stage) btn_cerrar.getScene().getWindow();
        stage.close();
    }
}