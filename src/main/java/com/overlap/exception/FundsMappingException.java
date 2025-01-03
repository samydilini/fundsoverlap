package com.overlap.exception;

/**
 * Exception for handling Fund mapping issues
 */
public class FundsMappingException extends Throwable {
    public FundsMappingException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    private final String message;

}
