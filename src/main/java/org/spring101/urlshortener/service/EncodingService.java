package org.spring101.urlshortener.service;

/**
 * Provides encoding and decoding functionality
 */
public interface EncodingService {
    
    /**
     * Encodes database id to short form
     * 
     * @param identifier Database id
     * 
     * @return Short string corresponding to database id
     */
    String encode(Long identifier);
    
    /**
     * Decodes short string corresponding to database id to database idS
     * 
     * @param input Short string corresponding to database id
     * 
     * @return Database id
     */
    Long decode(String input);
    
}
