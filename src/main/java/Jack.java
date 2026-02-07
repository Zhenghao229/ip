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

        while (true) {
            try {
                String input = ui.readCommand();
                ui.showLine();

                if (input.equals("bye")) {
                    ui.showGoodbye();
                    break;
                }

                String response = Parser.handle(input, tasks, storage);
                ui.showMessage(response);

            } catch (JackException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Jack("data/jack.txt").run();
    }
}
