package lulu.command;

import lulu.LuluException;
import lulu.storage.Storage;
import lulu.task.TaskList;
import lulu.ui.Ui;

/**
 * Finds tasks whose descriptions contain a given keyword.
 */
public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws LuluException {
        return tasks.findToDisplayString(keyword);
    }
}

