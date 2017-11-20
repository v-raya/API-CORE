package gov.ca.cwds.rest.validation;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = AfterDateValidator.class)
public @interface AfterDateValid {

  String message() default "{thenProperty} must be greater than or equal to {ifProperty}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  boolean required() default true;

  String ifProperty();

  String thenProperty();

  @Target({TYPE_USE})
  @Retention(RUNTIME)
  @interface List {
    IfThen[] value();
  }

}
