package jack.task;

/**
 * Represents a to-do task that only has a description.
 */
public class Todo extends Task {

    /**
     * Creates a {@code Todo} task with the given description.
     *
     * @param description The description of the to-do task.
     */
    public Todo(String description) {
        super(TaskType.TODO, description);
    }

    /**
     * Returns a user-friendly string representation of this to-do task.
     *
     * @return Formatted string including type, status, and description.
     */
    @Override
    public String toString() {
        return super.toString();
    }

}
