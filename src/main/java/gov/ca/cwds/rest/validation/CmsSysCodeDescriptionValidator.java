package gov.ca.cwds.rest.validation;

import java.text.MessageFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.persistence.cms.ApiSystemCodeCache;

/**
 * Validates that the {@code CmsSysCode.property} of a given bean must be a valid CMS system code
 * description for its system code category, {@code CmsSysCode.category}.
 * 
 * @author CWDS API Team
 */
public class CmsSysCodeDescriptionValidator
    implements AbstractBeanValidator, ConstraintValidator<CmsSysCodeId, Object> {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CmsSysCodeDescriptionValidator.class);

  private String category;
  private String property;
  private boolean required;

  @Override
  public void initialize(CmsSysCodeId anno) {
    this.category = anno.category();
    this.property = anno.property();
    this.required = anno.required();
  }

  @Override
  public boolean isValid(final Object bean, ConstraintValidatorContext context) {
    boolean valid = true;

    category = readBeanValue(bean, category);
    final boolean hasValue = !StringUtils.isNotBlank(property);

    if (required && !hasValue) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(MessageFormat.format("{0} is required", property))
          .addPropertyNode(property).addConstraintViolation();
      valid = false;
    } else if (hasValue) {
      valid = ApiSystemCodeCache.global().verifyCategoryAndSysCode(category, property);
    }

    return valid;
  }

}
