package lulu.command;

import lulu.LuluException;
import lulu.storage.Storage;
import lulu.task.Task;
import lulu.task.TaskList;
import lulu.ui.Ui;

/**
 * Deletes a task from the task list by index.
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Creates a {@code DeleteCommand} for the given task index.
     *
     * @param index Index of the task to delete (0-based).
     */
    public DeleteCommand(int index) {
        assert index >= 0 : "Index should already be converted to 0-based and non-negative";
        this.index = index;
    }

    /**
     * Removes the specified task and saves the updated list.
     *
     * @throws LuluException If the index is invalid.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws LuluException {
        assert tasks != null && ui != null && storage != null : "Dependencies must not be null";
        tasks.checkIndex(index);
        Task removed = tasks.remove(index);
        storage.save(tasks.getInternalList());
        return "Alright~ I removed it. Your list feels lighter now \uD83C\uDF3F:\n  " + removed
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }
}
