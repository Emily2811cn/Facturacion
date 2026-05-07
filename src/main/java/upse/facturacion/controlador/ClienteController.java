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

    private ToggleGroup grupoBusqueda;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.col_cedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        this.col_nombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        this.col_direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        this.dataPaneCliente.getStylesheets().add(getClass().getResource("/upse/facturacion/vistas/mystilo.css").toExternalForm());
        this.dataPaneCliente.getStyleClass().add("fondo");
        btn_cerrar.getStyleClass().add("boton");
        btn_nuevo.getStyleClass().add("boton");
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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/upse/facturacion/vistas/Clientes.fxml"));
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
            this.cargarClientes();
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
