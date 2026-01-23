import java.util.*;

public class jack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // storage for tasks
        Task[] tasks = new Task[100];
        int count = 0;

        // Greeting
        System.out.println("Hello! I'm Jack.");
        System.out.println("What can I do for you?");

        while (true) {
            String input = scanner.nextLine();

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

            if(input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5).trim()) - 1;//0-based
                tasks[index].markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[index]);
                continue;
            }

            if(input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7).trim()) - 1;//0-based
                tasks[index].markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[index]);
                continue;
            }
            //-----Level 4------------
            Task newTask;

            if(input.startsWith("todo ")) {
                String desc = input.substring(5).trim();
                newTask = new Todo(desc);
            } else if (input.startsWith("deadline ")) {
                String rest = input.substring(9).trim();
                String[] parts = rest.split(" /by ", 2);
                String desc = parts[0].trim();
                String by = parts[1].trim();
                newTask = new Deadline(desc,by);
            } else if (input.startsWith("event ")) {
                String rest = input.substring(6).trim();
                String[] p1 = rest.split(" /from ", 2);
                String desc = p1[0].trim();
                String[] p2 =p1[1].split(" /to ",2);
                String from = p2[0].trim();
                String to = p2[1].trim();
                newTask = new Event(desc,from,to);
            }else {
                newTask = new Todo(input);
            }


            tasks[count] = newTask;
            count++;

            System.out.println("Got it. I've added this task:");
            System.out.println("  " + newTask);
            System.out.println("Now you have " + count + " tasks in the list.");

        }
    }
}
