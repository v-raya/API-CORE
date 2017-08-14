package gov.ca.cwds.rest.validation;

import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import java.text.MessageFormat;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LogicalIdValidator implements AbstractBeanValidator,
    ConstraintValidator<ValidLogicalId, String> {

//  private static final String SYSTEM_ID = "system_id";
//  public static final String LOGICAL_ID = "logical_id";
  private String category;
  private boolean required;
//  private String fieldName = SYSTEM_ID;

  @Override
  public void initialize(ValidLogicalId constraintAnnotation) {
    this.category = constraintAnnotation.category();
    this.required = constraintAnnotation.required();
  }

  @Override
  public boolean isValid(final String value, ConstraintValidatorContext context) {
    boolean valid = false;
    final boolean hasProp = value != null && !value.isEmpty();

    if (required && !hasProp) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(
          MessageFormat.format("{0} sys code is required", category))
          .addPropertyNode(category).addConstraintViolation();
    } else if (hasProp) {
        valid =
            SystemCodeCache.global().verifyActiveLogicalIdForMeta(value, category);
    }

    return valid;
  }

}
