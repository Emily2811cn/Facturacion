
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.facturacion.general;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import java.util.Optional;
import java.util.Locale;
import javafx.scene.control.ButtonType;
import java.util.ResourceBundle;

/**
 *
 * @author EMILY CRUZ
 */
public class Mod_general {
    public static final String DIRVISTAS="/upse/facturacion/vistas/";
// Al inicio de la clase, después de DIRVISTAS:
public static Locale idiomaActual = new Locale("es");

public static ResourceBundle getBundle() {
    return ResourceBundle.getBundle("idioma.messages", idiomaActual);
}
    // Mensaje informativo
    public static void fun_mensajeInformacion(String mensaje){
        Alert alertInfo = new Alert(AlertType.INFORMATION);
        alertInfo.setTitle("Mensaje del Sistema");
        alertInfo.setHeaderText(null);
        alertInfo.setContentText(mensaje);
        alertInfo.showAndWait();
    }

    // Mensaje de error
    public static void fun_mensajeError(String mensaje){
        Alert alertError = new Alert(AlertType.ERROR);
        alertError.setTitle("Error del Sistema");
        alertError.setHeaderText(null);
        alertError.setContentText(mensaje);
        alertError.showAndWait();
    }

    // Mensaje de advertencia
    public static void fun_mensajeAdvertencia(String mensaje){
        Alert alertWarn = new Alert(AlertType.WARNING);
        alertWarn.setTitle("Advertencia");
        alertWarn.setHeaderText(null);
        alertWarn.setContentText(mensaje);
        alertWarn.showAndWait();
    }

    // Mensaje de confirmación (Sí/No)
    public static boolean fun_confirmacion(String mensaje){
        Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
        alertConfirm.setTitle("Confirmación");
        alertConfirm.setHeaderText(null);
        alertConfirm.setContentText(mensaje);

        Optional<ButtonType> result = alertConfirm.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    // Navegación con teclas
    public static void detectarTecla(Node nodo, KeyCode tecla, Node nodoAFoco){
        nodo.setOnKeyPressed(event -> {
            if(event.getCode() == tecla){
                if(nodoAFoco != null){
                    nodoAFoco.requestFocus();
                }
                if(tecla == KeyCode.TAB){
                    event.consume();
                }
            }
        });
    }
}
