package org.spring101.urlshortener.service;

import org.spring101.urlshortener.model.UriEncodeRequest;
import org.spring101.urlshortener.model.UriEncodeResponse;

/**
 * Provides functionality for creating short URIs and retrieving them from
 * database
 */
public interface UriService {
    
    /**
     * Saves URI data to database, returns shortened URI
     * 
     * @param request UriEncodeRequest
     * 
     * @return UriEncodeResponse
     */
    UriEncodeResponse create(UriEncodeRequest request);
    
    /**
     * Retrieves original URI from database using supplied identifier
     * 
     * @param identifier String
     * 
     * @return Original URI 
     */
    String get(String identifier);
    
}
