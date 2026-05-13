package upse.facturacion.controlador;

import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;
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

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        confirmarSalida(stage);

        // Cargar Login CON bundle desde el inicio para que %clave funcione
        ResourceBundle bundle = Mod_general.getBundle();
        FXMLLoader loader = new FXMLLoader(
            App.class.getResource("/upse/facturacion/vistas/Login.fxml"), bundle);
        Pane ventana = loader.load();

        Scene scene = new Scene(ventana);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/upse/facturacion/recursos/cafelogo.png"));
        stage.setTitle("--OASIS COFFEE--");
        stage.setResizable(false);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        ResourceBundle bundle = Mod_general.getBundle();
        FXMLLoader fxmlLoader = new FXMLLoader(
            App.class.getResource(fxml + ".fxml"), bundle);
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public void confirmarSalida(Stage stage) {
        stage.setOnCloseRequest(event -> {
            String titulo = "Confirmacion";
            String mensaje = "Esta seguro que desea cerrar el sistema?";
            try {
                ResourceBundle bundle = Mod_general.getBundle();
                titulo = bundle.getString("msg.titulo.confirmacion");
                mensaje = bundle.getString("msg.salida");
            } catch (Exception e) {}

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(titulo);
            alert.setContentText(mensaje);
            Optional<ButtonType> result = alert.showAndWait();
            if (!(result.isPresent() && result.get() == ButtonType.OK)) {
                event.consume();
            }
        });
    }
}
