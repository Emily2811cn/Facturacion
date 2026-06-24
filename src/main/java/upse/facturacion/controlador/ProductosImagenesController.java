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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.ByteArrayInputStream;
import javafx.scene.control.ToggleGroup;
import upse.facturacion.MAD.Mad_Productos;
import upse.facturacion.general.Mod_general;
import upse.facturacion.modelo.Productos;

/**
 * FXML Controller class
 *
 * @author EMILY CRUZ
 */
public class ProductosImagenesController implements Initializable {

    @FXML
    private AnchorPane dataPaneProducto;
    @FXML
    private TextField txt_buscarProducto;
    @FXML
    private RadioButton rad_codigo;
    @FXML
    private RadioButton rad_nombre;
    @FXML
    private Button btn_cerrar;
    @FXML
    private ScrollPane scroll_prod;
    @FXML
    private TilePane tilepane_prod;

    /**
     * Initializes the controller class.
     */
    private ObservableList<Productos> listaProductos;
    private Productos productoSeleccionado;
    ToggleGroup grupoBusqueda = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        rad_codigo.setToggleGroup(grupoBusqueda);
        rad_nombre.setToggleGroup(grupoBusqueda);
        rad_nombre.setSelected(true);
        cargarProductos();

        scroll_prod.setFitToWidth(true);
        scroll_prod.setFitToHeight(true);
        scroll_prod.setContent(tilepane_prod);
        scroll_prod.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // solo scroll vertical
        scroll_prod.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        // Filtrar al escribir
        txt_buscarProducto.textProperty().addListener((obs, oldValue, newValue) -> {
            mostrarProductosFiltrados(newValue);
        });
    }

    @FXML
    private void acc_cerrar(ActionEvent event) {
        this.cerrarFormulario();
    }

    private void cerrarFormulario() {
        try {
            Stage stage = (Stage) this.btn_cerrar.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            Mod_general.fun_mensajeError(e.getMessage());
        }
    }

    private void cargarProductos() {
        Mad_Productos madProductos = new Mad_Productos();
        listaProductos = madProductos.getProductos(); // traer lista desde BD
        mostrarProductosFiltrados("");
    }

    private void mostrarProductosFiltrados(String filtro) {
        tilepane_prod.getChildren().clear();

        for (Productos prod : listaProductos) {
            boolean coincide = false;

            if (filtro == null || filtro.isEmpty()) {
                coincide = true; // mostrar todos
            } else if (rad_codigo.isSelected()) {
                coincide = prod.getProd_cod().toLowerCase().startsWith(filtro.toLowerCase());
            } else if (rad_nombre.isSelected()) {
                coincide = prod.getProd_nombre().toLowerCase().contains(filtro.toLowerCase());
            }

            if (coincide) {
                VBox card = crearCard(prod);
                tilepane_prod.getChildren().add(card);
            }
        }
    }

    private VBox crearCard(Productos prod) {
        ImageView img;
        if (prod.getPod_imagen() != null) {
            img = new ImageView(new Image(new ByteArrayInputStream(prod.getPod_imagen())));
        } else {
            img = new ImageView(new Image("/upse/facturacion/recursos/error.png")); // imagen por defecto
        }
        img.setFitWidth(120);
        img.setFitHeight(120);

        Label lblNombre = new Label(prod.getProd_nombre());

        VBox card = new VBox(img, lblNombre);
        card.setSpacing(5);
        card.setPrefSize(150, 180);

        if ("E".equalsIgnoreCase(prod.getProd_estado())) {
            // 🔹 Estilo para inactivos
            card.setStyle("-fx-border-color: gray; -fx-padding: 10; -fx-background-color: #dddddd; -fx-opacity: 0.5;");
            // 🔹 Bloquear selección
            card.setOnMouseClicked(event -> {
                // no hace nada si está inactivo
            });
        } else {
            // 🔹 Estilo normal para activos
            card.setStyle("-fx-border-color: gray; -fx-padding: 10; -fx-background-color: #f9f9f9;");
            // 🔹 Doble clic para seleccionar
            card.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    productoSeleccionado = prod;
                    Stage stage = (Stage) tilepane_prod.getScene().getWindow();
                    stage.close();
                }
            });
        }

        return card;
    }

    public Productos getProductoSeleccionado() {
        return productoSeleccionado;
    }
}
