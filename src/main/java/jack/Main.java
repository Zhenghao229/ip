package jack;

import jack.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Jack using FXML.
 */
public class Main extends Application {

    private Jack jack = new Jack("data/jack.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            Scene scene = new Scene(ap);
            stage.setScene(scene);

            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setJack(jack); // inject the Jack instance

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
