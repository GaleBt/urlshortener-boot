package org.spring101.urlshortener.exception;

/**
 * Exception is thrown, when URI is expired
 */
public class UriExpiredException extends RuntimeException {
    
    private final String message;
    
    public UriExpiredException(String identifier) {
        this.message = "Link identifier \"" + identifier + "\" expired";
    }

    @Override
    public String getMessage() {
        return message;
    }
    
}
