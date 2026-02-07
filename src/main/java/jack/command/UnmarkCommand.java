package jack.command;

import jack.JackException;
import jack.storage.Storage;
import jack.task.TaskList;
import jack.ui.Ui;

public class UnmarkCommand extends Command {
    private final int index; // 0-based index

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws JackException {
        tasks.checkIndex(index);
        tasks.get(index).markAsNotDone();
        storage.save(tasks.getInternalList());
        return "OK, I've marked this jack.task as not done yet:\n  " + tasks.get(index);
    }
}
