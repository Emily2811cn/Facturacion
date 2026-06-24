/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package upse.facturacion.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import upse.facturacion.MAD.Mad_Productos;
import upse.facturacion.general.Mod_general;
import static upse.facturacion.general.Mod_general.fun_mensajeError;
import upse.facturacion.modelo.Productos;

/**
 * FXML Controller class
 *
 * @author EMILY CRUZ
 */
public class BuscarProductosController implements Initializable {

    @FXML
    private TextField txt_buscarProducto;
    @FXML
    private RadioButton rad_codigo;
    @FXML
    private RadioButton rad_nombre;
    @FXML
    private TableView<Productos> tbl_productos;
    @FXML
    private TableColumn<?, ?> col_codigo;
    @FXML
    private TableColumn<?, ?> col_nombre;
    @FXML
    private TableColumn<?, ?> col_stock;
    @FXML
    private TableColumn<?, ?> col_precio;
    @FXML
    private TableColumn<?, ?> col_precioMayor;
    @FXML
    private TableColumn<?, ?> col_precioMenor;
    @FXML
    private TableColumn<?, ?> col_iva;
    @FXML
    private TableColumn<?, ?> col_estado;
    @FXML
    private Button btn_cerrar;
    @FXML
    private Button btn_nuevoProd;
    @FXML
    private AnchorPane dataPaneProducto;

    private ToggleGroup grupoBusqueda;
    private ObservableList<Productos> listaProductos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.col_codigo.setCellValueFactory(new PropertyValueFactory<>("prod_cod"));
        this.col_nombre.setCellValueFactory(new PropertyValueFactory<>("prod_nombre"));
        this.col_stock.setCellValueFactory(new PropertyValueFactory<>("prod_stock"));
        this.col_precio.setCellValueFactory(new PropertyValueFactory<>("prod_precioCompra"));

        cargarProductos();
        grupoBusqueda = new ToggleGroup();
        rad_codigo.setToggleGroup(grupoBusqueda);
        rad_nombre.setToggleGroup(grupoBusqueda);
        rad_codigo.setSelected(true);
        // ✅ Filtrar al escribir
        txt_buscarProducto.textProperty().addListener((obs, oldValue, newValue) -> {
            filtrarTabla(newValue);
        });

        // ✅ Filtrar al cambiar radio button
        grupoBusqueda.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            filtrarTabla(txt_buscarProducto.getText());
        });
    }

    private void filtrarTabla(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            tbl_productos.setItems(listaProductos);
            return;
        }

        String criterioBajo = criterio.toLowerCase().trim();

        ObservableList<Productos> listaFiltrada = listaProductos.filtered(c -> {
            if (rad_codigo.isSelected()) {
                return c.getProd_cod().toLowerCase().contains(criterioBajo);
            } else {
                String nombreProducto = (c.getProd_nombre()).toLowerCase();
                return nombreProducto.contains(criterioBajo);
            }
        });

        tbl_productos.setItems(listaFiltrada);
    }

    private void cargarProductos() {
        try {
            Mad_Productos madProductos = new Mad_Productos();
            this.listaProductos = madProductos.getProductos(); // traer lista desde BD
            this.tbl_productos.setItems(listaProductos);       // asignar a la tabla
            this.tbl_productos.refresh();                      // refrescar visualmente
        } catch (Exception e) {
            fun_mensajeError(e.getMessage());
        }
    }

    @FXML
    private void acc_cerrar(ActionEvent event) {
        this.dataPaneProducto.setVisible(false);

    }

    @FXML
    private void acc_nuevo(ActionEvent event) {
        this.abrirModal("");
    }

    private void abrirModal(String id) {
        try {
            ResourceBundle bundle = Mod_general.getBundle();
            FXMLLoader loader = new FXMLLoader(
                    App.class.getResource("/upse/facturacion/vistas/Producto.fxml"),
                    bundle
            );
            Parent root = loader.load();

            ProductoController controlador = loader.getController();
            controlador.recuperarProducto(id); // ✅ usar el parámetro id

            Scene scene = new Scene(root);
            Stage mystage = new Stage();
            mystage.initModality(Modality.APPLICATION_MODAL);
            mystage.initStyle(StageStyle.UNDECORATED);
            mystage.setScene(scene);
            mystage.setResizable(false);
            mystage.showAndWait();

            this.cargarProductos(); // ✅ refrescar tabla al cerrar modal
        } catch (Exception e) {
            fun_mensajeError(e.getMessage());
        }
    }

    @FXML
    private void acc_Clickedmause(MouseEvent event) {
        try {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Productos objSeleccionado = this.tbl_productos.getSelectionModel().getSelectedItem();
                if (objSeleccionado != null) {
                    this.abrirModal(objSeleccionado.getProd_cod());
                }
            }
        } catch (Exception e) {
        }
    }
}
