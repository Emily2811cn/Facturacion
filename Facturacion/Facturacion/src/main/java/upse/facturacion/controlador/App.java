package upse.facturacion.controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * @author Miguel
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // 1. Configuración de Idioma
            Locale locale = new Locale("es", "EC");
            ResourceBundle bundle = ResourceBundle.getBundle("upse.facturacion.idiomas.textos", locale);

            // 2. Carga del FXML con validación de ruta
            URL fxmlLocation = getClass().getResource("/upse/facturacion/vistas/Login.fxml");
            if (fxmlLocation == null) {
                throw new IOException("No se encontró el archivo FXML en: /upse/facturacion/vistas/Login.fxml");
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            loader.setResources(bundle);

            Pane ventana = loader.load();
            Scene scene = new Scene(ventana);
            
            // 3. Configuración del Stage
            stage.setScene(scene);
            stage.setTitle(bundle.getString("lbl.acceso"));
            stage.setResizable(false);

            // 4. Carga del Icono con validación (Evita el error de Invalid URL)
            URL iconURL = getClass().getResource("/recursos/ico.png");
            if (iconURL != null) {
                stage.getIcons().add(new Image(iconURL.toExternalForm()));
            } else {
                System.out.println("Advertencia: No se encontró el icono en /recursos/ico.png");
            }

            // 5. Evento de cierre
            stage.setOnCloseRequest(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(bundle.getString("btn.cerrar"));
                alert.setHeaderText(null);
                alert.setContentText(bundle.getString("msg.confirmarSalida"));
                
                if (!(alert.showAndWait().filter(t -> t == ButtonType.OK).isPresent())) {
                    event.consume();
                }
            });

            stage.show();

        } catch (IOException e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}