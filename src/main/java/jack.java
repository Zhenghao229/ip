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

            tasks[count] = new Task(input);
            System.out.println("added: " + input);
            count++;

        }
    }
}
