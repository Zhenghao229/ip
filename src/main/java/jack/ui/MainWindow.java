package jack.ui;

import jack.Jack;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Jack jack;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/labi.png"));
    private Image jackImage = new Image(this.getClass().getResourceAsStream("/images/jack.png"));

    /**
     * Initializes the main window. Runs automatically after FXML is loaded.
     * Shows a welcome message and binds the scroll pane to the dialog container.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        dialogContainer.getChildren().add(
                DialogBox.getJackDialog("Hello! I'm Jack. What can I do for you today?", jackImage)
        );
    }

    /**
     * Injects the Jack instance
     */
    public void setJack(Jack j) {
        jack = j;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = jack.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getJackDialog(response, jackImage)
        );
        userInput.clear();

        if (input.trim().equalsIgnoreCase("bye")) {
            javafx.animation.PauseTransition pause;
            pause = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(1.0));
            pause.setOnFinished(e -> javafx.application.Platform.exit());
            pause.play();
        }

    }
}
