package jack.task;

import jack.JackException;

import java.util.ArrayList;

/**
 * Represents a list of tasks in the application.
 * Provides operations to add, remove, and retrieve tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list backed by the given list.
     *
     * @param tasks The list of tasks to use internally.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at the given index.
     *
     * @param index Index of the task to retrieve (0-based).
     * @return The task at the given index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the given index.
     *
     * @param index Index of the task to remove (0-based).
     * @return The removed task.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the internal list used to store tasks.
     * Intended for storage usage (saving/loading).
     *
     * @return Internal {@link ArrayList} of tasks.
     */
    public ArrayList<Task> getInternalList() {
        return tasks;
    }

    /**
     * Returns a formatted string representation of the task list.
     *
     * @return Display string of tasks.
     */
    public String toDisplayString() {
        if (size() == 0) {
            return "Your jack.task list is empty.";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < size(); i++) {
            sb.append(i + 1).append(". ").append(get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Checks whether the given index is within the valid range of the task list.
     *
     * @param idx Index to check (0-based).
     * @throws JackException If the index is out of range.
     */
    public void checkIndex(int idx) throws JackException {
        if (idx < 0 || idx >= size()) {
            throw new JackException("That jack.task number is out of range.");
        }
    }


}
