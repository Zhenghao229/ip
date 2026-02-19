package lulu.command;

import lulu.storage.Storage;
import lulu.task.TaskList;
import lulu.ui.Ui;

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
