package upse.facturacion.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import upse.facturacion.MAD.Mad_Clientes;
import upse.facturacion.general.Mod_general;
import static upse.facturacion.general.Mod_general.fun_mensajeError;
import upse.facturacion.modelo.Cliente;

public class ClienteController implements Initializable {

    @FXML
    private Button btn_nuevo;
    @FXML
    private TextField txt_buscarCliente;
    @FXML
    private RadioButton rad_cedula;
    @FXML
    private RadioButton rad_nombres;
    @FXML
    private TableView<Cliente> tbl_clientes;
    @FXML
    private TableColumn<?, ?> col_cedula;
    @FXML
    private TableColumn<?, ?> col_nombres;
    @FXML
    private TableColumn<?, ?> col_direccion;
    @FXML
    private Button btn_cerrar;
    @FXML
    private AnchorPane dataPaneCliente;
    @FXML
    private Text lbl_tituloBuscar;

    private ToggleGroup grupoBusqueda;
    private ObservableList<Cliente> listaClientes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (rb != null) {
            aplicarIdioma(rb);
        }

        this.col_cedula.setCellValueFactory(new PropertyValueFactory<>("cli_cedula"));
        this.col_nombres.setCellValueFactory(new PropertyValueFactory<>("cli_nombres"));
        this.col_direccion.setCellValueFactory(new PropertyValueFactory<>("cli_direccion"));

        cargarClientes();

        grupoBusqueda = new ToggleGroup();
        rad_cedula.setToggleGroup(grupoBusqueda);
        rad_nombres.setToggleGroup(grupoBusqueda);
        rad_cedula.setSelected(true);

        // ✅ Filtrar al escribir
        txt_buscarCliente.textProperty().addListener((obs, oldValue, newValue) -> {
            filtrarTabla(newValue);
        });

        // ✅ Filtrar al cambiar radio button
        grupoBusqueda.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            filtrarTabla(txt_buscarCliente.getText());
        });
    }

    private void filtrarTabla(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            tbl_clientes.setItems(listaClientes);
            return;
        }

        String criterioBajo = criterio.toLowerCase().trim();

        ObservableList<Cliente> listaFiltrada = listaClientes.filtered(c -> {
            if (rad_cedula.isSelected()) {
                return c.getCli_cedula().toLowerCase().contains(criterioBajo);
            } else {
                String nombreCompleto = (c.getCli_nombres() + " " + c.getCli_apellidos()).toLowerCase();
                return nombreCompleto.contains(criterioBajo);
            }
        });

        tbl_clientes.setItems(listaFiltrada);
    }

    private void aplicarIdioma(ResourceBundle rb) {
        try {
            if (lbl_tituloBuscar != null) {
                lbl_tituloBuscar.setText(t(rb, "clientes.titulo.buscar", "BUSCAR CLIENTES"));
            }
            if (btn_nuevo != null) {
                btn_nuevo.setText(t(rb, "clientes.btn.nuevo", "Nuevo Cliente"));
            }
            if (btn_cerrar != null) {
                btn_cerrar.setText(t(rb, "clientes.btn.cancelar", "Cancelar"));
            }
            if (rad_cedula != null) {
                rad_cedula.setText(t(rb, "clientes.radio.cedula", "Cédula"));
            }
            if (rad_nombres != null) {
                rad_nombres.setText(t(rb, "clientes.radio.nombres", "Nombres"));
            }
            if (col_cedula != null) {
                col_cedula.setText(t(rb, "clientes.col.cedula", "Cédula"));
            }
            if (col_nombres != null) {
                col_nombres.setText(t(rb, "clientes.col.nombres", "Nombres"));
            }
            if (col_direccion != null) {
                col_direccion.setText(t(rb, "clientes.col.direccion", "Dirección"));
            }
        } catch (Exception e) {
        }
    }

    private String t(ResourceBundle rb, String key, String fallback) {
        try {
            return rb.getString(key);
        } catch (Exception e) {
            return fallback;
        }
    }

    @FXML
    private void acc_nuevo(ActionEvent event) {
        this.abrirModal("");
    }

    @FXML
    private void acc_cerrar(ActionEvent event) {
        this.dataPaneCliente.setVisible(false);
    }

    private void cargarClientes() {
        try {
            Mad_Clientes madCliente = new Mad_Clientes();
            this.tbl_clientes.getItems().clear();
            this.listaClientes = madCliente.getClientes();
            this.tbl_clientes.setItems(listaClientes);
        } catch (Exception e) {
            fun_mensajeError(e.getMessage());
        }
    }

    private void abrirModal(String cedula) {
        try {
            ResourceBundle bundle = Mod_general.getBundle();
            FXMLLoader loader = new FXMLLoader(
                    App.class.getResource("/upse/facturacion/vistas/Clientes.fxml"),
                    bundle
            );
            Parent root = loader.load();

            ClientesController controlador = loader.getController();
            controlador.recuperarCliente(cedula); // ✅ ahora usa la cédula

            Scene scene = new Scene(root);
            Stage mystage = new Stage();
            mystage.initModality(Modality.APPLICATION_MODAL);
            mystage.initStyle(StageStyle.UNDECORATED);
            mystage.setScene(scene);
            mystage.setResizable(false);
            mystage.showAndWait();

            this.cargarClientes(); // refrescar tabla al cerrar modal
        } catch (Exception e) {
            fun_mensajeError(e.getMessage());
        }
    }

    @FXML
    private void acc_Clickedmause(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            Cliente objSeleccionado = this.tbl_clientes.getSelectionModel().getSelectedItem();
            if (objSeleccionado != null) {
                this.abrirModal(objSeleccionado.getCli_cedula()); // ✅ pasar cédula
            }
        }
    }

}
