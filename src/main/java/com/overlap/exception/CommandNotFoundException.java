package com.overlap.exception;

/**
 * Exception for handling missing commands.
 */
public class CommandNotFoundException extends Exception {
    private final String message;

    /**
     * Constructs a new CommandNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public CommandNotFoundException(String message) {
        super();
        this.message = message;
    }

    /**
     * Returns the detail message string of this exception.
     *
     * @return the detail message string
     */
    @Override
    public String getMessage() {
        return message;
    }
}
