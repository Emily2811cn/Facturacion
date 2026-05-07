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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static upse.facturacion.general.BD.clientes;
import static upse.facturacion.general.Mod_general.fun_mensajeError;
import static upse.facturacion.general.Mod_general.fun_mensajeInformacion;
import upse.facturacion.modelo.Cliente;

/**
 * @author Miguel
 */
public class ClienteController implements Initializable {

    @FXML private Button btn_cerrar;
    @FXML private Button btn_grabar;
    @FXML private TextField txt_cedula;
    @FXML private TextField txt_nombres;
    @FXML private TextArea txt_direccion;
    @FXML private TextField txt_telefono;
    @FXML private TextField txt_correo;
    @FXML private CheckBox chk_validar;

    int bandera;
    private ResourceBundle rb;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
    }

    @FXML
    private void acc_grabar(ActionEvent event) {
        try {
            Cliente obj = new Cliente(
                this.txt_cedula.getText(), 
                this.txt_nombres.getText(),
                this.txt_direccion.getText(), 
                this.txt_telefono.getText(),
                this.txt_correo.getText()
            );

            if (bandera == 0) {
                clientes.add(obj);
            } else {
                for (int i = 0; i < clientes.size(); i++) {
                    if (clientes.get(i).getCedula().equals(this.txt_cedula.getText())) {
                        clientes.set(i, obj);
                        break;
                    }
                }
            }
            fun_mensajeInformacion(rb.getString("msg.exito.guardar"));
            this.cerrarFormulario();
        } catch (Exception e) {
            fun_mensajeError(e.getMessage());
        }
    }

    public void recuperarCliente(String id) {
        if (id.equals("")) {
            bandera = 0;
            this.txt_cedula.setEditable(true);
            this.txt_cedula.clear();
            this.txt_nombres.clear();
            this.txt_direccion.clear();
            this.txt_telefono.clear();
            this.txt_correo.clear();
        } else {
            bandera = 1;
            this.txt_cedula.setEditable(false);
            recuperarcliente(id);
        }
    }

    private void cerrarFormulario() {
        Stage stage = (Stage) this.txt_cedula.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void acc_cerrar(ActionEvent event) {
        this.cerrarFormulario();
    }

    private void recuperarcliente(String id) {
        for (Cliente c : clientes) {
            if (c.getCedula().equals(id)) {
                this.txt_cedula.setText(c.getCedula());
                this.txt_nombres.setText(c.getNombre());
                this.txt_direccion.setText(c.getDireccion());
                this.txt_telefono.setText(c.getTelefono());
                this.txt_correo.setText(c.getCorreo());
                break;
            }
        }
    }
}