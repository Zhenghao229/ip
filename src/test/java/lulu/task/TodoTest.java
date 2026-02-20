package lulu.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Todo}.
 */
public class TodoTest {

    @Test
    public void constructor_initiallyNotDone() {
        Todo todo = new Todo("read book");

        assertFalse(todo.isDone());
    }

    @Test
    public void markAsDone_taskIsMarkedDone() {
        Todo todo = new Todo("read book");

        todo.markAsDone();

        assertTrue(todo.isDone());
    }

    @Test
    public void markAsNotDone_taskIsMarkedUndone() {
        Todo todo = new Todo("read book");

        todo.markAsDone();
        todo.markAsNotDone();

        assertFalse(todo.isDone());
    }

    @Test
    public void toString_containsDescription() {
        Todo todo = new Todo("read book");

        String out = todo.toString();

        assertTrue(out.contains("read book"));
    }

    @Test
    public void toString_whenDone_containsX() {
        Todo todo = new Todo("read book");
        todo.markAsDone();

        String out = todo.toString();

        assertTrue(out.contains("[X]"));
    }

    @Test
    public void toString_whenNotDone_containsSpace() {
        Todo todo = new Todo("read book");

        String out = todo.toString();

        assertTrue(out.contains("[ ]"));
    }

    @Test
    public void containsKeyword_match_returnsTrue() {
        Todo todo = new Todo("read book");

        assertTrue(todo.containsKeyword("read"));
        assertTrue(todo.containsKeyword("book"));
    }

    @Test
    public void containsKeyword_noMatch_returnsFalse() {
        Todo todo = new Todo("read book");

        assertFalse(todo.containsKeyword("sleep"));
    }

    @Test
    public void setDescription_updatesDescription() {
        Todo todo = new Todo("read book");

        todo.setDescription("write essay");

        assertTrue(todo.toString().contains("write essay"));
    }
}
