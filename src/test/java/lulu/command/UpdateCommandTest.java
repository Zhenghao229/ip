package lulu.command;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import lulu.LuluException;
import lulu.storage.Storage;
import lulu.task.Deadline;
import lulu.task.Event;
import lulu.task.TaskList;
import lulu.task.Todo;
import lulu.ui.Ui;

public class UpdateCommandTest {

    @TempDir
    Path tempDir;

    @Test
    public void execute_updateDescOnTodo_updatesDescription() throws Exception {
        Storage storage = new Storage(tempDir.resolve("tasks.txt").toString());
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        UpdateCommand cmd = new UpdateCommand(0, "write essay", null, null, null);
        String msg = cmd.execute(tasks, new Ui(), storage);

        assertTrue(tasks.get(0).toString().contains("write essay"));
        assertTrue(msg.contains("Updated:"));
    }

    @Test
    public void execute_updateByOnDeadline_updatesBy() throws Exception {
        Storage storage = new Storage(tempDir.resolve("tasks.txt").toString());
        TaskList tasks = new TaskList();
        tasks.add(new Deadline("return book", LocalDateTime.of(2026, 2, 25, 20, 0)));

        LocalDateTime newBy = LocalDateTime.of(2026, 3, 1, 9, 30);
        UpdateCommand cmd = new UpdateCommand(0, null, newBy, null, null);
        cmd.execute(tasks, new Ui(), storage);

        Deadline d = (Deadline) tasks.get(0);
        assertEquals(newBy, d.getBy());
    }

    @Test
    public void execute_updateFromToOnEvent_updatesTimes() throws Exception {
        Storage storage = new Storage(tempDir.resolve("tasks.txt").toString());
        TaskList tasks = new TaskList();
        tasks.add(new Event("lecture",
                LocalDateTime.of(2026, 2, 20, 14, 0),
                LocalDateTime.of(2026, 2, 20, 16, 0)));

        LocalDateTime newFrom = LocalDateTime.of(2026, 2, 21, 10, 0);
        LocalDateTime newTo = LocalDateTime.of(2026, 2, 21, 12, 0);

        UpdateCommand cmd = new UpdateCommand(0, null, null, newFrom, newTo);
        cmd.execute(tasks, new Ui(), storage);

        Event e = (Event) tasks.get(0);
        assertEquals(newFrom, e.getFrom());
        assertEquals(newTo, e.getTo());
    }

    @Test
    public void execute_updateByOnTodo_throwsLuluException() {
        Storage storage = new Storage(tempDir.resolve("tasks.txt").toString());
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        UpdateCommand cmd = new UpdateCommand(0, null,
                LocalDateTime.of(2026, 2, 25, 20, 0),
                null, null);

        assertThrows(LuluException.class, () -> cmd.execute(tasks, new Ui(), storage));
    }
}
