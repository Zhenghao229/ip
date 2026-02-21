package lulu.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 * Represents a deadline task with a due date.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter OUT_FMT =
            DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm", Locale.ENGLISH);

    private LocalDateTime by;

    /**
     * Creates a {@code Deadline} task with a description and due date.
     *
     * @param description The description of the deadline task.
     * @param by          The due date of the task.
     */
    public Deadline(String description, LocalDateTime by) {
        super(TaskType.DEADLINE, description);
        assert by != null : "Deadline 'by' must not be null";
        this.by = by;
    }

    /**
     * Returns the due date of this deadline task.
     *
     * @return Due date as a {@link LocalDateTime}.
     */
    public LocalDateTime getBy() {
        return by;
    }

    public void setBy(LocalDateTime by) {
        assert by != null : "Deadline 'by' must not be set to null";
        this.by = by;
    }

    /**
     * Returns true if this deadline is equal to another deadline.
     * <p>
     * Two deadlines are equal if they have the same description
     * (after normalisation) and the same due date.
     *
     * @param other The object to compare against.
     * @return true if both deadlines are logically identical.
     */
    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }
        Deadline that = (Deadline) other;
        return Objects.equals(this.by, that.by);
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return hash code representing this deadline.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), by);
    }

    /**
     * Returns a user-friendly string representation of this deadline task.
     *
     * @return Formatted string including type, status, description, and due date.
     */
    @Override
    public String toString() {
        assert by != null : "Deadline 'by' should never be null when displaying";
        return super.toString() + " (by: " + by.format(OUT_FMT) + ")";
    }
}

