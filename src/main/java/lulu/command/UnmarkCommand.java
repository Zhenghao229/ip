package lulu.command;

import lulu.LuluException;
import lulu.storage.Storage;
import lulu.task.TaskList;
import lulu.ui.Ui;

/**
 * Marks a task in the task list as not completed.
 */
public class UnmarkCommand extends Command {
    private final int index; // 0-based index

    /**
     * Creates an {@code UnmarkCommand} for the given task index.
     *
     * @param index Index of the task to unmark (0-based).
     */
    public UnmarkCommand(int index) {
        assert index >= 0 : "Index should already be converted to 0-based and non-negative";
        this.index = index;
    }

    /**
     * Marks the specified task as not done and saves the updated list.
     *
     * @throws LuluException If the index is invalid.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws LuluException {
        assert tasks != null && ui != null && storage != null : "Dependencies must not be null";
        tasks.checkIndex(index);
        tasks.get(index).markAsNotDone();
        storage.save(tasks.getInternalList());
        return "OK, I've marked this task as not done yet:\n  " + tasks.get(index);
    }
}
