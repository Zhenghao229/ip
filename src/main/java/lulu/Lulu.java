package lulu;

import lulu.command.Command;
import lulu.parser.Parser;
import lulu.storage.Storage;
import lulu.task.TaskList;
import lulu.ui.Ui;

/**
 * The main entry point of the Lulu application.
 * Lulu loads tasks from storage, reads user commands, and executes them to manage tasks.
 */
public class Lulu {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Constructs a Lulu instance and loads tasks from the given file path.
     *
     * @param filePath Path to the save file used for loading and saving tasks.
     */
    public Lulu(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);

        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.load());
        } catch (LuluException e) {
            ui.showError(e.getMessage());
            loadedTasks = new TaskList();
        }
        tasks = loadedTasks;
    }

    /**
     * Runs the main command loop of the application until the user exits.
     * Reads commands, parses them into {@link Command} objects, executes them,
     * and displays the result to the user.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();

                Command c = Parser.parse(fullCommand);
                String result = c.execute(tasks, ui, storage);
                ui.showMessage(result);

                isExit = c.isExit();
            } catch (LuluException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Returns the response for a given user input. This is used by the GUI
     * to obtain a single response per input without running the full loop.
     *
     * @param input User command text.
     * @return The response message to display.
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            return c.execute(tasks, ui, storage);
        } catch (LuluException e) {
            return "OOPS!!! " + e.getMessage();
        } catch (Exception e) {
            // Fallback for unexpected errors
            return "OOPS!!! I'm sorry, but I don't know what that means :-(";
        }
    }

    /**
     * Launches Lulu using the default save file path.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        new Lulu("data/lulu.txt").run();
    }
}
