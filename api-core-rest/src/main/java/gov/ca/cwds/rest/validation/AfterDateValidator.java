package gov.ca.cwds.rest.validation;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * Validates that if the {@code AfterDateValid.thenProperty} of the given bean is greater than or
 * equal to the {@code AfterDateValid.ifProperty}
 * 
 * @author CWDS API Team
 *
 */
public class AfterDateValidator
    implements AbstractBeanValidator, ConstraintValidator<AfterDateValid, Object> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AfterDateValidator.class);

  private String ifProperty;
  private String thenProperty;

  @Override
  public void initialize(AfterDateValid constraintAnnotation) {
    this.ifProperty = constraintAnnotation.ifProperty();
    this.thenProperty = constraintAnnotation.thenProperty();
  }

  @Override
  public boolean isValid(final Object bean, ConstraintValidatorContext context) {
    boolean valid = true;
    if (StringUtils.isNotBlank(readBeanValue(bean, ifProperty))
        && StringUtils.isNotBlank(readBeanValue(bean, thenProperty))) {
      String ifPropertyValue = readBeanValue(bean, ifProperty);
      String thenPropertyValue = readBeanValue(bean, thenProperty);
      Date ifPropertyDate = null;
      Date thenPropertyDate = null;
      try {
        ifPropertyDate = new SimpleDateFormat(DomainChef.DATE_FORMAT).parse(ifPropertyValue);
        thenPropertyDate = new SimpleDateFormat(DomainChef.DATE_FORMAT).parse(thenPropertyValue);
      } catch (ParseException e) {
        LOGGER.error("Unable to validate date string {} or {}", thenProperty, ifProperty);
        valid = false;
      }

      if (thenPropertyDate != null && thenPropertyDate.before(ifPropertyDate)) {
        valid = false;
        context.disableDefaultConstraintViolation();
        context
            .buildConstraintViolationWithTemplate(
                MessageFormat.format("should be greater than or equal to {0}", ifProperty))
            .addPropertyNode(thenProperty).addConstraintViolation();
      }

    }
    return valid;
  }

}
