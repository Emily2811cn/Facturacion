/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package upse.facturacion.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static upse.facturacion.general.BD.clientes;
import static upse.facturacion.general.Mod_general.fun_mensajeError;
import upse.facturacion.general.Mod_general;
import upse.facturacion.modelo.Cliente;

/**
 * @author Miguel
 */
public class ClientesController implements Initializable {

    @FXML private Button btn_cerrar;
    @FXML private AnchorPane dataPaneCliente;
    @FXML private Button btn_nuevo;
    @FXML private TextField txt_buscar;
    @FXML private RadioButton rad_nombres;
    @FXML private RadioButton rad_cedula;
    @FXML private TableView<Cliente> tb_clientes;
    @FXML private TableColumn<Cliente, String> col_cedula;
    @FXML private TableColumn<Cliente, String> col_nombres;
    @FXML private TableColumn<Cliente, String> col_direccion;

    private ToggleGroup grupoBusqueda;
    private ResourceBundle rb;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        
        try {
            this.dataPaneCliente.getStylesheets().add(getClass().getResource("/upse/facturacion/styles/myStyle.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("No se pudo cargar el CSS: " + e.getMessage());
        }

        // Configurar columnas
        this.col_cedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        this.col_nombres.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.col_direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        // Configurar RadioButtons
        grupoBusqueda = new ToggleGroup();
        this.rad_cedula.setToggleGroup(grupoBusqueda);
        this.rad_nombres.setToggleGroup(grupoBusqueda);
        this.rad_cedula.setSelected(true);

        // Lógica de búsqueda
        this.txt_buscar.textProperty().addListener((obs, oldValue, newValue) -> {
            filtrarTabla(newValue);
        });

        this.grupoBusqueda.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            filtrarTabla(this.txt_buscar.getText());
        });

        this.cargarClientes();
    }

    private void filtrarTabla(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            this.tb_clientes.setItems(FXCollections.observableArrayList(clientes));
            return;
        }

        ObservableList<Cliente> listaFiltrada = FXCollections.observableArrayList();
        String textoFiltro = filtro.toLowerCase();

        for (Cliente c : clientes) {
            if (rad_cedula.isSelected()) {
                if (c.getCedula().toLowerCase().contains(textoFiltro)) {
                    listaFiltrada.add(c);
                }
            } else if (rad_nombres.isSelected()) {
                if (c.getNombre().toLowerCase().contains(textoFiltro)) {
                    listaFiltrada.add(c);
                }
            }
        }
        this.tb_clientes.setItems(listaFiltrada);
    }

    private void cargarClientes() {
        this.tb_clientes.setItems(FXCollections.observableArrayList(clientes));
    }

    @FXML 
    private void acc_cerrar(ActionEvent event) { 
        this.dataPaneCliente.setVisible(false); 
    }

    @FXML 
    private void acc_nuevo(ActionEvent event) { 
        this.abrirModal(""); 
    }

    private void abrirModal(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Mod_general.DIRVISTAS + "Cliente.fxml"));
            loader.setResources(this.rb);
            Parent root = loader.load();
            
            ClienteController controlador = loader.getController();
            controlador.recuperarCliente(id);
            
            Scene scene = new Scene(root);
            Stage mystage = new Stage();
            mystage.initModality(Modality.APPLICATION_MODAL);
            mystage.initStyle(StageStyle.UNDECORATED);
            mystage.setScene(scene);
            mystage.showAndWait();
            
            this.cargarClientes();
        } catch (Exception e) {
            fun_mensajeError("Error: " + e.getMessage());
        }
    }

    @FXML
    private void acc_Clickedmause(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            Cliente sel = this.tb_clientes.getSelectionModel().getSelectedItem();
            if (sel != null) {
                this.abrirModal(sel.getCedula());
            }
        }
    }
}