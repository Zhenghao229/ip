package lulu.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import lulu.LuluException;
import lulu.storage.Storage;
import lulu.task.TaskList;
import lulu.task.Todo;
import lulu.ui.Ui;

public class DeleteCommandTest {

    @TempDir
    Path tempDir;

    @Test
    public void execute_validIndex_removesTaskAndSaves() throws Exception {
        Storage storage = new Storage(tempDir.resolve("tasks.txt").toString());
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));
        tasks.add(new Todo("write essay"));

        DeleteCommand cmd = new DeleteCommand(0);
        String msg = cmd.execute(tasks, new Ui(), storage);

        assertEquals(1, tasks.size());
        assertTrue(tasks.get(0).toString().contains("write essay"));
        assertTrue(msg.contains("removed"));
        assertTrue(msg.contains("Now you have 1 tasks"));
    }

    @Test
    public void execute_invalidIndex_throwsLuluException() {
        Storage storage = new Storage(tempDir.resolve("tasks.txt").toString());
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        DeleteCommand cmd = new DeleteCommand(5);

        assertThrows(LuluException.class, () -> cmd.execute(tasks, new Ui(), storage));
    }
}
