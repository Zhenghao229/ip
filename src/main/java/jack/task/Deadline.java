package jack.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
        this.by = by;
    }

    /**
     * Returns a user-friendly string representation of this deadline task.
     *
     * @return Formatted string including type, status, description, and due date.
     */
    @Override
    public String toString() {
        return super.toString() + " (by: " + by.format(OUT_FMT) + ")";
    }
}

