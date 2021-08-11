package org.spring101.urlshortener.controller;

import java.net.URI;
import javax.validation.Valid;
import org.spring101.urlshortener.model.UriEncodeRequest;
import org.spring101.urlshortener.model.UriEncodeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.spring101.urlshortener.service.UriService;

/**
 * Provides web API for managing URIs
 */
@RestController
@RequestMapping("/url-shortener")
public class UrlController {

    /**
     * URI service
     */
    private final UriService uriService;

    /**
     * Constructor injects dependencies
     * 
     * @param urlService UriService
     */
    public UrlController(UriService urlService) {
        this.uriService = urlService;
    }

    /**
     * Returns shortened URI
     * 
     * @param request UrlEncodeRequest
     * 
     * @return UrlEncodeResponse
     */
    @PostMapping("/create")
    public UriEncodeResponse convertToShortUrl(
            @Valid @RequestBody UriEncodeRequest request
    ) {
        return uriService.create(request);
    }
    
    /**
     * Redirects to the original URI using supplied URI identifier
     * 
     * @param identifier String
     * 
     * @return 
     */
    @GetMapping("/{identifier}")
    public ResponseEntity<Void> getAndRedirect(
            @PathVariable String identifier
    ) {
        String url = uriService.get(identifier);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
    
}
