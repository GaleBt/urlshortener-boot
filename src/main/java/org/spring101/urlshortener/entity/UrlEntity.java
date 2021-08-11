package org.spring101.urlshortener.entity;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * JPA entity containing URI information
 */
@Entity
@Table(name = "url")
public class UrlEntity implements Serializable {

    /**
     * Database id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Original URI
     */
    @Column(name = "url", nullable = false)
    private String url;

    /**
     * URI hash
     */
    @Column(name = "url_hash", nullable = false)
    private String hash;

    /**
     * Date and time when URI was created
     */
    @Column(name = "created", nullable = false)
    private Instant created;

    /**
     * Date and time when URI will expire
     */
    @Column(name = "expires", nullable = false)
    private Instant expires;

    public UrlEntity() {
    }

    /**
     * JPA callback executed before saving created entity
     */
    @PrePersist
    public void beforeSave() {
        created = Instant.now();
        expires = Instant.now().plus(1, ChronoUnit.DAYS);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getExpires() {
        return expires;
    }

    public void setExpires(Instant expires) {
        this.expires = expires;
    }

}
