package jack.command;

import jack.storage.Storage;
import jack.task.TaskList;
import jack.ui.Ui;

/**
 * Displays all tasks currently in the task list.
 */
public class ListCommand extends Command {

    /**
     * Displays the task list to the user.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return tasks.toDisplayString();
    }
}
