package gov.ca.cwds.drools.validation;

import gov.ca.cwds.drools.DroolsConfiguration;
import gov.ca.cwds.inject.InjectorHolder;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

/**
 * @author CWDS CALS API Team
 */
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class DroolsParamsValidator extends
    DroolsValidator<BusinessValidationParamsConstraint, Object[]>  {

  private DroolsConfiguration<Object[]> configuration;

  public void initialize(BusinessValidationParamsConstraint constraint) {
    this.configuration = InjectorHolder.INSTANCE.getInstance(ValidationConfigurationRegistry.class)
        .get(constraint.configuration());
  }

  @Override
  protected DroolsConfiguration<Object[]> getConfiguration() {
    return configuration;
  }

}
