package com.overlap.exception;

/**
 * This is the exception used to handle overall exceptions
 */
public class FundsOverlapException extends Exception {
    @Override
    public String getMessage() {
        return message;
    }

    private final String message;

    public FundsOverlapException(String message) {
        super();
        this.message = message;
    }
}
