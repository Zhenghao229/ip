package lulu;

/**
 * Represents a custom exception for the Lulu application.
 * Thrown when user input or task operations are invalid.
 */
public class LuluException extends Exception {

    /**
     * Constructs a LuluException with the specified error message.
     *
     * @param msg The detail message describing the error.
     */
    public LuluException(String msg) {
        super(msg);
    }
}
