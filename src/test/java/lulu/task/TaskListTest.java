package lulu.task;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lulu.LuluException;

/**
 * Tests for {@link TaskList}.
 * Covers:
 * - display formatting for empty/non-empty lists
 * - numbering for multiple tasks
 * - index validation
 * - get/remove behavior (recommended for A-MoreTesting)
 */
public class TaskListTest {

    // -------------------------
    // toDisplayString
    // -------------------------

    @Test
    public void toDisplayString_emptyList_returnsEmptyMessage() {
        TaskList tasks = new TaskList();
        String out = tasks.toDisplayString().toLowerCase();

        assertTrue(out.contains("list is empty"));
    }

    @Test
    public void toDisplayString_nonEmptyList_containsHeaderNumberingAndTask() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        String out = tasks.toDisplayString();

        assertTrue(out.startsWith("Here are the tasks in your list:"));
        assertTrue(out.contains("1."));
        assertTrue(out.contains("read book"));
    }

    @Test
    public void toDisplayString_multipleTasks_numbersAreCorrect() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));
        tasks.add(new Todo("write essay"));
        tasks.add(new Todo("drink water"));

        String out = tasks.toDisplayString();

        assertTrue(out.startsWith("Here are the tasks in your list:"));
        assertTrue(out.contains("1."));
        assertTrue(out.contains("2."));
        assertTrue(out.contains("3."));
        assertTrue(out.contains("read book"));
        assertTrue(out.contains("write essay"));
        assertTrue(out.contains("drink water"));
    }

    @Test
    public void checkIndexEmptyListAnyIndexThrowsLuluException() {
        TaskList tasks = new TaskList();
        assertThrows(LuluException.class, () -> tasks.checkIndex(0));
        assertThrows(LuluException.class, () -> tasks.checkIndex(-1));
    }

    @Test
    public void checkIndex_outOfRange_throwsLuluException() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        assertThrows(LuluException.class, () -> tasks.checkIndex(-1));
        assertThrows(LuluException.class, () -> tasks.checkIndex(1)); // size=1, only valid index is 0
    }

    @Test
    public void checkIndex_validIndex_doesNotThrow() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        assertDoesNotThrow(() -> tasks.checkIndex(0));
    }


    @Test
    public void get_validIndex_returnsTask() throws Exception {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        Task t = tasks.get(0);
        assertTrue(t.toString().contains("read book"));
    }

    @Test
    public void get_outOfRange_throwsLuluException() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        assertThrows(LuluException.class, () -> tasks.get(-1));
        assertThrows(LuluException.class, () -> tasks.get(1));
    }

    @Test
    public void remove_validIndex_removesAndReturnsTask() throws Exception {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));
        tasks.add(new Todo("write essay"));

        Task removed = tasks.remove(0);
        assertTrue(removed.toString().contains("read book"));

        // after removing first item, remaining should contain "write essay"
        String out = tasks.toDisplayString();
        assertTrue(out.contains("write essay"));
    }

    @Test
    public void remove_outOfRange_throwsLuluException() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        assertThrows(LuluException.class, () -> tasks.remove(-1));
        assertThrows(LuluException.class, () -> tasks.remove(1));
    }

    @Test
    public void size_afterAddAndRemove_isCorrect() throws Exception {
        TaskList tasks = new TaskList();
        assertEquals(0, tasks.size());

        tasks.add(new Todo("read book"));
        tasks.add(new Todo("write essay"));
        assertEquals(2, tasks.size());

        tasks.remove(0);
        assertEquals(1, tasks.size());
    }
}
