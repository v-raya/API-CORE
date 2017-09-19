package gov.ca.cwds.rest.validation;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = IfCollectionContainsShortThenValidator.class)
public @interface IfCollectionContainsShortThen {

  String message() default "{thenProperty} must be set if {ifProperty} is set to {ifValue}";

  Class<?>[]groups() default {};

  Class<? extends Payload>[]payload() default {};

  String ifProperty();

  String thenProperty();

  short ifValue();

  @Target({TYPE_USE})
  @Retention(RUNTIME)
  @interface List {
    IfCollectionContainsShortThen[]value();
  }
}
