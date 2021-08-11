package org.spring101.urlshortener.serviceimpl;

import java.net.MalformedURLException;
import org.spring101.urlshortener.service.HashService;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import org.spring101.urlshortener.exception.InvalidUriException;
import org.spring101.urlshortener.exception.NotImplementedException;
import org.springframework.stereotype.Service;

/**
 * Provides hashing functionality
 */
@Service
public class HashServiceImpl implements HashService {

    private final MessageDigest DIGEST;

    public HashServiceImpl() {
        try {
            DIGEST = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new NotImplementedException("Failed to initialize SHA-256 message digest");
        }
    }

    /**
     * Hashes string
     * 
     * @param source Source string
     * 
     * @return Hash
     */
    @Override
    public String hash(String source) {
        try {
            URL url = new URL(source);
            URI uri = new URI(
                    url.getProtocol().toLowerCase(),
                    url.getUserInfo(),
                    url.getHost().toLowerCase(),
                    url.getPort(),
                    url.getPath(),
                    url.getQuery(),
                    url.getRef()
            );
            return Base64.getEncoder()
                    .encodeToString(
                            DIGEST.digest(uri.toString().getBytes(StandardCharsets.UTF_8))
                    );
        } catch (MalformedURLException | URISyntaxException e) {
            throw new InvalidUriException(source);
        }
    }

}
