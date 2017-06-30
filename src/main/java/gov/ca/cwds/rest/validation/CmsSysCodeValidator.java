package gov.ca.cwds.rest.validation;

import java.text.MessageFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import gov.ca.cwds.data.persistence.cms.ApiSystemCodeCache;

/**
 * Validates that the {@code CmsSysCode.property} of a given bean must be a valid CMS system code
 * for its system code category, {@code CmsSysCode.category}.
 * 
 * @author CWDS API Team
 */
public class CmsSysCodeValidator
    implements AbstractBeanValidator, ConstraintValidator<CmsSysCode, Object> {

  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(CmsSysCodeValidator.class);

  private String category;
  private String property;
  private boolean required;

  @Override
  public void initialize(CmsSysCode anno) {
    this.category = anno.category();
    this.property = anno.property();
    this.required = anno.required();
  }

  @Override
  public boolean isValid(final Object bean, ConstraintValidatorContext context) {
    boolean valid = true;

    category = readBeanValue(bean, category);
    final boolean hasValue = !Strings.isNullOrEmpty(property);

    if (required && !hasValue) {
      context.disableDefaultConstraintViolation();
      context
          .buildConstraintViolationWithTemplate(MessageFormat.format("{0} is required", property))
          .addPropertyNode(property).addConstraintViolation();
      valid = false;
    } else if (hasValue) {
      try {
        final Integer sysId = Integer.parseInt(property);
        valid = ApiSystemCodeCache.global().verifyCategoryAndSysCode(category, sysId);
      } catch (NumberFormatException e) {
        LOGGER.warn("Cannot parse integer from {}", property);
      }
    }

    return valid;
  }

}
