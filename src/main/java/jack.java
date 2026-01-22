import java.util.*;

public class jack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // storage for tasks
        String[] items = new String[100];
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
                    System.out.println((i + 1) + ". " + items[i]);
                }
                continue;
            }

            items[count] = input;
            count++;

            // Echo the input
            System.out.println("added:" + input);
        }
    }
}
