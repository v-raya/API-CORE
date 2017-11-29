package gov.ca.cwds.rest.validation;

import java.text.MessageFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validates that the {@code NotEqual.thenProperty} of a given bean is not same as
 * {@code NotEqual.ifProperty}
 * 
 * @author CWDS API Team
 *
 */
public class NotEqualValidator
    implements AbstractBeanValidator, ConstraintValidator<NotEqual, Object> {

  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(NotEqualValidator.class);

  private String ifProperty;
  private String thenProperty;

  @Override
  public void initialize(NotEqual anno) {
    this.ifProperty = anno.ifProperty();
    this.thenProperty = anno.thenProperty();
  }

  @Override
  public boolean isValid(final Object bean, ConstraintValidatorContext context) {
    boolean valid = true;

    String ifValue = readBeanValue(bean, ifProperty);
    String thenValue = readBeanValue(bean, thenProperty);
    boolean ifValueBlank = StringUtils.isNotBlank(ifValue);
    boolean thenValueBlank = StringUtils.isNotBlank(thenValue);

    if (ifValueBlank && thenValueBlank && ifValue.equals(thenValue)) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(
              MessageFormat.format("cant be same as {0}", ifProperty))
          .addPropertyNode(thenProperty).addConstraintViolation();
      valid = false;
    }
    return valid;
  }

}
