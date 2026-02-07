public class MarkCommand extends Command {
    private final int index; // 0-based

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws JackException {
        tasks.checkIndex(index);
        tasks.get(index).markAsDone();
        storage.save(tasks.getInternalList());
        return "Nice! I've marked this task as done:\n  " + tasks.get(index);
    }
}
