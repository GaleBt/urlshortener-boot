package org.spring101.urlshortener.model;

import javax.validation.constraints.NotNull;
import org.spring101.urlshortener.validator.URI;

/**
 * DTO for URI encoding request
 */
public class UriEncodeRequest {

    @NotNull(message = "URI should be not null")
    @URI()
    private String url;

    public UriEncodeRequest() {
    }

    public UriEncodeRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
