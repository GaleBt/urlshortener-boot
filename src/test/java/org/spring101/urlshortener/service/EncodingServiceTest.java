package org.spring101.urlshortener.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.spring101.urlshortener.serviceimpl.EncodingServiceImpl;

/**
 *
 */
public class EncodingServiceTest {
    
    private final EncodingService encodingService = new EncodingServiceImpl();
    
    @Test
    public void encodeDecodeZero() {
        Long identifier = 0L;
        assertEquals(identifier, encodingService.decode(encodingService.encode(identifier)));
    }
    
    @Test
    public void encodeDecodeIntegerMaxValue() {
        Long identifier = (long)Integer.MAX_VALUE;
        assertEquals(identifier, encodingService.decode(encodingService.encode(identifier)));
    }
    
    @Test
    public void encodeDecodeLongMaxValue() {
        Long identifier = Long.MAX_VALUE;
        assertEquals(identifier, encodingService.decode(encodingService.encode(identifier)));
    }
    
    @Test
    public void encodeNegative() {
        Long identifier = -1L;
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> encodingService.encode(identifier)
        );
        assertEquals("Identifier cannot be negative", exception.getMessage());
    }
    
    @Test
    public void decodeNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> encodingService.decode(null)
        );
        assertEquals("Input cannot be null", exception.getMessage());
    }
    
    @Test
    public void decodeEmptyString() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> encodingService.decode("")
        );
        assertEquals("Input cannot be empty", exception.getMessage());
    }
    
    @Test
    public void decodeInvalidString() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> encodingService.decode("test!")
        );
        assertEquals("Input is invalid", exception.getMessage());
    }
    
}
