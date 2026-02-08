package jack.task;

/**
 * Represents an event task with a start and end time (or time range).
 */
public class Event extends Task{
    protected String from;
    protected String to;

    /**
     * Creates an {@code Event} task with a description, start, and end.
     *
     * @param description The description of the event.
     * @param from The start time/details of the event.
     * @param to The end time/details of the event.
     */
    public Event(String description, String from, String to) {
        super(TaskType.EVENT,description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start time/details of this event.
     *
     * @return Start value as a string.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the end time/details of this event.
     *
     * @return End value as a string.
     */
    public String getTo() {
        return to;
    }

    /**
     * Returns a user-friendly string representation of this event task.
     *
     * @return Formatted string including type, status, description, and event time range.
     */
    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
