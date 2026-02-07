package jack.command;

import jack.storage.Storage;
import jack.task.TaskList;
import jack.ui.Ui;

public class ListCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return tasks.toDisplayString();
    }
}
