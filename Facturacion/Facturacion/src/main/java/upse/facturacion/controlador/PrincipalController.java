/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package upse.facturacion.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import static upse.facturacion.general.Mod_general.DIRVISTAS;

/**
 * @author Miguel
 */
public class PrincipalController implements Initializable {

    @FXML
    private MenuItem menu_cliente;
    @FXML
    private Button btn_clientes;
    @FXML
    private Button btn_productos;
    @FXML
    private Label lbl_usuario;
    @FXML
    private StackPane dataPane;
    @FXML
    private MenuItem men_facturacion;

    private ResourceBundle rb;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Guardamos el bundle para persistir el idioma en las subpantallas
        this.rb = rb;
    }

    @FXML
    private void acc_menuCliente(ActionEvent event) throws IOException {
        String pantalla = DIRVISTAS + "Clientes.fxml";
        this.setDataPane(this.funAnimacion(pantalla));
    }

    @FXML
    private void acc_clientes(ActionEvent event) throws IOException {
        String pantalla = DIRVISTAS + "Clientes.fxml";
        this.setDataPane(this.funAnimacion(pantalla));
    }

    @FXML
    private void acc_productos(ActionEvent event) throws IOException {
        String pantalla = DIRVISTAS + "Productos.fxml";
        this.setDataPane(this.funAnimacion(pantalla));
    }

    @FXML
    private void acc_menFacturacion(ActionEvent event) throws IOException {
        String pantalla = DIRVISTAS + "FacturacionEliminar.fxml";
        this.setDataPane(this.funAnimacion(pantalla));
    }

    /**
     * Carga la vista FXML, inyecta el ResourceBundle y aplica una animación de
     * transición
     */
    public AnchorPane funAnimacion(String url) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        loader.setResources(this.rb);

        AnchorPane anchorPane = loader.load();

        // Esto asegura que el AnchorPane no intente expandirse a lo loco antes de la animación
        anchorPane.setManaged(true);

        FadeTransition ft = new FadeTransition(Duration.millis(800), anchorPane); // 800ms es más fluido
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();

        return anchorPane;
    }

    /**
     * Ajusta el nodo al contenedor principal dataPane
     */
    public void setDataPane(Node node) {
        // 1. Limpiamos el contenedor
        dataPane.getChildren().clear();

        if (node instanceof Region) {
            Region r = (Region) node;

            // 2. IMPORTANTE: NO vinculamos el tamaño para que el formulario 
            // mantenga su tamaño original (ej: 600x400)
            r.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        }

        // 3. Agregamos el nodo directamente. 
        // El StackPane (dataPane) lo centrará automáticamente.
        dataPane.getChildren().add(node);
    }
}
