package org.spring101.urlshortener.model;

/**
 * DTO for URI encoding response
 */
public class UriEncodeResponse {

    private String identifier;

    public UriEncodeResponse() {
    }

    public UriEncodeResponse(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}
