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
 * @see ApiSysCodeAware
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
   * Print the "logical id" field, such as "CA" for California.
   * 
   * @return whether to print logical id
   */
  boolean logical() default false;

  /**
   * Print the "meta" field (the code category), such as "state" or "placement type".
   * 
   * @return whether to print "meta" field (the system code category)
   */
  boolean meta() default false;
}
