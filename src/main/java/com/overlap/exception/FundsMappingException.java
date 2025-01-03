package com.overlap.exception;

/**
 * Exception for handling Fund mapping issues.
 */
public class FundsMappingException extends Throwable {
    /**
     * The detail message for this exception.
     */
    private final String message;

    /**
     * Constructs a new FundsMappingException with the specified detail message.
     *
     * @param message the detail message
     */
    public FundsMappingException(String message) {
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
