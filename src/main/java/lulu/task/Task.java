package lulu.task;

/**
 * Represents a generic task in Lulu.
 * Each task has a type, description, and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    /**
     * Creates a task with the given type and description.
     *
     * @param type        The task type (e.g., TODO, DEADLINE, EVENT).
     * @param description The description of the task.
     */
    public Task(TaskType type, String description) {
        assert type != null : "Task type must not be null";
        assert description != null : "Task description must not be null";
        this.type = type;
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }


    /**
     * Returns the status icon used in task display.
     *
     * @return "X" if the task is done, otherwise a blank space.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); //mark done task with X
    }

    /**
     * Returns whether this task is done.
     *
     * @return True if done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the description of this task.
     *
     * @return Task description.
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        assert description != null : "New description must not be null";
        this.description = description;
    }

    /**
     * Returns true if the task description contains the given keyword (case-insensitive).
     */
    public boolean containsKeyword(String keyword) {
        assert keyword != null : "Keyword must not be null";
        assert description != null : "Description should not be null here";
        return description.toLowerCase().contains(keyword.toLowerCase());
    }

    /**
     * Returns a user-friendly string representation of this task.
     *
     * @return Formatted string including type, status, and description.
     */
    @Override
    public String toString() {
        return type.getSymbol() + "[" + getStatusIcon() + "] " + description;
    }
}
