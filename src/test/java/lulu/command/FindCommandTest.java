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

public class FindCommandTest {

    @TempDir
    Path tempDir;

    @Test
    public void execute_keywordMatches_returnsMatchingTasks() throws Exception {
        Storage storage = new Storage(tempDir.resolve("tasks.txt").toString());
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));
        tasks.add(new Todo("write essay"));

        FindCommand cmd = new FindCommand("read");
        String out = cmd.execute(tasks, new Ui(), storage);

        assertTrue(out.contains("matching"));
        assertTrue(out.contains("read book"));
        assertFalse(out.contains("write essay"));
    }

    @Test
    public void execute_noMatches_returnsNoMatchingMessage() throws Exception {
        Storage storage = new Storage(tempDir.resolve("tasks.txt").toString());
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        FindCommand cmd = new FindCommand("sleep");
        String out = cmd.execute(tasks, new Ui(), storage);

        assertTrue(out.toLowerCase().contains("no matching"));
    }
}
