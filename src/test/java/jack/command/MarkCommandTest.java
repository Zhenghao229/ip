package jack.command;

import lulu.command.MarkCommand;
import lulu.storage.Storage;
import lulu.task.TaskList;
import lulu.task.Todo;
import lulu.ui.Ui;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class MarkCommandTest {

    @TempDir
    Path tempDir;

    @Test
    public void execute_validIndex_marksTaskDone() throws Exception {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        Ui ui = new Ui();
        Storage storage = new Storage(tempDir.resolve("tasks.txt").toString());

        MarkCommand cmd = new MarkCommand(0);
        cmd.execute(tasks, ui, storage);

        assertTrue(tasks.get(0).isDone());
    }
}
