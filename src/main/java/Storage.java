import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Storage {
    private final Path filePath;

    public Storage(String relativePath) {
        this.filePath = Paths.get(relativePath);
    }

    public ArrayList<Task> load() throws JackException {
        ArrayList<Task> tasks = new ArrayList<>();

        if (Files.notExists(filePath)) {
            // file (or folder) not there yet → treat as empty list
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;
                tasks.add(parseLine(line));
            }
            return tasks;
        } catch (IOException e) {
            throw new JackException("I couldn't read the save file: " + filePath);
        }
    }

    public void save(ArrayList<Task> tasks) throws JackException {
        try {
            // ensure folder exists (data/)
            Path parent = filePath.getParent();
            if (parent != null) Files.createDirectories(parent);

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
        // parts[0] = type, parts[1] = done, parts[2...] = fields

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
                if (parts.length < 4) throw new JackException("Bad deadline line: " + line);
                task = new Deadline(parts[2], parts[3]);
                break;
            case "E":
                if (parts.length < 5) throw new JackException("Bad event line: " + line);
                task = new Event(parts[2], parts[3], parts[4]);
                break;
            default:
                throw new JackException("Unknown task type in file: " + type);
        }

        if (isDone) task.markAsDone(); // use your existing methods
        return task;
    }

    private String toLine(Task t) throws JackException {
        // You must implement a way to know task type + its extra fields
        // Easiest: use instanceof

        String done = t.isDone() ? "1" : "0"; // you need isDone() getter in Task

        if (t instanceof Todo) {
            return "T | " + done + " | " + t.getDescription();
        } else if (t instanceof Deadline d) {
            return "D | " + done + " | " + d.getDescription() + " | " + d.getBy();
        } else if (t instanceof Event e) {
            return "E | " + done + " | " + e.getDescription() + " | " + e.getFrom() + " | " + e.getTo();
        } else {
            throw new JackException("Unknown task class: " + t.getClass());
        }
    }
}
