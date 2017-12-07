package gov.ca.cwds.drools.validation;

import gov.ca.cwds.drools.DroolsConfiguration;
import gov.ca.cwds.dto.BaseDTO;

/**
 * @author CWDS CALS API Team
 */
public class DroolsFieldValidationConfiguration<T extends BaseDTO> extends
    DroolsConfiguration<T> {

  public DroolsFieldValidationConfiguration(String sessionName, String agendaGroup,
      String kieContainerId) {
    super(sessionName, agendaGroup, kieContainerId);
  }

}
