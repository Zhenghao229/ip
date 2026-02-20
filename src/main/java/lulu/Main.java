package lulu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lulu.ui.MainWindow;

/**
 * A GUI for Lulu using FXML.
 */
public class Main extends Application {

    private Lulu lulu = new Lulu("data/lulu.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            Scene scene = new Scene(ap);
            scene.getStylesheets().add(Main.class.getResource("/view/Style.css").toExternalForm());

            stage.setTitle("LULU Capybara - Cozy Task Manager");
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setLulu(lulu); // inject the Lulu instance

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
