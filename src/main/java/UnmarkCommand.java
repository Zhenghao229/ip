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
        return "OK, I've marked this task as not done yet:\n  " + tasks.get(index);
    }
}
