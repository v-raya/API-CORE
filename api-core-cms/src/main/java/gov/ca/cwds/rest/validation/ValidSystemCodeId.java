package gov.ca.cwds.rest.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation indicating that a property must be a valid CMS system code id for its system code
 * category.
 * 
 * @author CWDS API Team
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = SystemCodeIdValidator.class)
public @interface ValidSystemCodeId {


  String message() default " must be a valid system code for category {category}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String category();

  boolean checkCategoryIdValueIsZero() default false;

  boolean ignoreable() default false;

  int ignoredValue() default 0;

  boolean required() default false;

}
