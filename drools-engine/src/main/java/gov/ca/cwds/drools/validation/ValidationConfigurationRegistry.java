package gov.ca.cwds.drools.validation;

import gov.ca.cwds.drools.DroolsConfiguration;

/**
 * @author CWDS CALS API Team
 */

public interface ValidationConfigurationRegistry {

  DroolsConfiguration get(String configurationName);

}
