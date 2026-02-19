package jack.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import lulu.task.Todo;

public class TodoTest {

    @Test
    public void markAsDone_taskIsMarkedDone() {
        Todo todo = new Todo("read book");
        todo.markAsDone();

        assertTrue(todo.isDone());
    }

    @Test
    public void markAsUndone_taskIsMarkedUndone() {
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
}
