package gov.ca.cwds.rest.validation;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = NotEqualValidator.class)
public @interface NotEqual {
  String message() default "{thenProperty} cant be same as {ifProperty}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String ifProperty();

  String thenProperty();

}
