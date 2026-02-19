package jack.task;

/**
 * Represents the supported types of tasks in the application.
 * Each type has a display symbol used in {@link Task#toString()}.
 */
public enum TaskType {
    /** A to-do task. */
    TODO("[T]"),
    /** A deadline task. */
    DEADLINE("[D]"),
    /** An event task. */
    EVENT("[E]");

    private final String symbol;

    /**
     * Creates a task type with the given display symbol.
     *
     * @param symbol Symbol used for displaying this task type.
     */
    TaskType(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Creates a task type with the given display symbol.
     *
     * @return symbol used for displaying this task type.
     */
    public String getSymbol() {
        return symbol;
    }
}
