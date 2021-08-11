package org.spring101.urlshortener.service;

/**
 * Provides hashing functionality
 */
public interface HashService {
    
    /**
     * Hashes string
     * 
     * @param source Source string
     * 
     * @return Hash
     */
    String hash (String source);
    
}
