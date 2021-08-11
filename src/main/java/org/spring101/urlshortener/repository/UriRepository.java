package org.spring101.urlshortener.repository;

import java.time.Instant;
import java.util.Optional;
import org.spring101.urlshortener.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring data repository
 */
@Repository
public interface UriRepository extends JpaRepository<UrlEntity, Long> {
    
    Optional<UrlEntity> getByHash(String hash);
    
    @Query("select count(*) from UrlEntity")
    long countAll();
    
    @Query("select count(*) from UrlEntity u where u.expires <= :now")
    long countExpired(@Param("now") Instant now);
    
}
