package jack.command;

import jack.*;
import jack.storage.Storage;
import jack.task.Task;
import jack.task.TaskList;
import jack.ui.Ui;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws JackException {
        tasks.checkIndex(index);
        Task removed = tasks.remove(index);
        storage.save(tasks.getInternalList());
        return "Noted. I've removed this jack.task:\n  " + removed
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }
}
