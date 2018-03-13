package gov.ca.cwds.authorizer;

import com.google.inject.Inject;
import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.authorizer.drools.configuration.SubstituteCareProviderAuthorizationDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;

/**
 * @author CWDS TPT-2 Team
 */
public class SubstituteCareProviderCreateAuthorizer extends AbstractBaseAuthorizer<SubstituteCareProvider, String> {

  @Inject
  private SubstituteCareProviderAuthorizationDroolsConfiguration droolsConfiguration;

  @Inject
  public SubstituteCareProviderCreateAuthorizer(DroolsAuthorizationService droolsAuthorizationService) {
    super(droolsAuthorizationService);
  }

  @Override
  protected boolean checkInstance(final SubstituteCareProvider substituteCareProvider) {
    return authorizeInstanceOperation(substituteCareProvider, droolsConfiguration, null);
  }

  public void setDroolsConfiguration(
      SubstituteCareProviderAuthorizationDroolsConfiguration droolsConfiguration) {
    this.droolsConfiguration = droolsConfiguration;
  }
}
