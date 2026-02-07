import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {

    public static String handle(String input, TaskList tasks, Storage storage) throws JackException {
        if (input.equals("list")) {
            return formatList(tasks);
        }

        if (input.startsWith("mark ")) {
            int idx = parseTaskNumber(input, "mark") - 1;
            checkIndex(idx, tasks);
            tasks.get(idx).markAsDone();
            storage.save(tasks.getInternalList());
            return "Nice! I've marked this task as done:\n  " + tasks.get(idx);
        }

        if (input.startsWith("unmark ")) {
            int idx = parseTaskNumber(input, "unmark") - 1;
            checkIndex(idx, tasks);
            tasks.get(idx).markAsNotDone();
            storage.save(tasks.getInternalList());
            return "OK, I've marked this task as not done yet:\n  " + tasks.get(idx);
        }

        if (input.startsWith("delete ")) {
            int idx = parseTaskNumber(input, "delete") - 1;
            checkIndex(idx, tasks);
            Task removed = tasks.remove(idx);
            storage.save(tasks.getInternalList());
            return "Noted. I've removed this task:\n  " + removed
                    + "\nNow you have " + tasks.size() + " tasks in the list.";
        }

        // otherwise: add task (todo/deadline/event)
        Task newTask = parseTask(input);
        tasks.add(newTask);
        storage.save(tasks.getInternalList());
        return "Got it. I've added this task:\n  " + newTask
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private static void checkIndex(int idx, TaskList tasks) throws JackException {
        if (idx < 0 || idx >= tasks.size()) {
            throw new JackException("That task number is out of range.");
        }
    }

    private static int parseTaskNumber(String input, String keyword) throws JackException {
        String numberPart = input.substring(keyword.length()).trim();
        if (numberPart.isEmpty()) {
            throw new JackException("Please provide a task number, e.g. " + keyword + " 2");
        }
        try {
            return Integer.parseInt(numberPart);
        } catch (NumberFormatException e) {
            throw new JackException("Task number must be a number, e.g. " + keyword + " 2");
        }
    }

    private static Task parseTask(String input) throws JackException {
        if (input.equals("todo")) {
            throw new JackException("The description of a todo cannot be empty.");
        }
        if (input.startsWith("todo ")) {
            String desc = input.substring(5).trim();
            if (desc.isEmpty()) throw new JackException("The description of a todo cannot be empty.");
            return new Todo(desc);
        }

        if (input.equals("deadline")) {
            throw new JackException("The description of a deadline cannot be empty.");
        }
        if (input.startsWith("deadline ")) {
            String rest = input.substring(9).trim();
            if (!rest.contains(" /by ")) {
                throw new JackException("Deadline format: deadline <description> /by <yyyy-MM-dd>");
            }
            String[] parts = rest.split(" /by ", 2);
            String desc = parts[0].trim();
            String byStr = parts[1].trim();
            if (desc.isEmpty() || byStr.isEmpty()) {
                throw new JackException("Deadline format: deadline <description> /by <yyyy-MM-dd>");
            }
            try {
                LocalDate by = LocalDate.parse(byStr);
                return new Deadline(desc, by);
            } catch (DateTimeParseException e) {
                throw new JackException("Date must be in yyyy-MM-dd format, e.g. 2019-10-15");
            }
        }

        if (input.equals("event")) {
            throw new JackException("The description of an event cannot be empty.");
        }
        if (input.startsWith("event ")) {
            String rest = input.substring(6).trim();
            if (!rest.contains(" /from ") || !rest.contains(" /to ")) {
                throw new JackException("Event format: event <description> /from <start> /to <end>");
            }
            String[] p1 = rest.split(" /from ", 2);
            String desc = p1[0].trim();
            String[] p2 = p1[1].split(" /to ", 2);
            String from = p2[0].trim();
            String to = p2[1].trim();

            if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new JackException("Event format: event <description> /from <start> /to <end>");
            }
            return new Event(desc, from, to);
        }

        throw new JackException("I'm sorry, but I don't know what that means :-(");
    }

    private static String formatList(TaskList tasks) {
        if (tasks.size() == 0) {
            return "Your task list is empty.";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }
}
