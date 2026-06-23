package upse.facturacion.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import upse.facturacion.MAD.Mad_Clientes;
import upse.facturacion.general.Mod_general;
import upse.facturacion.modelo.Cliente;

public class ClientesController implements Initializable {

    @FXML
    private Button btn_guardarCliente;
    @FXML
    private TextField txt_cedula;
    @FXML
    private TextField txt_direccion;
    @FXML
    private TextField txt_nombreCliente;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_telefono;
    @FXML
    private AnchorPane apClientes;
    @FXML
    private CheckBox chk_validarCedula;
    @FXML
    private Button btn_cerrar;
    // Labels de texto (Text no se traduce vía %clave automáticamente)
    @FXML
    private Text lbl_tituloClientes;
    @FXML
    private Text lbl_agregar;
    @FXML
    private Text lbl_cedula;
    @FXML
    private Text lbl_nombres;
    @FXML
    private Text lbl_telefono;
    @FXML
    private Text lbl_email;
    @FXML
    private Text lbl_direccion;

    private ResourceBundle bundle;
    int bandera;
    Mad_Clientes madCliente = new Mad_Clientes();

    @FXML
    private CheckBox chk_estado;
    @FXML
    private TextField txt_apellido;
    @FXML
    private Text lbl_apellidos_field;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.chk_estado.setSelected(true);

        this.bundle = (rb != null) ? rb : Mod_general.getBundle();

        // Aplicar traducciones a nodos Text
        aplicarIdioma();

        txt_cedula.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                txt_cedula.setText(newVal.replaceAll("[^\\d]", ""));
            }
            if (txt_cedula.getText().length() > 10) {
                txt_cedula.setText(txt_cedula.getText().substring(0, 10));
            }
        });

        txt_telefono.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                txt_telefono.setText(newVal.replaceAll("[^\\d]", ""));
            }
            if (txt_telefono.getText().length() > 10) {
                txt_telefono.setText(txt_telefono.getText().substring(0, 10));
            }
        });

        Mod_general.detectarTecla(txt_cedula, KeyCode.ENTER, txt_nombreCliente);
        Mod_general.detectarTecla(txt_nombreCliente, KeyCode.ENTER, txt_telefono);
        Mod_general.detectarTecla(txt_telefono, KeyCode.ENTER, txt_email);
        Mod_general.detectarTecla(txt_email, KeyCode.ENTER, txt_direccion);
        Mod_general.detectarTecla(txt_direccion, KeyCode.ENTER, btn_guardarCliente);
        txt_cedula.requestFocus();
    }

    private void aplicarIdioma() {
        try {
            if (lbl_tituloClientes != null) {
                lbl_tituloClientes.setText(t("clientes.titulo", "CLIENTES"));
            }
            if (lbl_agregar != null) {
                lbl_agregar.setText(t("clientes.agregar", "Agregar Clientes"));
            }
            if (lbl_cedula != null) {
                lbl_cedula.setText(t("lbl_cedula_field", "Cédula:"));
            }
            if (lbl_nombres != null) {
                lbl_nombres.setText(t("lbl_nombres_field", "Nombres:"));
            }
            if (lbl_telefono != null) {
                lbl_telefono.setText(t("lbl_telefono_field", "Teléfono:"));
            }
            if (lbl_email != null) {
                lbl_email.setText(t("lbl_email_field", "E-mail:"));
            }
            if (lbl_direccion != null) {
                lbl_direccion.setText(t("lbl_direccion_field", "Dirección:"));
            }
            if (btn_guardarCliente != null) {
                btn_guardarCliente.setText(t("clientes.btn.guardar", "Guardar cliente"));
            }
            if (btn_cerrar != null) {
                btn_cerrar.setText(t("clientes.btn.cancelar", "Cancelar"));
            }
            if (chk_validarCedula != null) {
                chk_validarCedula.setText(t("lbl_validar", "Validar"));
            }
            if (lbl_tituloClientes != null) {
                lbl_tituloClientes.setText(t("clientes.titulo", "CLIENTES"));
            }
        } catch (Exception e) {
            // ignorar
        }
    }

    private String t(String key, String fallback) {
        try {
            return bundle.getString(key);
        } catch (Exception e) {
            return fallback;
        }
    }

    @FXML
    private void acc_guardarCliente(ActionEvent event) {
        try {
            if (!fun_validar()) {
                return; // ← agregar validación
            }
            String estado = this.chk_estado.isSelected() ? "A" : "E";

            Cliente obj = new Cliente(
                    this.bandera, // 0 = insert, >0 = update
                    this.txt_cedula.getText(),
                    this.txt_nombreCliente.getText(),
                    this.txt_apellido.getText(),
                    this.txt_direccion.getText(),
                    this.txt_telefono.getText(),
                    this.txt_email.getText(),
                    estado
            );

            if (this.madCliente.mantCliente(obj)) {
                Mod_general.fun_mensajeInformacion(
                        bandera == 0 ? "Se registró con éxito" : "Se actualizó con éxito"
                );
            } else {
                Mod_general.fun_mensajeError(
                        bandera == 0 ? "Error al registrar" : "Error al actualizar"
                );
                return; // ← no cerrar si falló
            }

            this.cerrarFormulario();
        } catch (Exception e) {
            Mod_general.fun_mensajeError(e.getMessage());
        }
    }

    private boolean fun_validar() {
        if (txt_cedula.getText().isEmpty()) {
            Mod_general.fun_mensajeError(t("val.cedula.requerida", "Debe ingresar la cédula."));
            txt_cedula.requestFocus();
            return false;
        }
        if (txt_nombreCliente.getText().isEmpty()) {
            Mod_general.fun_mensajeError(t("val.nombre.requerido", "Debe ingresar el nombre."));
            txt_nombreCliente.requestFocus();
            return false;
        }
        if (txt_telefono.getText().isEmpty()) {
            Mod_general.fun_mensajeError(t("val.telefono.requerido", "Debe ingresar el teléfono."));
            txt_telefono.requestFocus();
            return false;
        }
        if (txt_email.getText().isEmpty()) {
            Mod_general.fun_mensajeError(t("val.email.requerido", "Debe ingresar el correo electrónico."));
            txt_email.requestFocus();
            return false;
        }
        if (txt_direccion.getText().isEmpty()) {
            Mod_general.fun_mensajeError(t("val.direccion.requerida", "Debe ingresar la dirección."));
            txt_direccion.requestFocus();
            return false;
        }
        if (!txt_cedula.getText().matches("\\d{10}")) {
            Mod_general.fun_mensajeError(t("val.cedula.formato", "La cédula debe contener exactamente 10 dígitos numéricos."));
            txt_cedula.requestFocus();
            return false;
        }
        if (!txt_telefono.getText().matches("\\d{10}")) {
            Mod_general.fun_mensajeError(t("val.telefono.formato", "El teléfono debe contener exactamente 10 dígitos numéricos."));
            txt_telefono.requestFocus();
            return false;
        }
        return true;
    }

    private void fun_limpiar() {
        txt_cedula.clear();
        txt_nombreCliente.clear();
        txt_telefono.clear();
        txt_email.clear();
        txt_direccion.clear();
        txt_cedula.requestFocus();
    }

    public void recuperarCliente(String id) {

        if (id.equals("")) {
            bandera = 0;
            fun_limpiar();
        } else {
            bandera = 1;
            recuperarcliente(id);
        }
    }

    private void recuperarcliente(String id) {
        Cliente objCliente = this.madCliente.buscaClientexId(Integer.parseInt(id));
        if (objCliente != null) {
            this.bandera = objCliente.getCli_id();
            this.txt_cedula.setText(objCliente.getCli_cedula());
            this.txt_nombreCliente.setText(objCliente.getCli_nombres());
            this.txt_apellido.setText(objCliente.getCli_apellidos());
            this.txt_direccion.setText(objCliente.getCli_direccion());
            this.txt_telefono.setText(objCliente.getCli_telefono());
            this.txt_email.setText(objCliente.getCli_correo());
            String estado = "A";
            if (objCliente.getCli_estado().equalsIgnoreCase(estado)) {
                System.out.println("ok0");
                this.chk_estado.setSelected(true);
            } else {
                System.out.println("ok1");
                this.chk_estado.setSelected(false);
            }
        }
    }

    @FXML
    private void acc_cerrar(ActionEvent event) {
        this.cerrarFormulario();
    }

    private void cerrarFormulario() {
        try {
            Stage stage = (Stage) this.btn_cerrar.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            Mod_general.fun_mensajeError(e.getMessage());
        }
    }
}
