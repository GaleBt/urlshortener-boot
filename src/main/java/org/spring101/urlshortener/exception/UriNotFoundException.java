package org.spring101.urlshortener.exception;

/**
 * Exception is thrown, when URI is not found
 */
public class UriNotFoundException extends RuntimeException {
    
    private final String message;
    
    public UriNotFoundException(String identifier) {
        this.message = "No identifier \"" + identifier + "\" found";
    }

    @Override
    public String getMessage() {
        return message;
    }
    
}
