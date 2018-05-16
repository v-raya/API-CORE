package gov.ca.cwds.authorizer;

import com.google.inject.Inject;

import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.authorizer.drools.configuration.ClientResultAuthorizationDroolsConfiguration;

/**
 * ABAC Authorizer that implements Client Result portion of IBM Security Guide.
 */
public class ClientResultReadAuthorizer extends ClientBaseReadAuthorizer {

  public static final String CLIENT_RESULT_READ = "client:readResult";
  public static final String CLIENT_RESULT_READ_OBJECT = CLIENT_RESULT_READ + ":client";

  @Inject
  public ClientResultReadAuthorizer(DroolsAuthorizationService droolsAuthorizationService,
      ClientResultAuthorizationDroolsConfiguration droolsConfiguration) {
    super(droolsAuthorizationService, droolsConfiguration);
  }

}
