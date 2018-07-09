package gov.ca.cwds.rest.validation;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

/**
 * Validates that if the {@code IfThen.ifProperty} of a given bean is not empty then the
 * {@code IfThen.thenProperty} is also not empty.
 * 
 * @author CWDS API Team
 */
public class IfCollectionContainsShortThenValidator
    implements AbstractBeanValidator, ConstraintValidator<IfCollectionContainsShortThen, Object> {

  private String ifProperty;
  private String thenProperty;
  private Short ifValue;

  @Override
  public void initialize(IfCollectionContainsShortThen constraintAnnotation) {
    this.ifProperty = constraintAnnotation.ifProperty();
    this.thenProperty = constraintAnnotation.thenProperty();
    this.ifValue = constraintAnnotation.ifValue();
  }

  @Override
  public boolean isValid(final Object bean, ConstraintValidatorContext context) {
    boolean valid = true;

    Collection ifCollection = (Collection) readBeanPropertyValue(bean, ifProperty);
    if (ifCollection != null && ifCollection.contains(ifValue)) {
      String thenValue = readBeanValue(bean, thenProperty);
      if (StringUtils.isBlank(thenValue)) {
        valid = false;
      }
    }

    return valid;
  }
}
