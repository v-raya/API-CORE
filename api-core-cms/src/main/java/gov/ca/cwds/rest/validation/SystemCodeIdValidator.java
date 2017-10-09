package gov.ca.cwds.rest.validation;

import java.text.MessageFormat;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;

/**
 * Validates that the {@code SystemCodeId.property} of a given bean must be a valid CMS system code
 * id for its system code category, {@code SystemCodeId.category}.
 * 
 * @author CWDS API Team
 */
public class SystemCodeIdValidator
    implements AbstractBeanValidator, ConstraintValidator<ValidSystemCodeId, Object> {

  private String message;
  private String category;
  private boolean required;
  private int ignoredValue;
  private boolean ignoreable;
  private boolean checkCategoryIdValueIsZero;

  @Override
  public void initialize(ValidSystemCodeId anno) {
    this.category = anno.category();
    this.required = anno.required();
    this.ignoredValue = anno.ignoredValue();
    this.ignoreable = anno.ignoreable();
    this.checkCategoryIdValueIsZero = anno.checkCategoryIdValueIsZero();
    this.message = anno.message();
  }

  @Override
  public boolean isValid(final Object param, ConstraintValidatorContext context) {
    if (param == null) {
      return (!required && ignoreable);
    } else {
      return validateObject(param, context);
    }
  }

  private boolean validateObject(Object param, ConstraintValidatorContext context) {
    if (param instanceof Set) {
      return validateSet((Set<?>) param, context);
    }
    return validateString(param.toString(), context);
  }

  private boolean validateSet(Set<?> param, ConstraintValidatorContext context) {

    if (param.isEmpty()) {
      return (!required && ignoreable);
    }
    for (Object value : param) {
      if (!validateString(value.toString(), context)) {
        return false;
      }
    }
    return true;
  }

  private boolean validateString(String value, ConstraintValidatorContext context) {
    if (StringUtils.isNumeric(value)) {
      Integer numericValue = Integer.valueOf((String) value);
      return isSystemCodeValid(numericValue, context);
    }
    return false;
  }

  private boolean isIgnorable(Number value) {
    return !required && ignoreable && hasIgnorableValue(value);
  }

  private boolean hasIgnorableValue(Number value) {
    return value == null || (value.intValue() == ignoredValue);
  }


  private Boolean isSystemCodeValid(final Number value, ConstraintValidatorContext context) {

    boolean valid = false;
    final boolean hasProp = value != null && value.intValue() != 0;

    if (required && !hasProp) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(
              MessageFormat.format("{0} sys code is required", category))
          .addPropertyNode(category).addConstraintViolation();
    } else if (isIgnorable(value)) {
      valid = true;
    } else if (hasProp) {
      if (checkCategoryIdValueIsZero) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message + " where category id is 0")
            .addConstraintViolation();
      }
      valid = SystemCodeCache.global().verifyActiveSystemCodeIdForMeta(value.shortValue(), category,
          checkCategoryIdValueIsZero);
    }

    return valid;
  }
}


