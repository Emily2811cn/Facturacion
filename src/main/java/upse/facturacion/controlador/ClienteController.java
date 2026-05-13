package upse.facturacion.controlador;

import java.net.URL;
import java.util.ResourceBundle;
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
import upse.facturacion.general.BD;
import upse.facturacion.general.Mod_general;
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
    // Texto del título (Text no soporta %clave nativamente, lo traducimos aquí)
    @FXML
    private Text lbl_tituloBuscar;

    private ToggleGroup grupoBusqueda;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Aplicar traducciones a los nodos Text que no se traducen vía %clave
        if (rb != null) {
            aplicarIdioma(rb);
        }

        this.col_cedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        this.col_nombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        this.col_direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        cargarClientes();

        grupoBusqueda = new ToggleGroup();
        rad_cedula.setToggleGroup(grupoBusqueda);
        rad_nombres.setToggleGroup(grupoBusqueda);
        rad_cedula.setSelected(true);

        txt_buscarCliente.textProperty().addListener((obs, oldValue, newValue) -> {
            filtrarTabla(newValue);
        });

        grupoBusqueda.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            filtrarTabla(txt_buscarCliente.getText());
        });
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
            // ignorar
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
        this.tbl_clientes.getItems().clear();
        this.tbl_clientes.getItems().addAll(BD.listaClientes);
    }

    private void abrirModal(String id) {
        try {
            ResourceBundle bundle = Mod_general.getBundle();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/upse/facturacion/vistas/Clientes.fxml"), bundle);
            Parent root = loader.load();
            ClientesController controlador = loader.getController();
            controlador.recuperarCliente(id);
            Scene scene = new Scene(root);
            Stage mystage = new Stage();
            mystage.initModality(Modality.APPLICATION_MODAL);
            mystage.initStyle(StageStyle.UNDECORATED);
            mystage.setScene(scene);
            mystage.setResizable(false);
            mystage.showAndWait();
            cargarClientes();
            tbl_clientes.refresh(); // 🔹 refrescar tabla
        } catch (Exception e) {
            Mod_general.fun_mensajeError("Error al abrir modal");
        }
    }

    @FXML
    private void acc_Clickedmause(MouseEvent event) {
        try {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Cliente objSeleccionado = this.tbl_clientes.getSelectionModel().getSelectedItem();
                if (objSeleccionado != null) {
                    this.abrirModal(objSeleccionado.getCedula());
                }
            }
        } catch (Exception e) {
        }
    }

    private void filtrarTabla(String criterio) {
        tbl_clientes.getItems().clear();
        if (criterio == null || criterio.trim().isEmpty()) {
            tbl_clientes.getItems().addAll(BD.listaClientes);
            return;
        }
        for (Cliente c : BD.listaClientes) {
            if (rad_cedula.isSelected()) {
                if (c.getCedula().toLowerCase().contains(criterio.toLowerCase())) {
                    tbl_clientes.getItems().add(c);
                }
            } else if (rad_nombres.isSelected()) {
                if (c.getNombres().toLowerCase().contains(criterio.toLowerCase())) {
                    tbl_clientes.getItems().add(c);
                }
            }
        }
    }
}
