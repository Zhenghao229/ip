package jack.ui;

import java.util.Scanner;

/**
 * Handles user interaction via the console.
 * Responsible for reading user commands and printing messages to the user.
 */
public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the welcome message at application start.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm Jack.Jack.");
        System.out.println("What can I do for you?");
    }

    /**
     * Reads the next line of user input.
     *
     * @return The trimmed user input string.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Reads the next line of user input.
     *
     * @return The trimmed user input string.
     */
    public void showLine() {
        System.out.println("____________________________________________");
    }

    /**
     * Displays an error message to the user.
     *
     * @param msg The error message content.
     */
    public void showError(String msg) {
        System.out.println("OOPS!!! " + msg);
    }

    /**
     * Displays the goodbye message at application exit.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Displays a generic message to the user.
     *
     * @param msg The message content.
     */
    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
