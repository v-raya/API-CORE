package gov.ca.cwds.data;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.google.inject.BindingAnnotation;

import gov.ca.cwds.data.persistence.cms.ISystemCodeCache;

/**
 * CWDS API annotation marks fields, methods, and parameters which should be translated via
 * {@link ISystemCodeCache}.
 * 
 * @author CWDS API Team
 * @see ISysCodeAware
 * @see CmsSystemCodeSerializer
 */
@Documented
@Retention(RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@BindingAnnotation
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
