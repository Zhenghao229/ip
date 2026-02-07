import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private static final DateTimeFormatter OUT_FMT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private final LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(TaskType.DEADLINE,description);
        this.by = by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by.format(OUT_FMT) + ")";
    }

    public LocalDate getBy() {
        return by;
    }
}

