package gov.ca.cwds.rest.validation;

import java.text.MessageFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;

/**
 * Validates that the {@code CmsSysCode.property} of a given bean must be a valid CMS system code
 * description for its system code category, {@code CmsSysCode.category}.
 * 
 * @author CWDS API Team
 */
public class SystemCodeDescriptionValidator
    implements AbstractBeanValidator, ConstraintValidator<ValidSystemCodeDescription, String> {

  private String category;
  private boolean required;

  @Override
  public void initialize(ValidSystemCodeDescription anno) {
    this.category = anno.category();
    this.required = anno.required();
  }

  @Override
  public boolean isValid(final String value, ConstraintValidatorContext context) {
    boolean valid = false;
    final boolean hasProp = StringUtils.isNotBlank(value);

    if (required && !hasProp) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(
              MessageFormat.format("{0} description is required", category))
          .addPropertyNode(category).addConstraintViolation();
    } else if (hasProp) {
      valid = SystemCodeCache.global().verifyActiveSystemCodeDescriptionForMeta(value, category);
    }

    return valid;
  }

}
