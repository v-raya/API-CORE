package gov.ca.cwds.authorizer;

import com.google.inject.Inject;
import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.authorizer.drools.configuration.ClientAuthorizationDroolsConfiguration;

/**
 * @author CWDS TPT-3 Team
 */
public class ClientAbstractReadAuthorizer extends ClientBaseReadAuthorizer {

  @Inject
  private ClientAuthorizationDroolsConfiguration droolsConfiguration;

  @Inject
  public ClientAbstractReadAuthorizer(
      DroolsAuthorizationService droolsAuthorizationService) {
    super(droolsAuthorizationService);
    setDroolsConfiguration(droolsConfiguration);
  }
}
