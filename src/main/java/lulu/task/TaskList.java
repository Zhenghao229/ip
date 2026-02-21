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
        assert tasks != null : "Backing task list must not be null";
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
    public Task get(int index) throws LuluException {
        if (index < 0 || index >= tasks.size()) {
            throw new LuluException("Task number is out of range.");
        }
        return tasks.get(index);
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added.
     */
    public void add(Task task) {
        assert task != null : "Task to add must not be null";
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the given index.
     *
     * @param index Index of the task to remove (0-based).
     * @return The removed task.
     */
    public Task remove(int index) throws LuluException {
        if (index < 0 || index >= tasks.size()) {
            throw new LuluException("Task number is out of range.");
        }
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
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
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
        assert keyword != null : "Keyword must not be null";
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        int count = 0;

        for (int i = 0; i < size(); i++) {
            Task t = tasks.get(i);
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
     * Returns true if the given task already exists in the task list.
     * <p>
     * Equality is determined using {@link Task#equals(Object)}.
     *
     * @param task The task to check.
     * @return true if a duplicate task exists.
     */
    public boolean contains(Task task) {
        assert task != null : "Task must not be null";
        return tasks.contains(task); // uses equals() now
    }

    /**
     * Adds a task to the list only if it does not already exist.
     *
     * @param task The task to add.
     * @throws LuluException If the task already exists in the list.
     */
    public void addUnique(Task task) throws LuluException {
        assert task != null : "Task to add must not be null";

        if (contains(task)) {
            throw new LuluException("OOPS!!! That task already exists in your list:\n  " + task);
        }
        tasks.add(task);
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

        String oldDesc = t.getDescription();

        LocalDateTime oldBy = null;
        if (t instanceof Deadline) {
            oldBy = ((Deadline) t).getBy();
        }

        LocalDateTime oldFrom = null;
        LocalDateTime oldTo = null;
        if (t instanceof Event) {
            oldFrom = ((Event) t).getFrom();
            oldTo = ((Event) t).getTo();
        }

        boolean changed = false;

        if (newDesc != null) {
            String current = t.getDescription();
            String curNorm = current == null ? "" : current.trim().replaceAll("\\s+", " ").toLowerCase();
            String newNorm = newDesc.trim().replaceAll("\\s+", " ").toLowerCase();

            if (!newNorm.equals(curNorm)) {
                t.setDescription(newDesc);
                changed = true;
            }
        }

        if (newBy != null) {
            if (!(t instanceof Deadline)) {
                throw new LuluException("Only deadlines can be updated with /by.");
            }

            Deadline d = (Deadline) t;

            if (!newBy.equals(d.getBy())) {
                d.setBy(newBy);
                changed = true;
            }
        }

        if (newFrom != null || newTo != null) {
            if (!(t instanceof Event)) {
                throw new LuluException("Only events can be updated with /from and /to.");
            }

            Event e = (Event) t;

            oldFrom = e.getFrom();
            oldTo = e.getTo();

            LocalDateTime finalFrom = (newFrom != null) ? newFrom : oldFrom;
            LocalDateTime finalTo = (newTo != null) ? newTo : oldTo;


            if (finalTo.isBefore(finalFrom)) {
                throw new LuluException("Event end time must not be before start time.");
            }

            // Apply updates in a safe order to maintain invariant at every step.
            // If both changed, set 'to' first then 'from' (prevents temporary invalid state).
            if (newTo != null && !newTo.equals(oldTo)) {
                e.setTo(newTo);
                changed = true;
            }
            if (newFrom != null && !newFrom.equals(oldFrom)) {
                e.setFrom(newFrom);
                changed = true;
            }
        }

        if (!changed) {
            throw new LuluException("Nothing changed -- the task already has that value.");
        }

        if (hasDuplicateExceptIndex(index)) {
            t.setDescription(oldDesc);

            if (t instanceof Deadline) {
                Deadline d = (Deadline) t;
                d.setBy(oldBy);
            }

            if (t instanceof Event) {
                Event e = (Event) t;

                e.setTo(oldTo);
                e.setFrom(oldFrom);
            }

            throw new LuluException("Hmm~ This update would create a duplicated task:\n  " + t);
        }

        return t;
    }

    /**
     * Checks whether the task at the given index duplicates
     * another task in the list.
     *
     * @param idx The index of the task to check.
     * @return true if another identical task exists.
     */
    private boolean hasDuplicateExceptIndex(int idx) {
        Task target = tasks.get(idx);
        for (int i = 0; i < tasks.size(); i++) {
            if (i == idx) {
                continue;
            }
            if (target.equals(tasks.get(i))) {
                return true;
            }
        }
        return false;
    }
}
