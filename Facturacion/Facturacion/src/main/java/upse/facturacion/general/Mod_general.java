/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.facturacion.general;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;

/**
 * @author Miguel
 */
public class Mod_general {

    // Ruta base para las vistas FXML
    public static final String DIRVISTAS = "/upse/facturacion/vistas/";

    /**
     * Muestra un diálogo de alerta de tipo informativo
     */
    public static void fun_mensajeInformacion(String mensaje) {
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        alertInfo.setTitle("Mensaje del Sistema");
        alertInfo.setHeaderText(null);
        alertInfo.setContentText(mensaje);
        alertInfo.showAndWait();
    }

    /**
     * Muestra un diálogo de alerta de tipo error
     */
    public static void fun_mensajeError(String mensaje) {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setTitle("Mensaje del Sistema");
        alertError.setHeaderText(null);
        alertError.setContentText(mensaje);
        alertError.showAndWait();
    }

    /**
     * Detecta la pulsación de una tecla específica en un nodo y traslada el foco a otro
     * @param nodo El campo actual
     * @param tecla La tecla a detectar (ej: KeyCode.ENTER)
     * @param nodoAFoco El siguiente campo o botón que recibirá el foco
     */
    public static void detectarTecla(Node nodo, KeyCode tecla, Node nodoAFoco) {
        nodo.setOnKeyPressed(event -> {
            if (event.getCode() == tecla) {
                if (nodoAFoco != null) {
                    nodoAFoco.requestFocus();
                }
                // Si la tecla es TAB, consumimos el evento para controlar el foco manualmente
                if (tecla == KeyCode.TAB) {
                    event.consume();
                }
            }
        });
    }
}