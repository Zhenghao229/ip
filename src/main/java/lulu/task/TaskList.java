package lulu.task;

import java.time.LocalDateTime;
import java.util.ArrayList;

import lulu.LuluException;

/**
 * Represents a list of tasks in the application.
 * Provides operations to add, remove, and retrieve tasks.
 * Support update Operation
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
            return "Your task list is empty.";
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
     * @throws LuluException If the index is out of range.
     */
    public void checkIndex(int idx) throws LuluException {
        if (idx < 0 || idx >= size()) {
            throw new LuluException("That task number is out of range.");
        }
    }

    /**
     * Returns a formatted string of tasks whose descriptions contain the keyword.
     */
    public String findToDisplayString(String keyword) {
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        int count = 0;

        for (int i = 0; i < size(); i++) {
            Task t = get(i);
            if (t.containsKeyword(keyword)) {
                count++;
                sb.append(count).append(". ").append(t).append("\n");
            }
        }

        if (count == 0) {
            return "No matching tasks found.";
        }

        return sb.toString().trim();
    }

    /**
     * Updates selected fields of an existing task (null fields are ignored).
     *
     * @param index   0-based task index
     * @param newDesc new description (nullable)
     * @param newBy   new deadline date/time (nullable)
     * @param newFrom new event start date/time (nullable)
     * @param newTo   new event end date/time (nullable)
     * @return updated task
     * @throws LuluException if invalid index or invalid update for task type
     */
    public Task updateTask(int index, String newDesc, LocalDateTime newBy,
                           LocalDateTime newFrom, LocalDateTime newTo) throws LuluException {
        checkIndex(index);
        Task t = get(index);

        boolean changed = false;

        if (newDesc != null) {
            t.setDescription(newDesc);
            changed = true;
        }

        if (newBy != null) {
            if (!(t instanceof Deadline)) {
                throw new LuluException("Only deadlines can be updated with /by.");
            }

            Deadline d = (Deadline) t;
            d.setBy(newBy);

            changed = true;
        }

        if (newFrom != null || newTo != null) {
            if (!(t instanceof Event)) {
                throw new LuluException("Only events can be updated with /from and /to.");
            }
            Event e = (Event) t;
            if (newFrom != null) {
                e.setFrom(newFrom);
                changed = true;
            }
            if (newTo != null) {
                e.setTo(newTo);
                changed = true;
            }
            // If both present (or after partial update), validate range.
            if (e.getTo().isBefore(e.getFrom())) {
                throw new LuluException("Event end time must not be before start time.");
            }
        }

        if (!changed) {
            throw new LuluException("Nothing to update. Use /desc, /by, /from, /to.");
        }

        return t;
    }
}
