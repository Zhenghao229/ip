package lulu.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void toString_containsDescriptionAndBy() {
        LocalDateTime by = LocalDateTime.of(2026, 2, 25, 20, 0);
        Deadline d = new Deadline("return book", by);

        String out = d.toString();

        assertTrue(out.contains("return book"));
        assertTrue(out.contains("(by:"));
        assertTrue(out.contains("2026")); // formatted output includes year
    }

    @Test
    public void setBy_updatesBy() {
        LocalDateTime by1 = LocalDateTime.of(2026, 2, 25, 20, 0);
        LocalDateTime by2 = LocalDateTime.of(2026, 3, 1, 9, 30);

        Deadline d = new Deadline("return book", by1);
        d.setBy(by2);

        assertEquals(by2, d.getBy());
    }

    @Test
    public void inheritedMarking_works() {
        Deadline d = new Deadline("return book", LocalDateTime.of(2026, 2, 25, 20, 0));

        assertFalse(d.isDone());
        d.markAsDone();
        assertTrue(d.isDone());
        d.markAsNotDone();
        assertFalse(d.isDone());
    }
}
