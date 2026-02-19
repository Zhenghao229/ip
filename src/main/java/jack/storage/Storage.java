package jack.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import jack.JackException;
import jack.task.Deadline;
import jack.task.Event;
import jack.task.Task;
import jack.task.Todo;

/**
 * Handles loading tasks from disk and saving tasks to disk.
 * Tasks are stored as lines of text in a simple pipe-delimited format.
 */
public class Storage {
    private static final DateTimeFormatter DATE_TIME_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final Path filePath;

    /**
     * Handles loading tasks from disk and saving tasks to disk.
     * Tasks are stored as lines of text in a simple pipe-delimited format.
     */
    public Storage(String relativePath) {
        this.filePath = Paths.get(relativePath);
    }

    /**
     * Loads tasks from the save file.
     * If the file does not exist, returns an empty list.
     *
     * @return An {@link ArrayList} of tasks loaded from disk.
     * @throws JackException If the file exists but cannot be read or contains invalid data.
     */
    public ArrayList<Task> load() throws JackException {
        ArrayList<Task> tasks = new ArrayList<>();

        if (Files.notExists(filePath)) {
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                tasks.add(parseLine(line));
            }
            return tasks;
        } catch (IOException e) {
            throw new JackException("I couldn't read the save file: " + filePath);
        }
    }

    /**
     * Saves the given list of tasks to the save file.
     * Creates parent directories if necessary.
     *
     * @param tasks The task list to be saved.
     * @throws JackException If the file cannot be written.
     */
    public void save(ArrayList<Task> tasks) throws JackException {
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            ArrayList<String> lines = new ArrayList<>();
            for (Task t : tasks) {
                lines.add(toLine(t));
            }
            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new JackException("I couldn't save to file: " + filePath);
        }
    }

    private Task parseLine(String line) throws JackException {
        String[] parts = line.split("\\s*\\|\\s*");

        if (parts.length < 3) {
            throw new JackException("Save file is corrupted: " + line);
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");

        Task task;
        switch (type) {
        case "T":
            task = new Todo(parts[2]);
            break;

        case "D":
            if (parts.length < 4) {
                throw new JackException("Bad deadline line: " + line);
            }
            try {
                LocalDateTime by = parseDateTime(parts[3]);
                task = new Deadline(parts[2], by);
            } catch (DateTimeParseException e) {
                throw new JackException("Bad date in save file: " + parts[3]);
            }
            break;

        case "E":
            if (parts.length < 5) {
                throw new JackException("Bad event line: " + line);
            }
            LocalDateTime from = parseDateTime(parts[3]);
            LocalDateTime to = parseDateTime(parts[4]);
            task = new Event(parts[2], from, to);

            break;

        default:
            throw new JackException("Unknown jack.task type in file: " + type);
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    private static LocalDateTime parseDateTime(String text) throws JackException {
        try {
            return LocalDateTime.parse(text, DATE_TIME_FMT);
        } catch (DateTimeParseException e) {
            try {
                return LocalDate.parse(text).atStartOfDay();
            } catch (DateTimeParseException e2) {
                throw new JackException("Bad date/time in save file: " + text);
            }
        }
    }

    private String toLine(Task t) throws JackException {
        String done = t.isDone() ? "1" : "0";

        if (t instanceof Todo) {
            return "T | " + done + " | " + t.getDescription();
        } else if (t instanceof Deadline d) {
            return "D | " + done + " | " + d.getDescription()
                    + " | " + d.getBy().format(DATE_TIME_FMT);
        } else if (t instanceof Event e) {
            return "E | " + done + " | " + e.getDescription()
                    + " | " + e.getFrom().format(DATE_TIME_FMT)
                    + " | " + e.getTo().format(DATE_TIME_FMT);

        } else {
            throw new JackException("Unknown jack.task class: " + t.getClass());
        }
    }
}
