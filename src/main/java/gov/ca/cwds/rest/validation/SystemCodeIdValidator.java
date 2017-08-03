package gov.ca.cwds.rest.validation;

import java.text.MessageFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;

/**
 * Validates that the {@code SystemCodeId.property} of a given bean must be a valid CMS system code
 * id for its system code category, {@code SystemCodeId.category}.
 * 
 * @author CWDS API Team
 */
public class SystemCodeIdValidator
    implements AbstractBeanValidator, ConstraintValidator<ValidSystemCodeId, Number> {

  private String category;
  private boolean required;
  private int ignoredValue;
  private boolean ignoreable;

  @Override
  public void initialize(ValidSystemCodeId anno) {
    this.category = anno.category();
    this.required = anno.required();
    this.ignoredValue = anno.ignoredValue();
    this.ignoreable = anno.ignoreable();
  }

  @Override
  public boolean isValid(final Number value, ConstraintValidatorContext context) {
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
      valid =
          SystemCodeCache.global().verifyActiveSystemCodeIdForMeta(value.shortValue(), category);
    }

    return valid;
  }

  public boolean isIgnorable(Number value){
    return !required && ignoreable && value != null && value.intValue() == ignoredValue ;
  }

}
