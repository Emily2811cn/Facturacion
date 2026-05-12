package upse.facturacion.controlador;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import upse.facturacion.general.Mod_general;

public class LoginController implements Initializable {

    @FXML private PasswordField txt_clave;
    @FXML private TextField txt_usuario;
    @FXML private Button btn_acceder;
    @FXML private Button btn_cancelar;
    @FXML private ComboBox<String> cbo_idioma;
    @FXML private Text lbl_usuario;
    @FXML private Text lbl_password;
    @FXML private Text lbl_sistema;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Llenar combo sin disparar eventos
        cbo_idioma.getItems().addAll("Español", "English");

        // 2. Sincronizar visualmente sin listener todavía
        if (Mod_general.idiomaActual.getLanguage().equals("en")) {
            cbo_idioma.getSelectionModel().select("English");
        } else {
            cbo_idioma.getSelectionModel().select("Español");
        }

        // 3. Los nodos Text no se traducen automáticamente con %clave al cargar FXML,
        //    pero sí Button/Label. Aplicamos traducción manual solo a los Text.
        if (rb != null) {
            aplicarIdiomaTextos(rb);
        }

        // 4. Registrar listener DESPUÉS de setear el valor inicial
        cbo_idioma.setOnAction(event -> {
            String seleccion = cbo_idioma.getValue();
            if (seleccion == null) return;

            Locale nuevo = seleccion.equals("English") ? new Locale("en") : new Locale("es");

            // Si el idioma no cambió, no hacer nada
            if (nuevo.getLanguage().equals(Mod_general.idiomaActual.getLanguage())) return;

            Mod_general.idiomaActual = nuevo;
            ResourceBundle.clearCache();
            recargarLogin();
        });
    }

    /**
     * Traduce los nodos Text (que JavaFX NO traduce automáticamente con %clave).
     * Button y Label sí se traducen solos cuando el FXML se carga con un ResourceBundle.
     */
    private void aplicarIdiomaTextos(ResourceBundle rb) {
        try {
            if (lbl_usuario != null)  lbl_usuario.setText(rb.getString("login.usuario"));
            if (lbl_password != null) lbl_password.setText(rb.getString("login.contrasena"));
            if (lbl_sistema != null)  lbl_sistema.setText(rb.getString("lbl_sistema"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Recarga el Login pasando SIEMPRE el ResourceBundle actualizado.
     * Esto es lo que permite que %clave funcione en todos los nodos.
     */
    private void recargarLogin() {
        try {
            ResourceBundle bundle = Mod_general.getBundle();
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/upse/facturacion/vistas/Login.fxml"), bundle);
            Pane root = loader.load();
            Stage stage = (Stage) cbo_idioma.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void acc_login(ActionEvent event) {
        try {
            if (fun_validar(txt_usuario.getText(), txt_clave.getText())) {
                ResourceBundle.clearCache();
                ResourceBundle bundle = Mod_general.getBundle();
                FXMLLoader loader = new FXMLLoader(
                    App.class.getResource("/upse/facturacion/vistas/Principal.fxml"), bundle);
                Pane ventana = loader.load();
                Scene scene = new Scene(ventana);
                Stage mystage = (Stage) txt_usuario.getScene().getWindow();
                mystage.setScene(scene);
                mystage.getIcons().add(new Image("/upse/facturacion/recursos/cafelogo.png"));
                mystage.setTitle("--SISTEMA--");
                mystage.setResizable(true);
                mystage.setMaximized(true);
                mystage.show();
            } else {
                ResourceBundle bundle = Mod_general.getBundle();
                String msg = "Credenciales invalidas";
                try { msg = bundle.getString("login.error"); } catch (Exception ex) {}
                Mod_general.fun_mensajeError(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void acc_cancelar(ActionEvent event) {
        Stage stage = (Stage) btn_cancelar.getScene().getWindow();
        stage.close();
    }

    public boolean fun_validar(String usuario, String clave) {
        return usuario.equals("admin") && clave.equals("123");
    }
}
