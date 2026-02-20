package lulu.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class EventTest {

    @Test
    public void toString_containsDescriptionFromTo() {
        LocalDateTime from = LocalDateTime.of(2026, 2, 20, 14, 0);
        LocalDateTime to = LocalDateTime.of(2026, 2, 20, 16, 0);

        Event e = new Event("cs2103 lecture", from, to);

        String out = e.toString();

        assertTrue(out.contains("cs2103 lecture"));
        assertTrue(out.contains("(from:"));
        assertTrue(out.contains("to:"));
        assertTrue(out.contains("2026"));
    }

    @Test
    public void setFrom_setTo_updatesValues() {
        Event e = new Event("meeting",
                LocalDateTime.of(2026, 2, 20, 14, 0),
                LocalDateTime.of(2026, 2, 20, 16, 0));

        LocalDateTime newFrom = LocalDateTime.of(2026, 2, 21, 10, 0);
        LocalDateTime newTo = LocalDateTime.of(2026, 2, 21, 12, 0);

        e.setTo(newTo);
        e.setFrom(newFrom);

        assertEquals(newFrom, e.getFrom());
        assertEquals(newTo, e.getTo());
    }

    @Test
    public void inheritedContainsKeyword_caseInsensitive() {
        Event e = new Event("Team Meeting",
                LocalDateTime.of(2026, 2, 20, 14, 0),
                LocalDateTime.of(2026, 2, 20, 16, 0));

        assertTrue(e.containsKeyword("meeting"));
        assertTrue(e.containsKeyword("MEET"));
        assertFalse(e.containsKeyword("lecture"));
    }
}
