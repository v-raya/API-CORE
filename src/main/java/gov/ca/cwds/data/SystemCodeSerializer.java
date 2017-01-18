package gov.ca.cwds.data;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotation;

@Retention(RUNTIME)
@Target({TYPE, FIELD, METHOD})
@JacksonAnnotation
public @interface SystemCodeSerializer {

  /**
   * Print the short description field, such as "California".
   * 
   * @return whether to print short description
   */
  boolean description() default true;

  /**
   * Print the "other code" field, as "CA" for California.
   * 
   * @return whether to print other code
   */
  boolean other() default false;
}
