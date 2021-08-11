package org.spring101.urlshortener.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.spring101.urlshortener.exception.InvalidUriException;
import org.spring101.urlshortener.serviceimpl.HashServiceImpl;

/**
 *
 */
public class HashServiceTest {

    private final HashService hashService = new HashServiceImpl();

    @Test
    public void domain2dLevelIgnoreCase() {
        assertEquals(
                hashService.hash("https://GOOGLE.com"),
                hashService.hash("https://google.com")
        );
    }

    @Test
    public void domain1stLevelIgnoreCase() {
        assertEquals(
                hashService.hash("https://google.COM"),
                hashService.hash("https://google.com")
        );
    }

    @Test
    public void protocolDiffers() {
        assertNotEquals(
                hashService.hash("http://google.com"),
                hashService.hash("https://google.com")
        );
    }

    @Test
    public void protocolIgnoreCase() {
        assertEquals(
                hashService.hash("HTTPS://google.com"),
                hashService.hash("https://google.com")
        );
    }

    @Test
    public void queryIgnoreCase() {
        assertNotEquals(
                hashService.hash("https://yandex.ru?text=маски%20купить%20спб"),
                hashService.hash("https://yandex.ru?text=МАСКИ%20купить%20спб")
        );
    }

    @Test
    public void emptyString() {
        InvalidUriException exception = assertThrows(
                InvalidUriException.class,
                () -> hashService.hash("")
        );
        assertEquals("URI \"\" is invalid", exception.getMessage());
    }
    
    @Test
    public void nullString() {
        InvalidUriException exception = assertThrows(
                InvalidUriException.class,
                () -> hashService.hash(null)
        );
        assertEquals("URI \"null\" is invalid", exception.getMessage());
    }
    
}
