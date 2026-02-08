package jack.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task with a due date.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter OUT_FMT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private final LocalDate by;

    /**
     * Creates a {@code Deadline} task with a description and due date.
     *
     * @param description The description of the deadline task.
     * @param by The due date of the task.
     */
    public Deadline(String description, LocalDate by) {
        super(TaskType.DEADLINE,description);
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

    /**
     * Returns the due date of this deadline task.
     *
     * @return Due date as a {@link LocalDate}.
     */
    public LocalDate getBy() {
        return by;
    }
}

