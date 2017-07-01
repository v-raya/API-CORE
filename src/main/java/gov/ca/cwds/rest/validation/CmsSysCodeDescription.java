package gov.ca.cwds.rest.validation;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation indicating that a property must be a valid CMS system code description for its system
 * code category.
 * 
 * @author CWDS API Team
 */
@Target({TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = CmsSysCodeDescriptionValidator.class)
public @interface CmsSysCodeDescription {

  String message() default "{property} must be a valid system code for category {category}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String category();

  // String property();

  boolean required() default false;

}
