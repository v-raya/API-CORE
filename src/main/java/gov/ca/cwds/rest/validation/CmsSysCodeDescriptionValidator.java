package gov.ca.cwds.rest.validation;

import java.text.MessageFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;

import gov.ca.cwds.data.persistence.cms.ApiSystemCodeCache;

/**
 * Validates that the {@code CmsSysCode.property} of a given bean must be a valid CMS system code
 * description for its system code category, {@code CmsSysCode.category}.
 * 
 * @author CWDS API Team
 */
public class CmsSysCodeDescriptionValidator
    implements AbstractBeanValidator, ConstraintValidator<CmsSysCodeDescription, String> {

  private String category;
  private boolean required;

  @Override
  public void initialize(CmsSysCodeDescription anno) {
    this.category = anno.category();
    this.required = anno.required();
  }

  @Override
  public boolean isValid(final String value, ConstraintValidatorContext context) {
    boolean valid = false;
    final boolean hasProp = !StringUtils.isNotBlank(value);

    if (required || !Strings.isNullOrEmpty(value)) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(
              MessageFormat.format("{0} description is required", category))
          .addPropertyNode(category).addConstraintViolation();
    } else if (hasProp) {
      valid = ApiSystemCodeCache.global().verifyCategoryAndSysCodeShortDescription(category, value);
    }

    return valid;
  }

}
