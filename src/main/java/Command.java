public abstract class Command {
    public abstract String execute(TaskList tasks, Ui ui, Storage storage) throws JackException;

    public boolean isExit() {
        return false;
    }
}
