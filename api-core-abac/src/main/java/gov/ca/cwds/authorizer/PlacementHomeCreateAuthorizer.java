package gov.ca.cwds.authorizer;

import com.google.inject.Inject;
import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.authorizer.drools.configuration.PlacementHomeAuthorizationDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;

/**
 * @author CWDS TPT-2 Team
 */
public class PlacementHomeCreateAuthorizer extends AbstractBaseAuthorizer<PlacementHome, String> {

  @Inject
  private PlacementHomeAuthorizationDroolsConfiguration droolsConfiguration;

  @Inject
  public PlacementHomeCreateAuthorizer(DroolsAuthorizationService droolsAuthorizationService) {
    super(droolsAuthorizationService);
  }

  @Override
  protected boolean checkInstance(final PlacementHome placementHome) {
    return authorizeInstanceOperation(placementHome, droolsConfiguration, null);
  }

  public void setDroolsConfiguration(
      PlacementHomeAuthorizationDroolsConfiguration droolsConfiguration) {
    this.droolsConfiguration = droolsConfiguration;
  }
}
