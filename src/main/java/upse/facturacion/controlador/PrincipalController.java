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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import upse.facturacion.general.Mod_general;
import static upse.facturacion.general.Mod_general.DIRVISTAS;

public class PrincipalController implements Initializable {

    @FXML private MenuItem menu_clientes;
    @FXML private Button btn_clientes;
    @FXML private Button btn_productos;
    @FXML private Label lbl_usuario;
    @FXML private StackPane datapane;
    @FXML private Button btn_facturar;
    @FXML private Menu menu_archivo;
    @FXML private Menu menu_proceso;
    @FXML private Menu menu_reportes;
    @FXML private TitledPane tp_archivo;
    @FXML private TitledPane tp_proceso;
    @FXML private TitledPane tp_reportes;
    @FXML private TitledPane tp_configuracion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // rb viene con el idioma correcto desde el FXMLLoader del LoginController.
        // El FXML ya traduce Button/Label/MenuItem via %clave.
        // Menu y TitledPane también usan %clave en el FXML y se traducen automáticamente.
        // Solo necesitamos aplicar traducción manual si algún nodo no responde a %clave.
        if (rb != null) {
            aplicarIdiomaExtra(rb);
        }
    }

    /**
     * Menu y TitledPane a veces no respetan %clave al cargar con FXMLLoader+bundle
     * dependiendo de la versión de JavaFX. Los traducimos manualmente por seguridad.
     */
    private void aplicarIdiomaExtra(ResourceBundle rb) {
        try {
            if (menu_archivo != null)     menu_archivo.setText(t(rb, "menu.archivo", "Archivo"));
            if (menu_proceso != null)     menu_proceso.setText(t(rb, "menu.proceso", "Proceso"));
            if (menu_reportes != null)    menu_reportes.setText(t(rb, "menu.reportes", "Reportes"));
            if (menu_clientes != null)    menu_clientes.setText(t(rb, "menu.clientes", "Clientes"));
            if (btn_clientes != null)     btn_clientes.setText(t(rb, "btn.clientes", "Clientes"));
            if (btn_productos != null)    btn_productos.setText(t(rb, "btn.productos", "Productos"));
            if (btn_facturar != null)     btn_facturar.setText(t(rb, "btn.facturar", "Facturar"));
            if (tp_archivo != null)       tp_archivo.setText(t(rb, "menu.archivo", "Archivo"));
            if (tp_proceso != null)       tp_proceso.setText(t(rb, "menu.proceso", "Proceso"));
            if (tp_reportes != null)      tp_reportes.setText(t(rb, "menu.reportes", "Reportes"));
            if (tp_configuracion != null) tp_configuracion.setText(t(rb, "menu.configuracion", "Configuración"));
        } catch (Exception e) {
            // ignorar
        }
    }

    private String t(ResourceBundle rb, String key, String fallback) {
        try { return rb.getString(key); } catch (Exception e) { return fallback; }
    }

    @FXML
    private void acc_menuClientes(ActionEvent event) throws IOException {
        setDataPane(funAnimacion(DIRVISTAS + "Cliente.fxml"));
    }

    @FXML
    private void acc_clientes(ActionEvent event) throws IOException {
        setDataPane(funAnimacion(DIRVISTAS + "Cliente.fxml"));
    }

    @FXML
    private void acc_productos(ActionEvent event) throws IOException {
        setDataPane(funAnimacion(DIRVISTAS + "Producto.fxml"));
    }

    @FXML
    private void acc_facturar(ActionEvent event) throws IOException {
        setDataPane(funAnimacion(DIRVISTAS + "Facturacion.fxml"));
    }

    /**
     * Carga cualquier sub-vista pasando SIEMPRE el ResourceBundle del idioma actual.
     * Esto activa la traducción %clave en todos los FXML hijos.
     */
    public AnchorPane funAnimacion(String url) throws IOException {
        ResourceBundle bundle = Mod_general.getBundle();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url), bundle);
        AnchorPane anchorPane = loader.load();
        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(anchorPane);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        return anchorPane;
    }

    public void setDataPane(Node node) {
        StackPane wrapper = new StackPane();
        if (node instanceof Region) {
            ((Region) node).setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        }
        wrapper.getChildren().add(node);
        datapane.getChildren().setAll(wrapper);
    }
}
