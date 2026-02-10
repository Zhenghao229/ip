package jack.command;

import jack.JackException;
import jack.storage.Storage;
import jack.task.TaskList;
import jack.ui.Ui;

/**
 * Finds tasks whose descriptions contain a given keyword.
 */
public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws JackException {
        return tasks.findToDisplayString(keyword);
    }
}

