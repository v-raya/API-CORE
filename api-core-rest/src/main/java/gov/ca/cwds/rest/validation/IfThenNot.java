package gov.ca.cwds.rest.validation;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author CWDS API Team
 * 
 * @see IfThenNotValidator
 */
@Target({TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = IfThenNotValidator.class)
public @interface IfThenNot {
  String message() default "";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  boolean required() default true;

  String ifProperty();

  String thenProperty();

  boolean ifValue();

  short thenNotValue();

  @Target({TYPE_USE})
  @Retention(RUNTIME)
  @interface List {
    IfThenNot[] value();
  }

}
