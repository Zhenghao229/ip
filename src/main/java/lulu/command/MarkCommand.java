package lulu.command;

import lulu.LuluException;
import lulu.storage.Storage;
import lulu.task.TaskList;
import lulu.ui.Ui;

/**
 * Marks a task in the task list as completed.
 */
public class MarkCommand extends Command {
    private final int index; // 0-based

    /**
     * Creates a {@code MarkCommand} for the given task index.
     *
     * @param index Index of the task to mark as done (0-based).
     */
    public MarkCommand(int index) {
        assert index >= 0 : "Index should already be converted to 0-based and non-negative";
        this.index = index;
    }

    /**
     * Marks the specified task as done and saves the updated list.
     *
     * @throws LuluException If the index is invalid.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws LuluException {
        assert tasks != null && ui != null && storage != null : "Dependencies must not be null";
        tasks.checkIndex(index);
        tasks.get(index).markAsDone();
        storage.save(tasks.getInternalList());
        return "Nice! I've marked this task as done:\n  " + tasks.get(index);
    }
}
