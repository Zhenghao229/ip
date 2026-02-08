package jack.command;

import jack.storage.Storage;
import jack.task.TaskList;
import jack.ui.Ui;

/**
 * Terminates the application.
 */
public class ExitCommand extends Command {

    /**
     * Displays the farewell message.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Indicates that this command exits the application.
     *
     * @return True always.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
