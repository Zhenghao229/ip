package jack.command;

import jack.storage.Storage;
import jack.task.TaskList;
import jack.ui.Ui;

public class ExitCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return "Bye. Hope to see you again soon!";
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
