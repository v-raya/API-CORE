package gov.ca.cwds.authorizer.drools.configuration;

import gov.ca.cwds.drools.DroolsConfiguration;

@FunctionalInterface
public interface DroolsAuthorizer {

  DroolsConfiguration getInstance();

}
