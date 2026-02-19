package lulu.command;

import java.time.LocalDateTime;

import lulu.LuluException;
import lulu.storage.Storage;
import lulu.task.Task;
import lulu.task.TaskList;
import lulu.ui.Ui;

/**
 * Updates one or more fields of an existing task.
 * /desc for all tasks
 * /by for Deadline
 * /from and /to for Event
 */
public class UpdateCommand extends Command {
    private final int index; // 0-based
    private final String newDesc; // nullable
    private final LocalDateTime newBy; // nullable
    private final LocalDateTime newFrom; // nullable
    private final LocalDateTime newTo; // nullable

    /**
     * Creates an UpdateCommand that updates one task's fields.
     *
     * @param index   Index of the task to update (0-based).
     * @param newDesc New description, or null if not updating.
     * @param newBy   New deadline time, or null if not updating.
     * @param newFrom New event start time, or null if not updating.
     * @param newTo   New event end time, or null if not updating.
     */
    public UpdateCommand(int index, String newDesc, LocalDateTime newBy, LocalDateTime newFrom, LocalDateTime newTo) {
        this.index = index;
        this.newDesc = newDesc;
        this.newBy = newBy;
        this.newFrom = newFrom;
        this.newTo = newTo;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws LuluException {
        Task updated = tasks.updateTask(index, newDesc, newBy, newFrom, newTo);
        storage.save(tasks.getInternalList()); // must return ArrayList<Task>
        return "Updated: " + updated;
    }
}
