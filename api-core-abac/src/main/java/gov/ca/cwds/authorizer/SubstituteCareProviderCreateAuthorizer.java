package gov.ca.cwds.authorizer;

import static gov.ca.cwds.authorizer.util.StaffPrivilegeUtil.toStaffPersonPrivilegeTypes;

import com.google.inject.Inject;
import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.security.authorizer.BaseAuthorizer;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.realm.PerrySubject;
import java.util.Set;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CWDS TPT-2 Team
 */
public class SubstituteCareProviderCreateAuthorizer extends BaseAuthorizer<SubstituteCareProvider, Long> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SubstituteCareProviderCreateAuthorizer.class);

  private final DroolsAuthorizationService droolsAuthorizationService;

  @Inject
  public SubstituteCareProviderCreateAuthorizer(DroolsAuthorizationService droolsAuthorizationService) {
    this.droolsAuthorizationService = droolsAuthorizationService;
  }

  @Override
  protected boolean checkInstance(final SubstituteCareProvider substituteCareProvider) {
    final PerryAccount perryAccount = PerrySubject.getPerryAccount();
    final Set<StaffPrivilegeType> staffPrivilegeTypes = toStaffPersonPrivilegeTypes(perryAccount);
    if (staffPrivilegeTypes.isEmpty()) {
      return false;
    }

    return authorizeSubstituteCareProviderCreateOperation(staffPrivilegeTypes, substituteCareProvider, perryAccount);
  }

  private boolean authorizeSubstituteCareProviderCreateOperation(
      final Set<StaffPrivilegeType> staffPrivilegeTypes,
      final SubstituteCareProvider substituteCareProvider,
      final PerryAccount perryAccount) {
    try {
      final boolean authorizationResult = droolsAuthorizationService
          .authorizeSubstituteCareProviderCreate(substituteCareProvider, perryAccount);
      logAuthorization(perryAccount, staffPrivilegeTypes, substituteCareProvider, authorizationResult);
      return authorizationResult;
    } catch (DroolsException e) {
      throw new AuthorizationException(e.getMessage(), e);
    }
  }

  private static void logAuthorization(
      final PerryAccount perryAccount,
      final Set<StaffPrivilegeType> staffPrivilegeTypes,
      final SubstituteCareProvider substituteCareProvider,
      final boolean authorizationResult) {
    LOGGER.info(
        "StaffPerson [{}] with staffPrivilegeTypes = {} is creating Substitute Care Provider [{}] with condition = [{}]. "
            + "Authorization result = [{}]",
        perryAccount.getStaffId(),
        staffPrivilegeTypes,
        substituteCareProvider.getIdentifier(),
        authorizationResult
    );
  }
}
