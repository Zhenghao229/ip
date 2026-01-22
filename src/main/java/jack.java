import java.util.*;

public class jack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Greeting
        System.out.println("Hello! I'm Jack.");
        System.out.println("What can I do for you?");

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            // Echo the input
            System.out.println(input);
        }
    }
}
