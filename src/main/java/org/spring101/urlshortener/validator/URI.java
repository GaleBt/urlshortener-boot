package org.spring101.urlshortener.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation for URI validation
 */
@Constraint(validatedBy = URIValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface URI {

    String message() default "is not a valid URI";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
