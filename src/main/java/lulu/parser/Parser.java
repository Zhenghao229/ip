package lulu.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import lulu.LuluException;
import lulu.command.AddCommand;
import lulu.command.Command;
import lulu.command.DeleteCommand;
import lulu.command.ExitCommand;
import lulu.command.FindCommand;
import lulu.command.ListCommand;
import lulu.command.MarkCommand;
import lulu.command.UnmarkCommand;
import lulu.command.UpdateCommand;
import lulu.task.Deadline;
import lulu.task.Event;
import lulu.task.Task;
import lulu.task.Todo;

/**
 * Parses raw user input into executable {@link Command} objects.
 * Supported commands include listing tasks, marking/unmarking, deleting, finding and adding tasks.
 */
public class Parser {

    private static final DateTimeFormatter IN_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final String UPDATE_HELP =
            "How to use update:\n"
                    + "update INDEX FIELD\n\n"
                    + "Fields:\n"
                    + "  /desc TEXT\n"
                    + "  /by yyyy-MM-dd HH:mm\n"
                    + "  /from yyyy-MM-dd HH:mm /to yyyy-MM-dd HH:mm\n\n"
                    + "Example:\n"
                    + "  update 2 /desc read book\n"
                    + "  update 3 /by 2026-02-25 20:00\n"
                    + "  update 4 /from 2026-02-20 14:00 /to 2026-02-20 16:00";

    /**
     * Converts a user input string into the corresponding {@link Command}.
     *
     * @param input Raw user input.
     * @return A {@link Command} representing the action requested by the user.
     * @throws LuluException If the input is invalid or does not match any supported command format.
     */
    public static Command parse(String input) throws LuluException {
        assert input != null : "User input should not be null";
        input = input.trim();

        if (input.equals("bye")) {
            return new ExitCommand();
        }
        if (input.equals("list")) {
            return new ListCommand();
        }

        // mark
        if (input.equals("mark")) {
            throw new LuluException("Usage: mark INDEX\nExample: mark 2");
        }
        if (input.startsWith("mark ")) {
            int idx = parsePositiveTaskNumber(input, "mark") - 1;
            return new MarkCommand(idx);
        }

        // unmark
        if (input.equals("unmark")) {
            throw new LuluException("Usage: unmark INDEX\nExample: unmark 2");
        }
        if (input.startsWith("unmark ")) {
            int idx = parsePositiveTaskNumber(input, "unmark") - 1;
            return new UnmarkCommand(idx);
        }

        // delete
        if (input.equals("delete")) {
            throw new LuluException("Usage: delete INDEX\nExample: delete 2");
        }
        if (input.startsWith("delete ")) {
            int idx = parsePositiveTaskNumber(input, "delete") - 1;
            return new DeleteCommand(idx);
        }

        // find
        if (input.equals("find")) {
            throw new LuluException("Usage: find KEYWORD\nExample: find book");
        }
        if (input.startsWith("find ")) {
            String keyword = input.substring(5).trim();
            if (keyword.isEmpty()) {
                throw new LuluException("Usage: find KEYWORD\nExample: find book");
            }
            return new FindCommand(keyword);
        }

        // update
        if (input.equals("update")) {
            throw new LuluException(UPDATE_HELP);
        }
        if (input.startsWith("update ")) {
            return parseUpdate(input);
        }

        // add tasks
        Task t = parseTask(input);
        return new AddCommand(t);
    }

    private static int parsePositiveTaskNumber(String input, String keyword) throws LuluException {
        assert input != null && keyword != null : "Input and keyword must not be null";

        String numberPart = input.substring(keyword.length()).trim();
        if (numberPart.isEmpty()) {
            throw new LuluException("Usage: " + keyword + " INDEX\nExample: " + keyword + " 2");
        }

        try {
            int num = Integer.parseInt(numberPart);
            if (num <= 0) {
                throw new LuluException("Task number must be a positive integer.");
            }
            return num;
        } catch (NumberFormatException e) {
            throw new LuluException("Task number must be a valid integer, e.g. " + keyword + " 2");
        }
    }

    private static Task parseTask(String input) throws LuluException {
        if (input.equals("todo")) {
            throw new LuluException("The description of a todo cannot be empty.");
        }
        if (input.startsWith("todo ")) {
            String desc = input.substring(5).trim();
            if (desc.isEmpty()) {
                throw new LuluException("The description of a todo cannot be empty.");
            }
            return new Todo(desc);
        }

        if (input.equals("deadline")) {
            throw new LuluException("The description of a deadline cannot be empty.\n"
                                        + "Deadline format: deadline <description> /by <yyyy-MM-dd HH:mm>");
        }
        if (input.startsWith("deadline ")) {
            String rest = input.substring(9).trim();
            if (!rest.contains(" /by ")) {
                throw new LuluException(
                        "Deadline format: deadline <description> /by <yyyy-MM-dd HH:mm>"
                );
            }
            String[] parts = rest.split(" /by ", 2);
            String desc = parts[0].trim();
            String byStr = parts[1].trim();

            if (desc.isEmpty() || byStr.isEmpty()) {
                throw new LuluException(
                        "Deadline format: deadline <description> /by <yyyy-MM-dd HH:mm>"
                );
            }

            LocalDateTime by = parseDateTime(byStr, "/by");
            return new Deadline(desc, by);
        }

        if (input.equals("event")) {
            throw new LuluException("The description of an event cannot be empty.\n"
                    + "Event format: event <description> /from <yyyy-MM-dd HH:mm> /to <yyyy-MM-dd HH:mm>");
        }
        if (input.startsWith("event ")) {
            String rest = input.substring(6).trim();
            if (!rest.contains(" /from ") || !rest.contains(" /to ")) {
                throw new LuluException(
                        "Event format: event <description> /from <yyyy-MM-dd HH:mm> /to <yyyy-MM-dd HH:mm>"
                );
            }

            String[] p1 = rest.split(" /from ", 2);
            String desc = p1[0].trim();
            String[] p2 = p1[1].split(" /to ", 2);
            String fromStr = p2[0].trim();
            String toStr = p2[1].trim();

            if (desc.isEmpty() || fromStr.isEmpty() || toStr.isEmpty()) {
                throw new LuluException(
                        "Event format: event <description> /from <yyyy-MM-dd HH:mm> /to <yyyy-MM-dd HH:mm>"
                );
            }

            LocalDateTime from = parseDateTime(fromStr, "/from");
            LocalDateTime to = parseDateTime(toStr, "/to");

            if (to.isBefore(from)) {
                throw new LuluException("Event end time must not be before start time.");
            }

            return new Event(desc, from, to);
        }

        throw new LuluException("I'm sorry, but I don't know what that means :-(");
    }

    private static Command parseUpdate(String input) throws LuluException {
        assert input != null : "Update input must not be null";

        String rest = input.substring("update ".length()).trim();
        String[] parts = rest.split("\\s+", 2);

        if (parts.length < 2) {
            throw new LuluException(UPDATE_HELP);
        }

        int idx;
        try {
            int userIndex = Integer.parseInt(parts[0]);
            if (userIndex <= 0) {
                throw new LuluException("Task number must be a positive integer.");
            }
            idx = userIndex - 1;
        } catch (NumberFormatException e) {
            throw new LuluException("Task number must be a valid integer, e.g. update 2 /desc ...");
        }

        String args = parts[1].trim();

        String newDesc = extractValue(args, "/desc");
        String byStr = extractValue(args, "/by");
        String fromStr = extractValue(args, "/from");
        String toStr = extractValue(args, "/to");

        LocalDateTime newBy = (byStr == null) ? null : parseDateTime(byStr, "/by");
        LocalDateTime newFrom = (fromStr == null) ? null : parseDateTime(fromStr, "/from");
        LocalDateTime newTo = (toStr == null) ? null : parseDateTime(toStr, "/to");

        if (newDesc == null && newBy == null && newFrom == null && newTo == null) {
            throw new LuluException(UPDATE_HELP);
        }

        if (newFrom != null && newTo != null && newTo.isBefore(newFrom)) {
            throw new LuluException("Event end time must not be before start time.");
        }

        return new UpdateCommand(idx, newDesc, newBy, newFrom, newTo);
    }

    private static LocalDateTime parseDateTime(String text, String flag) throws LuluException {
        try {
            return LocalDateTime.parse(text, IN_FMT);
        } catch (DateTimeParseException e) {
            throw new LuluException("Invalid date/time after " + flag
                    + ". Use yyyy-MM-dd HH:mm (e.g. 2026-02-10 17:00)");
        }
    }

    private static String extractValue(String args, String flag) throws LuluException {
        int start = args.indexOf(flag);
        if (start == -1) {
            return null;
        }

        int valueStart = start + flag.length();
        if (valueStart >= args.length()) {
            throw new LuluException("Missing value after " + flag);
        }

        int end = nextFlagPos(args, valueStart);
        String value = (end == -1) ? args.substring(valueStart) : args.substring(valueStart, end);
        value = value.trim();

        if (value.isEmpty()) {
            throw new LuluException("Missing value after " + flag);
        }
        return value;
    }

    private static int nextFlagPos(String s, int fromIndex) {
        int d = s.indexOf(" /desc", fromIndex);
        int b = s.indexOf(" /by", fromIndex);
        int f = s.indexOf(" /from", fromIndex);
        int t = s.indexOf(" /to", fromIndex);

        int min = -1;
        if (d != -1) {
            min = d;
        }
        if (b != -1 && (min == -1 || b < min)) {
            min = b;
        }
        if (f != -1 && (min == -1 || f < min)) {
            min = f;
        }
        if (t != -1 && (min == -1 || t < min)) {
            min = t;
        }

        return min;
    }
}
