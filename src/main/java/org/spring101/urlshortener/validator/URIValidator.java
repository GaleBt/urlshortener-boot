package org.spring101.urlshortener.validator;

import java.net.URISyntaxException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * URI validator
 */
public class URIValidator implements ConstraintValidator<URI, String> {

    @Override
    public void initialize(URI annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        try {
            new java.net.URI(value);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

}
