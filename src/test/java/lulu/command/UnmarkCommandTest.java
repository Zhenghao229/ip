package lulu.command;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import lulu.storage.Storage;
import lulu.task.TaskList;
import lulu.task.Todo;
import lulu.ui.Ui;

public class UnmarkCommandTest {

    @TempDir
    Path tempDir;

    @Test
    public void execute_unmarksTask() throws Exception {
        Storage storage = new Storage(tempDir.resolve("tasks.txt").toString());
        TaskList tasks = new TaskList();
        Todo t = new Todo("read book");
        t.markAsDone();
        tasks.add(t);

        UnmarkCommand cmd = new UnmarkCommand(0);
        String msg = cmd.execute(tasks, new Ui(), storage);

        assertFalse(tasks.get(0).isDone());
        assertTrue(msg.toLowerCase().contains("not done"));
    }
}
