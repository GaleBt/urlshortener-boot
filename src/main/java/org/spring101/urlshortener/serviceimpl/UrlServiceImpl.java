package org.spring101.urlshortener.serviceimpl;

import java.time.Instant;
import java.util.Optional;
import org.spring101.urlshortener.entity.UrlEntity;
import org.spring101.urlshortener.exception.UriExpiredException;
import org.spring101.urlshortener.exception.UriNotFoundException;
import org.spring101.urlshortener.model.UriEncodeRequest;
import org.spring101.urlshortener.model.UriEncodeResponse;
import org.spring101.urlshortener.service.EncodingService;
import org.spring101.urlshortener.service.HashService;
import org.springframework.stereotype.Service;
import org.spring101.urlshortener.service.UriService;
import org.spring101.urlshortener.repository.UriRepository;

/**
 * Provides functionality for creating short URIs and retrieving them from
 * database
 */
@Service
public class UrlServiceImpl implements UriService {

    private final UriRepository urlRepository;
    
    private final EncodingService encodingService;
    
    private final HashService hashService;

    public UrlServiceImpl(
            UriRepository urlRepository, 
            EncodingService encodingService,
            HashService hashService
    ) {
        this.urlRepository = urlRepository;
        this.encodingService = encodingService;
        this.hashService = hashService;
    }
    
    /**
     * Saves URI data to database, returns shortened URI
     * 
     * @param request UriEncodeRequest
     * 
     * @return UriEncodeResponse
     */
    @Override
    public UriEncodeResponse create(UriEncodeRequest request) {
        
        String hash = hashService.hash(request.getUrl());
        
        Optional<UrlEntity> optional = urlRepository.getByHash(hash);
        if(optional.isPresent()) {
            UrlEntity entity = optional.get();
            return new UriEncodeResponse(encodingService.encode(entity.getId()));
        }
        
        UrlEntity entity = new UrlEntity();
        entity.setUrl(request.getUrl());
        entity.setHash(hash);
        entity = urlRepository.save(entity);

        return new UriEncodeResponse(encodingService.encode(entity.getId()));
    }

    /**
     * Retrieves original URI from database using supplied identifier
     * 
     * @param identifier String
     * 
     * @return Original URI 
     */
    @Override
    public String get(String identifier) {
        
        Long id = encodingService.decode(identifier);
        
        UrlEntity entity = urlRepository.findById(id)
                .orElseThrow(() -> new UriNotFoundException(identifier));

        if (entity.getExpires() != null && entity.getExpires().isBefore(Instant.now())){
            urlRepository.delete(entity);
            throw new UriExpiredException(identifier);
        }

        return entity.getUrl();
    }
    
}
