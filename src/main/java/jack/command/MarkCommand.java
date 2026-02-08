package jack.command;

import jack.JackException;
import jack.storage.Storage;
import jack.task.TaskList;
import jack.ui.Ui;

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
        this.index = index;
    }

    /**
     * Marks the specified task as done and saves the updated list.
     *
     * @throws JackException If the index is invalid.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws JackException {
        tasks.checkIndex(index);
        tasks.get(index).markAsDone();
        storage.save(tasks.getInternalList());
        return "Nice! I've marked this jack.task as done:\n  " + tasks.get(index);
    }
}
