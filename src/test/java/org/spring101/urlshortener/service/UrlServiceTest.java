package org.spring101.urlshortener.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring101.urlshortener.entity.UrlEntity;
import org.spring101.urlshortener.model.UriEncodeRequest;
import org.spring101.urlshortener.model.UriEncodeResponse;
import org.spring101.urlshortener.serviceimpl.EncodingServiceImpl;
import org.spring101.urlshortener.serviceimpl.HashServiceImpl;
import org.spring101.urlshortener.serviceimpl.UrlServiceImpl;
import org.spring101.urlshortener.repository.UriRepository;

/**
 *
 */
public class UrlServiceTest {

    private UriRepository urlRepository = mock(UriRepository.class);

    private EncodingService encodingService = mock(EncodingService.class);

    private HashService hashService = mock(HashService.class);

    private UriService urlService = new UrlServiceImpl(urlRepository, encodingService, hashService);

    private final String TEST_URI = "https://google.com";

    private final String TEST_IDENTIFIER = "a";

    private final String TEST_HASH = "hash";

    @BeforeEach()
    public void before() {

        UrlEntity entity = new UrlEntity();
        entity.setUrl(TEST_URI);
        entity.setCreated(Instant.now());
        entity.setExpires(Instant.now().plus(1, ChronoUnit.DAYS));
        entity.setHash(TEST_HASH);
        entity.setId(1L);

        when(hashService.hash(TEST_URI)).thenReturn(TEST_HASH);
        when(encodingService.encode(1L)).thenReturn(TEST_IDENTIFIER);
        when(encodingService.decode(TEST_IDENTIFIER)).thenReturn(1L);
        when(urlRepository.getByHash(eq(TEST_HASH))).thenReturn(Optional.of(entity));
        when(urlRepository.save(any(UrlEntity.class))).thenReturn(entity);
        when(urlRepository.findById(eq(1L))).thenAnswer(invocation -> Optional.of(entity));
    }

    @Test
    public void save() {
        UriEncodeRequest request = new UriEncodeRequest("https://google.com");
        UriEncodeResponse response = urlService.create(request);
        assertEquals(TEST_IDENTIFIER, response.getIdentifier());
    }

    @Test
    public void get() {
        String uri = urlService.get(TEST_IDENTIFIER);
        assertEquals(TEST_URI, uri);
    }

}
