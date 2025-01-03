package com.overlap.exception;

/**
 * Exception for handling missing commands
 */
public class CommandNotFoundException extends Exception {
    public CommandNotFoundException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    private final String message;
}
