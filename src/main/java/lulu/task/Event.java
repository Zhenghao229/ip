package lulu.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 * Represents an event task with a start and end date/time.
 */
public class Event extends Task {
    private static final DateTimeFormatter OUT_FMT =
            DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm", Locale.ENGLISH);


    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Creates an {@code Event} task with a description, start, and end.
     *
     * @param description The description of the event.
     * @param from        The start time/date of the event.
     * @param to          The end time/date of the event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(TaskType.EVENT, description);
        assert from != null : "Event 'from' must not be null";
        assert to != null : "Event 'to' must not be null";
        assert !to.isBefore(from) : "Event end must not be before start";
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start time/details of this event.
     *
     * @return Start value as a DateTimeFormatter.
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the end time/details of this event.
     *
     * @return End value as a DateTimeFormatter.
     */
    public LocalDateTime getTo() {
        return to;
    }

    public void setFrom(LocalDateTime from) {
        assert from != null : "Event 'from' must not be set to null";
        this.from = from;
        assert to != null : "Event 'to' should not be null here";
        assert !to.isBefore(this.from) : "Event end must not be before start";
    }

    public void setTo(LocalDateTime to) {
        assert to != null : "Event 'to' must not be set to null";
        this.to = to;
        assert from != null : "Event 'from' should not be null here";
        assert !this.to.isBefore(from) : "Event end must not be before start";
    }

    /**
     * Returns true if this event is equal to another event.
     * <p>
     * Two events are equal if they have the same description
     * (after normalisation), and the same start and end times.
     *
     * @param other The object to compare against.
     * @return true if both events are logically identical.
     */
    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }
        Event that = (Event) other;
        return Objects.equals(this.from, that.from)
                && Objects.equals(this.to, that.to);
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return hash code representing this event.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), from, to);
    }

    /**
     * Returns a user-friendly string representation of this event task.
     *
     * @return Formatted string including type, status, description, and event time range.
     */
    @Override
    public String toString() {
        assert from != null && to != null : "Event times should never be null when displaying";
        return super.toString()
                + " (from: " + from.format(OUT_FMT)
                + " to: " + to.format(OUT_FMT) + ")";
    }
}
