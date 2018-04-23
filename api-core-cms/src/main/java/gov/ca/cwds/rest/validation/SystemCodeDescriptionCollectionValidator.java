package gov.ca.cwds.rest.validation;

import java.text.MessageFormat;
import java.util.Collection;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * CWDS API Team
 */
public class SystemCodeDescriptionCollectionValidator implements
    AbstractBeanValidator, ConstraintValidator<ValidSystemCodeDescription, Collection<String>> {

  private SystemCodeDescriptionValidator valueValidator;
  private String category;
  private boolean required;

  @Override
  public void initialize(ValidSystemCodeDescription anno) {
    this.category = anno.category();
    this.required = anno.required();

    valueValidator = new SystemCodeDescriptionValidator();
    valueValidator.initialize(anno);
  }

  @Override
  public boolean isValid(Collection<String> values, ConstraintValidatorContext context) {
    final boolean hasProp = values != null &&  values.size() > 0;

    if (required && !hasProp) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(
              MessageFormat.format("{0} description is required", category))
          .addPropertyNode(category).addConstraintViolation();
    } else if (hasProp) {
      for (String value : values) {
        if (!valueValidator.isValid(value, context)) {
          return false;
        }
      }
    }
    return true;
  }
}
