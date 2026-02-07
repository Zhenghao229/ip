package jack.command;

import jack.*;
import jack.storage.Storage;
import jack.task.Task;
import jack.task.TaskList;
import jack.ui.Ui;

public class AddCommand extends Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws JackException {
        tasks.add(task);
        storage.save(tasks.getInternalList());
        return "Got it. I've added this jack.task:\n  " + task
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }
}
