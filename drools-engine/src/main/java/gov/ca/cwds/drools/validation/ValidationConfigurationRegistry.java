package gov.ca.cwds.drools.validation;

import gov.ca.cwds.drools.DroolsConfiguration;

/**
 * @author CWDS CALS API Team
 */
@FunctionalInterface
public interface ValidationConfigurationRegistry {

  DroolsConfiguration get(String configurationName);

}
