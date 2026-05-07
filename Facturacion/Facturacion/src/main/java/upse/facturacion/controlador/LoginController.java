package upse.facturacion.controlador;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// Imports de tus clases generales
import static upse.facturacion.general.Mod_general.detectarTecla;
import static upse.facturacion.general.Mod_general.fun_mensajeError;
import static upse.facturacion.general.Mod_general.fun_mensajeInformacion;

/**
 * @author Miguel
 */
public class LoginController implements Initializable {

    @FXML private ComboBox<String> cbx_idioma;
    @FXML private Label lbl_usuario, lbl_clave, lbl_titulo; 
    @FXML private Button btn_login, btn_cancelar;
    @FXML private TextField txt_usuario;
    @FXML private PasswordField txt_clave;

    private ResourceBundle rb;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        
        // 1. Configurar navegación con teclado
        detectarTecla(txt_usuario, KeyCode.ENTER, txt_clave);
        detectarTecla(txt_clave, KeyCode.ENTER, btn_login);
        
        // 2. Llenar el ComboBox de idiomas
        ObservableList<String> idiomas = FXCollections.observableArrayList("Español", "English");
        cbx_idioma.setItems(idiomas);
        
        // Seleccionar idioma actual
        if (rb.getLocale().getLanguage().equals("en")) {
            cbx_idioma.setValue("English");
        } else {
            cbx_idioma.setValue("Español");
        }

        // 3. Valores por defecto para pruebas
        this.txt_usuario.setText("admin");
        this.txt_clave.setText("123");

        // Evento para el cambio de idioma
        cbx_idioma.setOnAction(e -> cambiarIdioma());
    }

    private void cambiarIdioma() {
        String seleccion = cbx_idioma.getValue();
        Locale locale = seleccion.equals("English") ? new Locale("en", "US") : new Locale("es", "EC");

        // Recargar el bundle
        this.rb = ResourceBundle.getBundle("upse.facturacion.idiomas.textos", locale);
        
        // Actualizar la interfaz
        actualizarTextos();
    }

    private void actualizarTextos() {
        btn_login.setText(rb.getString("btn.login"));
        btn_cancelar.setText(rb.getString("btn.cancelar"));
        lbl_usuario.setText(rb.getString("lbl.usuario"));
        lbl_clave.setText(rb.getString("lbl.clave"));
        lbl_titulo.setText(rb.getString("lbl.tituloLogin"));
        txt_usuario.setPromptText(rb.getString("lbl.usuario"));
    }

    @FXML
    private void acc_login(ActionEvent event) {
        try {
            if (fun_validar(this.txt_usuario.getText(), this.txt_clave.getText())) {
                URL principalURL = getClass().getResource("/upse/facturacion/vistas/Principal.fxml");
                
                FXMLLoader loader = new FXMLLoader(principalURL);
                loader.setResources(this.rb); // Pasa el idioma actual
                
                Pane ventana = loader.load();
                Scene scene = new Scene(ventana);
                
                Stage mystage = (Stage) this.txt_clave.getScene().getWindow();
                mystage.setScene(scene);
                
                // Icono seguro
                URL iconURL = getClass().getResource("/upse/facturacion/recursos/carrito.png");
                if (iconURL != null) {
                    mystage.getIcons().add(new Image(iconURL.toExternalForm()));
                }
                
                mystage.setTitle(rb.getString("lbl.tituloPrincipal")); 
                mystage.setMaximized(true);
                mystage.show();
            } else {
                fun_mensajeInformacion(rb.getString("msg.errorLogin"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            fun_mensajeError("Error: " + e.getMessage());
        }
    }

    @FXML
    private void acc_cancelar(ActionEvent event) {
        Platform.exit();
    }

    public boolean fun_validar(String usuario, String clave) {
        return usuario.equals("admin") && clave.equals("123");
    }
}