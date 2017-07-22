package gov.ca.cwds.rest.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * Check whether value is a valid date.
 * 
 * @author CWDS API Team
 */
public class DateValidator implements ConstraintValidator<Date, String> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DateValidator.class);

  private String format;
  private boolean required;

  @Override
  public void initialize(Date constraintAnnotation) {
    this.format = constraintAnnotation.format();
    this.required = constraintAnnotation.required();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    DateFormat df = new SimpleDateFormat(format);
    df.setLenient(false);
    boolean valid = false;

    if (Strings.isNullOrEmpty(value)) {
      valid = !required;
    } else {
      try {
        df.parse(value);
        valid = true;
      } catch (ParseException e) {
        LOGGER.warn("Unable to validate date string {} with format {}", value, format);
      }
    }
    return valid;
  }
}
