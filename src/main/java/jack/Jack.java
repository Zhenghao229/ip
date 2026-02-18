package jack;

import jack.command.Command;
import jack.parser.Parser;
import jack.storage.Storage;
import jack.task.TaskList;
import jack.ui.Ui;

public class Jack {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    public Jack(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);

        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.load());
        } catch (JackException e) {
            ui.showError(e.getMessage());
            loadedTasks = new TaskList();
        }
        tasks = loadedTasks;
    }

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
            } catch (JackException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            return c.execute(tasks, ui, storage);
        } catch (Exception e) {
            return "OOPS!!! " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        new Jack("data/jack.txt").run();
    }
}
