package gov.ca.cwds.rest.validation;

import java.text.MessageFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validates that if the {@code IfThenNot.ifProperty} of a given bean is set to some specific field
 * like(true or false) thenProprtey should be set to valid value. At present if handles the boolean
 * and short
 * 
 * @author CWDS API Team
 *
 */
public class IfThenNotValidator
    implements AbstractBeanValidator, ConstraintValidator<IfThenNot, Object> {

  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(IfThenNotValidator.class);

  private String ifProperty;
  private String thenProperty;
  private boolean ifValue;
  private short thenNotValue;

  @Override
  public void initialize(IfThenNot constraintAnnotation) {
    this.ifProperty = constraintAnnotation.ifProperty();
    this.thenProperty = constraintAnnotation.thenProperty();
    this.ifValue = constraintAnnotation.ifValue();
    this.thenNotValue = constraintAnnotation.thenNotValue();
  }

  @Override
  public boolean isValid(final Object bean, ConstraintValidatorContext context) {
    boolean valid = true;

    boolean ifPropertyValue = readBeanPropertyValue(bean, ifProperty) != null
        ? (Boolean) readBeanPropertyValue(bean, ifProperty)
        : false;

    short thenPropertyValue = readBeanPropertyValue(bean, thenProperty) != null
        ? (Short) readBeanPropertyValue(bean, thenProperty)
        : 0;

    if (ifPropertyValue == ifValue && thenNotValue == thenPropertyValue) {
      valid = false;
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(
              MessageFormat.format("is not valid since {0} is set to {1}", ifProperty, ifValue))
          .addPropertyNode(thenProperty).addConstraintViolation();
    }
    return valid;
  }
}
