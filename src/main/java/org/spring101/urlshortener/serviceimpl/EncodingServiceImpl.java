package org.spring101.urlshortener.serviceimpl;

import java.util.regex.Pattern;
import org.spring101.urlshortener.service.EncodingService;
import org.springframework.stereotype.Service;

/**
 * Provides encoding and decoding functionality
 */
@Service
public class EncodingServiceImpl implements EncodingService {

    private static final String ALLOWED_STRING = "abcdefghijklmnopqrstuvwxyz"
            + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "0123456789";
    
    private final char[] ALLOWED_CHARS = ALLOWED_STRING.toCharArray();
    
    private final int LENGTH = ALLOWED_CHARS.length;
    
    private final Pattern PATTERN = Pattern.compile("[a-zA-Z0-9]+");

    /**
     * Encodes database id to short form
     * 
     * @param identifier Database id
     * 
     * @return Short string corresponding to database id
     */
    @Override
    public String encode(Long identifier) {
        
        if(identifier < 0) {
            throw new IllegalArgumentException("Identifier cannot be negative");
        }
        
        StringBuilder encoded = new StringBuilder();

        if(identifier == 0) {
            return String.valueOf(ALLOWED_CHARS[0]);
        }

        while (identifier > 0) {
            encoded.append(ALLOWED_CHARS[(int) (identifier % LENGTH)]);
            identifier = identifier / LENGTH;
        }

        return encoded.reverse().toString();
        
    }

    /**
     * Decodes short string corresponding to database id to database idS
     * 
     * @param input Short string corresponding to database id
     * 
     * @return Database id
     */
    @Override
    public Long decode(String input) {
        
        if(input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        
        if("".equals(input)) {
            throw new IllegalArgumentException("Input cannot be empty");
        }
        
        if(!PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException("Input is invalid");
        }
        
        char[] chars = input.toCharArray();
        int length = chars.length;
        Long decoded = 0L;
        int counter = 1;
        
        for (int i = 0; i < length; i++) {
            decoded += Double.valueOf(ALLOWED_STRING.indexOf(chars[i]) * Math.pow(LENGTH, length - counter)).longValue();
            counter++;
        }
        
        return decoded;
        
    }

}
