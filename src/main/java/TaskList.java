import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    // If Storage needs the raw ArrayList to save
    public ArrayList<Task> getInternalList() {
        return tasks;
    }

    public String toDisplayString() {
        if (size() == 0) {
            return "Your task list is empty.";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < size(); i++) {
            sb.append(i + 1).append(". ").append(get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    public void checkIndex(int idx) throws JackException {
        if (idx < 0 || idx >= size()) {
            throw new JackException("That task number is out of range.");
        }
    }


}
