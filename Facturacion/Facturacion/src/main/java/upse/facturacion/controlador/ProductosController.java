/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package upse.facturacion.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
import static upse.facturacion.general.BD.productos;
import static upse.facturacion.general.Mod_general.fun_mensajeError;
import upse.facturacion.general.Mod_general;
import upse.facturacion.modelo.Producto;

/**
 * FXML Controller class
 *
 * @author Miguel
 */
public class ProductosController implements Initializable {

    @FXML private Button btn_cerrar;
    @FXML private AnchorPane dataPaneProducto;
    @FXML private Button btn_nuevo;
    @FXML private TextField txt_buscar;
    @FXML private RadioButton rad_nombre;
    @FXML private RadioButton rad_codigo;
    @FXML private TableView<Producto> tb_productos;
    @FXML private TableColumn<Producto, String> col_codigo;
    @FXML private TableColumn<Producto, String> col_nombre;
    @FXML private TableColumn<Producto, Double> col_precio;
    @FXML private TableColumn<Producto, Integer> col_stock;

    private ToggleGroup grupoBusqueda;
    private ResourceBundle rb;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;

        // Configurar las celdas de la tabla con los atributos del modelo Producto
        this.col_codigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        this.col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.col_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        this.col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        // Configuración del grupo de búsqueda (RadioButtons)
        grupoBusqueda = new ToggleGroup();
        this.rad_codigo.setToggleGroup(grupoBusqueda);
        this.rad_nombre.setToggleGroup(grupoBusqueda);
        this.rad_codigo.setSelected(true);

        // Listener para búsqueda en tiempo real
        this.txt_buscar.textProperty().addListener((obs, oldValue, newValue) -> {
            filtrarTabla(newValue);
        });

        // Listener para cambio de criterio de búsqueda
        this.grupoBusqueda.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            filtrarTabla(this.txt_buscar.getText());
        });

        this.cargarProductos();
    }

    private void filtrarTabla(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            this.tb_productos.setItems(FXCollections.observableArrayList(productos));
            return;
        }

        ObservableList<Producto> listaFiltrada = FXCollections.observableArrayList();
        String textoFiltro = filtro.toLowerCase();

        for (Producto p : productos) {
            if (rad_codigo.isSelected()) {
                if (p.getCodigo().toLowerCase().contains(textoFiltro)) {
                    listaFiltrada.add(p);
                }
            } else if (rad_nombre.isSelected()) {
                if (p.getNombre().toLowerCase().contains(textoFiltro)) {
                    listaFiltrada.add(p);
                }
            }
        }
        this.tb_productos.setItems(listaFiltrada);
    }

    private void cargarProductos() {
        this.tb_productos.setItems(FXCollections.observableArrayList(productos));
    }

    @FXML
    private void acc_cerrar(ActionEvent event) {
        this.dataPaneProducto.setVisible(false);
    }

    @FXML
    private void acc_nuevo(ActionEvent event) {
        this.abrirModal("");
    }

    /**
     * Abre la ventana de registro/edición de Producto
     * @param id Código del producto a editar (vacío para nuevo)
     */
    private void abrirModal(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Mod_general.DIRVISTAS + "Producto.fxml"));
            loader.setResources(this.rb);
            Parent root = loader.load();

            ProductoController controlador = loader.getController();
            controlador.recuperarProducto(id);

            Stage mystage = new Stage();
            mystage.initModality(Modality.APPLICATION_MODAL);
            mystage.initStyle(StageStyle.UNDECORATED);
            mystage.setScene(new Scene(root));
            mystage.showAndWait();

            // Refrescar la tabla al cerrar el modal
            this.cargarProductos();
        } catch (Exception e) {
            fun_mensajeError("Error al abrir el formulario: " + e.getMessage());
        }
    }

    @FXML
    private void acc_Clickedmause(MouseEvent event) {
        // Doble clic para editar producto
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            Producto sel = this.tb_productos.getSelectionModel().getSelectedItem();
            if (sel != null) {
                this.abrirModal(sel.getCodigo());
            }
        }
    }
}