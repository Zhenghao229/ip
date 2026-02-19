package jack.command;

import jack.JackException;
import jack.storage.Storage;
import jack.task.Task;
import jack.task.TaskList;
import jack.ui.Ui;

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
        this.index = index;
    }

    /**
     * Removes the specified task and saves the updated list.
     *
     * @throws JackException If the index is invalid.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws JackException {
        tasks.checkIndex(index);
        Task removed = tasks.remove(index);
        storage.save(tasks.getInternalList());
        return "Noted. I've removed this jack.task:\n  " + removed
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }
}
