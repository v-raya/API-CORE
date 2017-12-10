/**
 * 
 */
package gov.ca.cwds.rest.resources;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

import gov.ca.cwds.rest.api.Request;

/**
 * Utility class. Validates parameter keys and API {@link Request} objects.
 * 
 * <p>
 * In Java 8 this functionality could be designed as an interface with default method behavior.
 * However, Cobertura is unable parse Java 8 features. See story #137202471.
 * </p>
 * 
 * @author CWDS API Team
 */
public class ResourceParamValidator {

  /**
   * Utility class. Default constructor intentionally hidden.
   */
  private ResourceParamValidator() {
    // Hide public, default constructor.
  }

  /**
   * Validate an incoming object, such as an API {@link Request} or key.
   * 
   * @param <T> Object type, typically extends
   * @param t Object to validate
   * @throws ConstraintViolationException if the key fails validation
   */
  public static final <T> void validate(T t) throws ConstraintViolationException {
    final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    final Set<ConstraintViolation<T>> violations = validator.validate(t);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

}
