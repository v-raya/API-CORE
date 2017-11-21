package gov.ca.cwds.drools.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import gov.ca.cwds.drools.DroolsConfiguration;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.inject.InjectorHolder;
import gov.ca.cwds.rest.exception.IssueDetails;
import gov.ca.cwds.utils.JsonUtils;
import java.lang.annotation.Annotation;
import java.util.Set;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author CWDS CALS API Team
 */
public abstract class DroolsValidator<A extends Annotation, T> implements
    ConstraintValidator<A, T> {

  @Override
  public boolean isValid(T obj, ConstraintValidatorContext context) {
    if (obj == null) {
      return true;
    }

    DroolsConfiguration<T> configuration = getConfiguration();
    Object validatedFact = configuration.getValidatedFact(obj);

    DroolsService droolsService = InjectorHolder.INSTANCE.getInstance(DroolsService.class);
    Set<IssueDetails> detailsList = droolsService.validate(validatedFact, configuration);
    if (detailsList.isEmpty()) {
      return true;
    } else {
      context.disableDefaultConstraintViolation();
      detailsList.forEach(
          (details ->
              context.buildConstraintViolationWithTemplate(marshallData(details))
                  .addPropertyNode("")
                  .addConstraintViolation()
          ));
      return false;
    }

  }

  public String marshallData(IssueDetails details) {
    try {
      return JsonUtils.to(details);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(
          "Cannot marshall validation details for message: " + details.getUserMessage(), e);
    }
  }

  protected abstract DroolsConfiguration<T> getConfiguration();

}
