package lulu.task;

import java.util.Objects;

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
     * Returns a normalised version of the task description.
     * <p>
     * Normalisation trims leading/trailing spaces, collapses multiple
     * spaces into one, and converts text to lowercase.
     *
     * @return normalised description string.
     */
    private String normalisedDesc() {
        return description == null ? "" : description.trim().replaceAll("\\s+", " ").toLowerCase();
    }

    /**
     * Returns true if this task is considered a duplicate of another task.
     * <p>
     * Two tasks are considered equal if:
     * <ul>
     *     <li>They are of the same concrete type</li>
     *     <li>They have the same task type</li>
     *     <li>Their descriptions are equal after normalisation</li>
     * </ul>
     * The completion status (isDone) is ignored when checking equality.
     *
     * @param other The object to compare against.
     * @return true if both tasks represent the same logical task.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Task that = (Task) other;

        // IMPORTANT: we ignore isDone for duplication purposes
        return this.type == that.type
                && this.normalisedDesc().equals(that.normalisedDesc());
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return hash code representing this task.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getClass(), type, normalisedDesc());
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
