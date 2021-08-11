package org.spring101.urlshortener.exception;

/**
 * Exception is thrown when URI is invalid
 */
public class InvalidUriException extends RuntimeException {

    private final String message;

    public InvalidUriException(String uri) {
        message = "URI \"" + uri + "\" is invalid";
    }

    @Override
    public String getMessage() {
        return message;
    }

}
