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

    /**
     * Creates a FindCommand that searches for tasks containing the given keyword.
     *
     * @param keyword The keyword to search for.
     */
    public FindCommand(String keyword) {
        assert keyword != null : "Keyword must not be null";
        assert !keyword.trim().isEmpty() : "Keyword should not be blank";
        this.keyword = keyword;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws LuluException {
        return tasks.findToDisplayString(keyword);
    }
}

