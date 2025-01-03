package com.overlap.exception;

/**
 * This is the exception used to handle overall exceptions.
 */
public class FundsOverlapException extends Exception {
    /**
     * The detail message for this exception.
     */
    private final String message;

    /**
     * Constructs a new FundsOverlapException with the specified detail message.
     *
     * @param message the detail message
     */
    public FundsOverlapException(String message) {
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
