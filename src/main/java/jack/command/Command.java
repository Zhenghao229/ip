package jack.command;

import jack.JackException;
import jack.storage.Storage;
import jack.task.TaskList;
import jack.ui.Ui;

public abstract class Command {
    public abstract String execute(TaskList tasks, Ui ui, Storage storage) throws JackException;

    public boolean isExit() {
        return false;
    }
}
