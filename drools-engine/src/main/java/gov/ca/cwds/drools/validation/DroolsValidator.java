package gov.ca.cwds.drools.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import gov.ca.cwds.drools.DroolsConfiguration;
import gov.ca.cwds.drools.DroolsErrorMessages;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.inject.InjectorHolder;
import gov.ca.cwds.rest.exception.IssueDetails;
import gov.ca.cwds.utils.JsonUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CWDS CALS API Team
 */
public abstract class DroolsValidator<A extends Annotation, T> implements
    ConstraintValidator<A, T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DroolsValidator.class);

  @Override
  public boolean isValid(T obj, ConstraintValidatorContext context) {
    if (obj == null) {
      return true;
    }

    DroolsConfiguration<T> configuration = getConfiguration();
    Object validatedFact = configuration.getValidatedFact(obj);

    DroolsService droolsService = InjectorHolder.INSTANCE.getInstance(DroolsService.class);
    Set<IssueDetails> detailsList = null;
    try {
      detailsList = droolsService.performBusinessRules(configuration, validatedFact);
    } catch (DroolsException e) {
      LOGGER.warn(e.getMessage(), e);
      throw new RuntimeException(
          String.format(DroolsErrorMessages.CANT_PERFORM_BUSINESS_VALIDATION,
              configuration.getAgendaGroup()));
    }
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
