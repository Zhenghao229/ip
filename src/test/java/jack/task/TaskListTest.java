package jack.task;

import lulu.LuluException;
import lulu.task.TaskList;
import lulu.task.Todo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    @Test
    public void toDisplayString_emptyList_returnsEmptyMessage() {
        TaskList tasks = new TaskList();
        String out = tasks.toDisplayString().toLowerCase();

        assertTrue(out.contains("list is empty"));
    }


    @Test
    public void toDisplayString_nonEmptyList_containsNumberingAndTask() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        String out = tasks.toDisplayString();

        assertTrue(out.startsWith("Here are the tasks in your list:"));
        assertTrue(out.contains("1."));
        assertTrue(out.contains("read book")); // description should appear
    }

    @Test
    public void checkIndex_outOfRange_throwsJackException() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        assertThrows(LuluException.class, () -> tasks.checkIndex(-1));
        assertThrows(LuluException.class, () -> tasks.checkIndex(1)); // size=1, valid index only 0
    }

    @Test
    public void checkIndex_validIndex_doesNotThrow() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        assertDoesNotThrow(() -> tasks.checkIndex(0));
    }
}
