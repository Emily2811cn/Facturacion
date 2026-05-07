package upse.facturacion.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
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
import upse.facturacion.general.Mod_general;
import static upse.facturacion.general.Mod_general.DIRVISTAS;

public class PrincipalController implements Initializable {

    @FXML
    private MenuItem menu_clientes;
    @FXML
    private Button btn_clientes;
    @FXML
    private Button btn_productos;
    @FXML
    private Label lbl_usuario;
    @FXML
    private StackPane datapane;
    @FXML
    private Button btn_facturar;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización si la necesitas
        ResourceBundle bundle = Mod_general.getBundle();
    }

    @FXML
    private void acc_menuClientes(ActionEvent event) throws IOException {
        String pantalla = DIRVISTAS + "Cliente.fxml";
        this.setDataPane(this.funAnimacion(pantalla));
    }

    @FXML
    private void acc_clientes(ActionEvent event) throws IOException {
        String pantalla = DIRVISTAS + "Cliente.fxml";
        this.setDataPane(this.funAnimacion(pantalla));
    }

    @FXML
    private void acc_productos(ActionEvent event) throws IOException {
        String pantalla = DIRVISTAS + "Producto.fxml";
        this.setDataPane(this.funAnimacion(pantalla));
    }

    // 🔹 Método que carga cualquier ventana con animación y traducción
    public AnchorPane funAnimacion(String url) throws IOException {
        Locale locale = new Locale("es"); // idioma actual
        ResourceBundle bundle = ResourceBundle.getBundle("idioma.messages", locale);

        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(url), bundle);

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

    @FXML
    private void acc_facturar(ActionEvent event) throws IOException{
        String pantalla = DIRVISTAS + "Facturacion.fxml";
        this.setDataPane(this.funAnimacion(pantalla));
    }
}
