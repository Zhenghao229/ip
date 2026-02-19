package lulu.command;

import lulu.storage.Storage;
import lulu.task.TaskList;
import lulu.ui.Ui;

/**
 * Terminates the application.
 */
public class ExitCommand extends Command {

    /**
     * Displays the farewell message.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return "Bye bye~ LULU is going to rest now \uD83D\uDE34";
    }

    /**
     * Indicates that this command exits the application.
     *
     * @return True always.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
