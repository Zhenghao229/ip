package lulu.command;


import lulu.LuluException;
import lulu.storage.Storage;
import lulu.task.Task;
import lulu.task.TaskList;
import lulu.ui.Ui;

/**
 * Indicates whether this command should terminate the application.
 * True if the application should exit, false otherwise.
 */
public class AddCommand extends Command {
    private final Task task;

    /**
     * Creates an {@code AddCommand} with the given task.
     *
     * @param task The task to be added.
     */
    public AddCommand(Task task) {
        assert task != null : "AddCommand must have a task";
        this.task = task;
    }

    /**
     * Adds the task to the list and saves the updated list.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws LuluException {
        assert tasks != null && ui != null && storage != null : "Dependencies must not be null";
        tasks.addUnique(task);
        storage.save(tasks.getInternalList());
        return "Ok ok~ I added it to your list:\n  " + task
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }
}
