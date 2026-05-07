package upse.facturacion.controlador;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import upse.facturacion.general.Mod_general;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        this.confirmarSalida(stage);

        // Selecciona el idioma (ejemplo: español)
        ResourceBundle bundle = Mod_general.getBundle();
        // Cargar el FXML con el bundle
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/upse/facturacion/vistas/Login.fxml"), bundle);
        Pane ventana = loader.load();

        Scene scene = new Scene(ventana);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/upse/facturacion/recursos/cafelogo.png"));
        stage.setTitle("--SISTEMA DE FACTURACIÓN--");
        stage.setResizable(false);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

public void confirmarSalida(Stage stage) {
        stage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setContentText("¿Esta seguro que desea cerrar el sistema?");
            Optional<ButtonType> result = alert.showAndWait();
            if (!(result.isPresent() && result.get() == ButtonType.OK)) {
                event.consume();//cancelar cierre
            }
        });
    }
}//fin
