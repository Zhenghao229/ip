package jack.command;

import jack.JackException;
import jack.storage.Storage;
import jack.task.TaskList;
import jack.ui.Ui;

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
        this.index = index;
    }

    /**
     * Marks the specified task as not done and saves the updated list.
     *
     * @throws JackException If the index is invalid.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws JackException {
        tasks.checkIndex(index);
        tasks.get(index).markAsNotDone();
        storage.save(tasks.getInternalList());
        return "OK, I've marked this jack.task as not done yet:\n  " + tasks.get(index);
    }
}
