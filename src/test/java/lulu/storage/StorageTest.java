package lulu.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import lulu.LuluException;
import lulu.task.Deadline;
import lulu.task.Event;
import lulu.task.Task;
import lulu.task.Todo;

public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void saveThenLoad_preservesTasksAndDoneStatus() throws Exception {
        Path file = tempDir.resolve("tasks.txt");
        Storage storage = new Storage(file.toString());

        ArrayList<Task> tasks = new ArrayList<>();
        Todo t = new Todo("read book");
        Deadline d = new Deadline("return book", LocalDateTime.of(2026, 2, 25, 20, 0));
        Event e = new Event("lecture",
                LocalDateTime.of(2026, 2, 20, 14, 0),
                LocalDateTime.of(2026, 2, 20, 16, 0));

        d.markAsDone(); // mark one as done to test persistence

        tasks.add(t);
        tasks.add(d);
        tasks.add(e);

        storage.save(tasks);

        ArrayList<Task> loaded = storage.load();

        assertEquals(3, loaded.size());
        assertTrue(loaded.get(0) instanceof Todo);
        assertTrue(loaded.get(1) instanceof Deadline);
        assertTrue(loaded.get(2) instanceof Event);

        assertTrue(loaded.get(0).toString().contains("read book"));
        assertTrue(loaded.get(1).toString().contains("return book"));
        assertTrue(loaded.get(2).toString().contains("lecture"));

        assertFalse(loaded.get(0).isDone());
        assertTrue(loaded.get(1).isDone()); // must stay done after load
        assertFalse(loaded.get(2).isDone());
    }

    @Test
    public void load_missingFile_returnsEmptyList() throws Exception {
        Path file = tempDir.resolve("does_not_exist.txt");
        Storage storage = new Storage(file.toString());

        assertEquals(0, storage.load().size());
    }

    @Test
    public void load_corruptedLine_throwsLuluException() throws Exception {
        Path file = tempDir.resolve("bad.txt");
        java.nio.file.Files.writeString(file, "BAD LINE");

        Storage storage = new Storage(file.toString());

        assertThrows(LuluException.class, storage::load);
    }
}
