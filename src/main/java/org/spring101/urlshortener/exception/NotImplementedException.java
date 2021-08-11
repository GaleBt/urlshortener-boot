package org.spring101.urlshortener.exception;

/**
 * Exception is thrown when some functionality isn't implemented
 */
public class NotImplementedException extends RuntimeException {
    
    private final String message;
    
    public NotImplementedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
    
}
