package gov.ca.cwds.rest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.cms.ApiSystemCodeCache;

public class SystemCodeValidator extends AbstractBeanValidator
    implements ConstraintValidator<SystemCode, Object> {

  @Inject
  private ApiSystemCodeCache systemCodes;

  private boolean required;

  @Override
  public void initialize(SystemCode constraintAnnotation) {
    this.required = constraintAnnotation.required();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    boolean valid = true;
    // ImmutableList.Builder<String> messages = new ImmutableList.Builder<>();
    // int countNotEmpty = 0;
    // for (String property : properties) {
    // String value = readBeanValue(bean, property);
    // if (StringUtils.isNotBlank(value)) {
    // countNotEmpty++;
    // }
    // }
    // if (required && countNotEmpty == 0) {
    // messages.add(MessageFormat.format("{0} must have one of their values set",
    // Arrays.toString(properties)));
    // valid = false;
    // }
    // if (countNotEmpty > 1) {
    // messages.add(MessageFormat.format("{0} are mutually exclusive but multiple values are set",
    // Arrays.toString(properties)));
    // valid = false;
    // }
    //
    // if (!valid) {
    // setupViolationMessages(context, messages.build());
    // }
    return valid;
  }


}
