package jack.command;


import jack.JackException;
import jack.storage.Storage;
import jack.task.Task;
import jack.task.TaskList;
import jack.ui.Ui;

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
        this.task = task;
    }

    /**
     * Adds the task to the list and saves the updated list.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws JackException {
        tasks.add(task);
        storage.save(tasks.getInternalList());
        return "Got it. I've added this jack.task:\n  " + task
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }
}
