
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

public class Mod_general {

    public static final String DIRVISTAS = "/upse/facturacion/vistas/";

    // Idioma actual del sistema (por defecto español)
    public static Locale idiomaActual = new Locale("es");

    public static ResourceBundle getBundle() {
        return ResourceBundle.getBundle("idioma.messages", idiomaActual);
    }

    // Mensaje informativo
    public static void fun_mensajeInformacion(String mensaje) {
        ResourceBundle b = getBundle();
        Alert alertInfo = new Alert(AlertType.INFORMATION);
        alertInfo.setTitle(b.containsKey("msg.titulo.info") ? b.getString("msg.titulo.info") : "Información");
        alertInfo.setHeaderText(null);
        alertInfo.setContentText(mensaje);
        alertInfo.showAndWait();
    }

    // Mensaje de error
    public static void fun_mensajeError(String mensaje) {
        ResourceBundle b = getBundle();
        Alert alertError = new Alert(AlertType.ERROR);
        alertError.setTitle(b.containsKey("msg.titulo.error") ? b.getString("msg.titulo.error") : "Error");
        alertError.setHeaderText(null);
        alertError.setContentText(mensaje);
        alertError.showAndWait();
    }

    // Mensaje de advertencia
    public static void fun_mensajeAdvertencia(String mensaje) {
        ResourceBundle b = getBundle();
        Alert alertWarn = new Alert(AlertType.WARNING);
        alertWarn.setTitle(b.containsKey("msg.titulo.advertencia") ? b.getString("msg.titulo.advertencia") : "Advertencia");
        alertWarn.setHeaderText(null);
        alertWarn.setContentText(mensaje);
        alertWarn.showAndWait();
    }

    // Mensaje de confirmación (Sí/No)
    public static boolean fun_confirmacion(String mensaje) {
        ResourceBundle b = getBundle();
        Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
        alertConfirm.setTitle(b.containsKey("msg.titulo.confirmacion") ? b.getString("msg.titulo.confirmacion") : "Confirmación");
        alertConfirm.setHeaderText(null);
        alertConfirm.setContentText(mensaje);
        Optional<ButtonType> result = alertConfirm.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    // Navegación con teclas
    public static void detectarTecla(Node nodo, KeyCode tecla, Node nodoAFoco) {
        nodo.setOnKeyPressed(event -> {
            if (event.getCode() == tecla) {
                if (nodoAFoco != null) {
                    nodoAFoco.requestFocus();
                }
                if (tecla == KeyCode.TAB) {
                    event.consume();
                }
            }
        });
    }
}
