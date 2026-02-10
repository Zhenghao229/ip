package jack.parser;

import jack.JackException;
import jack.task.TaskList;
import jack.command.*;
import jack.task.Deadline;
import jack.task.Event;
import jack.task.Task;
import jack.task.Todo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parses raw user input into executable {@link Command} objects.
 * Supported commands include listing tasks, marking/unmarking, deleting, and adding tasks.
 */
public class Parser {

    /**
     * Converts a user input string into the corresponding {@link Command}.
     *
     * @param input Raw user input.
     * @return A {@link Command} representing the action requested by the user.
     * @throws JackException If the input is invalid or does not match any supported command format.
     */
    public static Command parse(String input) throws JackException {
        input = input.trim();

        if (input.equals("bye")) {
            return new ExitCommand();
        }
        if (input.equals("list")) {
            return new ListCommand();
        }
        if (input.startsWith("mark ")) {
            int idx = parseTaskNumber(input, "mark") - 1;
            return new MarkCommand(idx);
        }
        if (input.startsWith("unmark ")) {
            int idx = parseTaskNumber(input, "unmark") - 1;
            return new UnmarkCommand(idx);
        }
        if (input.startsWith("delete ")) {
            int idx = parseTaskNumber(input, "delete") - 1;
            return new DeleteCommand(idx);
        }
        if (input.startsWith("find ")) {
            String keyword = input.substring(5).trim();
            if (keyword.isEmpty()) {
                throw new JackException("Please provide a keyword to find.");
            }
            return new FindCommand(keyword);
        }

        // add tasks
        Task t = parseTask(input); // existing parseTask that returns Jack.Todo/Jack.Deadline/Jack.Event
        return new AddCommand(t);
    }
    private static int parseTaskNumber(String input, String keyword) throws JackException {
        String numberPart = input.substring(keyword.length()).trim();
        if (numberPart.isEmpty()) {
            throw new JackException("Please provide a jack.task number, e.g. " + keyword + " 2");
        }
        try {
            return Integer.parseInt(numberPart);
        } catch (NumberFormatException e) {
            throw new JackException("Jack.Task number must be a number, e.g. " + keyword + " 2");
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
                throw new JackException("Jack.Deadline format: deadline <description> /by <yyyy-MM-dd>");
            }
            String[] parts = rest.split(" /by ", 2);
            String desc = parts[0].trim();
            String byStr = parts[1].trim();
            if (desc.isEmpty() || byStr.isEmpty()) {
                throw new JackException("Jack.Deadline format: deadline <description> /by <yyyy-MM-dd>");
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
                throw new JackException("Jack.Event format: event <description> /from <start> /to <end>");
            }
            String[] p1 = rest.split(" /from ", 2);
            String desc = p1[0].trim();
            String[] p2 = p1[1].split(" /to ", 2);
            String from = p2[0].trim();
            String to = p2[1].trim();

            if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new JackException("Jack.Event format: event <description> /from <start> /to <end>");
            }
            return new Event(desc, from, to);
        }

        throw new JackException("I'm sorry, but I don't know what that means :-(");
    }

    private static String formatList(TaskList tasks) {
        if (tasks.size() == 0) {
            return "Your jack.task list is empty.";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }
}
