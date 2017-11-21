package gov.ca.cwds.drools.validation;

import gov.ca.cwds.drools.DroolsConfiguration;
import gov.ca.cwds.dto.BaseDTO;
import gov.ca.cwds.inject.InjectorHolder;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

/**
 * @author CWDS CALS API Team
 */
@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class DroolsFieldValidator extends
    DroolsValidator<BusinessValidationFieldConstraint, BaseDTO> {

  private DroolsConfiguration<BaseDTO> configuration;

  public void initialize(BusinessValidationFieldConstraint constraint) {
    this.configuration = InjectorHolder.INSTANCE.getInstance(ValidationConfigurationRegistry.class)
        .get(constraint.configuration());
  }

  @Override
  protected DroolsConfiguration<BaseDTO> getConfiguration() {
    return configuration;
  }

}
