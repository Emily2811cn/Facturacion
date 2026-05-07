/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package upse.facturacion.controlador;

import java.net.URL;
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
import javafx.stage.Stage;
import upse.facturacion.general.Mod_general;
import java.util.Locale;

/**
 * FXML Controller class
 *
 * @author EMILY CRUZ
 */
public class LoginController implements Initializable {

    @FXML
    private PasswordField txt_clave;
    @FXML
    private TextField txt_usuario;
    @FXML
    private Button btn_acceder;
    @FXML
    private Button btn_cancelar;
    @FXML
    private ComboBox<String> cbo_idioma;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbo_idioma.getItems().addAll("Español", "English");
        cbo_idioma.setValue("Español"); // valor por defecto

        cbo_idioma.setOnAction(event -> {
            String seleccion = cbo_idioma.getValue();
            if (seleccion.equals("Español")) {
                Mod_general.idiomaActual = new Locale("es");
            } else {
                Mod_general.idiomaActual = new Locale("en");
            }
            recargarLogin();
        });
    }

    private void recargarLogin() {
        try {
            ResourceBundle bundle = Mod_general.getBundle();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/upse/facturacion/vistas/Login.fxml"), bundle);
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
        //presentar pantalla principal
        try {
            if (fun_validar(this.txt_usuario.getText(), this.txt_clave.getText())) {
                //ir a la pantalla principal
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(App.class.getResource("/upse/facturacion/vistas/Principal.fxml"));
                Pane ventana = (Pane) loader.load();
                Scene scene = new Scene(ventana);
                Stage mystage = (Stage) this.txt_usuario.getScene().getWindow();
                mystage.setScene(scene);
                mystage.getIcons().add(new Image("/upse/facturacion/recursos/cafelogo.png"));
                mystage.setTitle("--SISTEMA--");
                mystage.setResizable(true);//bloquear minimizar
                mystage.setMaximized(true);//pantalla completa 
                mystage.show();
            } else {
                //denegar el acceso al sistema
                System.out.println("Credenciales inválidas");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void acc_cancelar(ActionEvent event) {
    }

    public boolean fun_validar(String usuario, String clave) {
        //String usr="admin";
        if (usuario.equals("admin") && clave.equals("123")) {
            return true;
        }
        return false;
    }
}//fin
