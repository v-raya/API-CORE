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

  boolean description() default true;

  boolean other() default false;
}
