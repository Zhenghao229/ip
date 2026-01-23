import java.util.*;

public class jack {
    private static void printError(String msg) {
        System.out.println("OOPS!!!" + msg);
    }

    private static int parseTaskNumber(String input, String keyword) throws JackException {
        String numberPart = input.substring(keyword.length()).trim(); // after "mark " or "unmark "
        if (numberPart.isEmpty()) {
            throw new JackException("Please provide a task number, e.g. " + keyword.trim() + " 2");
        }
        try {
            return Integer.parseInt(numberPart);
        } catch (NumberFormatException e) {
            throw new JackException("Task number must be a number, e.g. " + keyword.trim() + " 2");
        }
    }

    private static Task parseTask(String input) throws JackException {

        if (input.equals("todo")) {
            throw new JackException("The description of a todo cannot be empty.");
        }
        if (input.startsWith("todo ")) {
            String desc = input.substring(5).trim();
            if (desc.isEmpty()) {
                throw new JackException("The description of a todo cannot be empty.");
            }
            return new Todo(desc);
        }

        if (input.equals("deadline")) {
            throw new JackException("The description of a deadline cannot be empty.");
        }
        if (input.startsWith("deadline ")) {
            String rest = input.substring(9).trim();
            if (!rest.contains(" /by ")) {
                throw new JackException("Deadline format: deadline <description> /by <when>");
            }
            String[] parts = rest.split(" /by ", 2);
            String desc = parts[0].trim();
            String by = parts[1].trim();
            if (desc.isEmpty() || by.isEmpty()) {
                throw new JackException("Deadline format: deadline <description> /by <when>");
            }
            return new Deadline(desc, by);
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

        // unknown command
        throw new JackException("I'm sorry, but I don't know what that means :-(");
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // storage for tasks
        Task[] tasks = new Task[100];
        int count = 0;

        // Greeting
        System.out.println("Hello! I'm Jack.");
        System.out.println("What can I do for you?");

        while (true) {
            String input = scanner.nextLine().trim();
try {
    if (input.equals("bye")) {
        System.out.println("Bye. Hope to see you again soon!");
        break;
    }

    if (input.equals("list")) {
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
        continue;
    }

    if (input.startsWith("mark ")) {
        int taskNo = parseTaskNumber(input, "mark");
        int idx = taskNo - 1;//0-based

        if (idx < 0 || idx >= count) {
            throw new JackException("That task number is out of range.");
        }

        tasks[idx].markAsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + tasks[idx]);
        continue;
    }

    if (input.startsWith("unmark ")) {
        int taskNo = parseTaskNumber(input, "unmark");
        int idx = taskNo - 1;

        if (idx < 0 || idx >= count) {
            throw new JackException("That task number is out of range.");
        }

        tasks[idx].markAsNotDone();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + tasks[idx]);
        continue;
    }
    /*
    //-----Level 4------------
    Task newTask;

    if (input.startsWith("todo ")) {
        String desc = input.substring(5).trim();
        newTask = new Todo(desc);
    } else if (input.startsWith("deadline ")) {
        String rest = input.substring(9).trim();
        String[] parts = rest.split(" /by ", 2);
        String desc = parts[0].trim();
        String by = parts[1].trim();
        newTask = new Deadline(desc, by);
    } else if (input.startsWith("event ")) {
        String rest = input.substring(6).trim();
        String[] p1 = rest.split(" /from ", 2);
        String desc = p1[0].trim();
        String[] p2 = p1[1].split(" /to ", 2);
        String from = p2[0].trim();
        String to = p2[1].trim();
        newTask = new Event(desc, from, to);
    } else {
        newTask = new Todo(input);
    }
*/
    Task newTask = parseTask(input);
    tasks[count] = newTask;
    count++;

    System.out.println("Got it. I've added this task:");
    System.out.println("  " + newTask);
    System.out.println("Now you have " + count + " tasks in the list.");
} catch (JackException e){
    printError(e.getMessage());
}

        }
    }
}
