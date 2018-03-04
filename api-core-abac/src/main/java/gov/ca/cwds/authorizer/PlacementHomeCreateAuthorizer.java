package gov.ca.cwds.authorizer;

import static gov.ca.cwds.authorizer.util.StaffPrivilegeUtil.toStaffPersonPrivilegeTypes;

import com.google.inject.Inject;
import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
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
public class PlacementHomeCreateAuthorizer extends BaseAuthorizer<PlacementHome, Long> {

  private static final Logger LOGGER = LoggerFactory.getLogger(PlacementHomeCreateAuthorizer.class);

  private final DroolsAuthorizationService droolsAuthorizationService;

  @Inject
  public PlacementHomeCreateAuthorizer(DroolsAuthorizationService droolsAuthorizationService) {
    this.droolsAuthorizationService = droolsAuthorizationService;
  }

  @Override
  protected boolean checkInstance(final PlacementHome placementHome) {
    final PerryAccount perryAccount = PerrySubject.getPerryAccount();
    final Set<StaffPrivilegeType> staffPrivilegeTypes = toStaffPersonPrivilegeTypes(perryAccount);
    if (staffPrivilegeTypes.isEmpty()) {
      return false;
    }

    return authorizePlacementHomeCreateOperation(staffPrivilegeTypes, placementHome, perryAccount);
  }

  private boolean authorizePlacementHomeCreateOperation(
      final Set<StaffPrivilegeType> staffPrivilegeTypes,
      final PlacementHome placementHome,
      final PerryAccount perryAccount) {
    try {
      final boolean authorizationResult = droolsAuthorizationService
          .authorizePlacementHomeCreate(placementHome, staffPrivilegeTypes);
      logAuthorization(perryAccount, staffPrivilegeTypes, placementHome, authorizationResult);
      return authorizationResult;
    } catch (DroolsException e) {
      throw new AuthorizationException(e.getMessage(), e);
    }
  }

  private static void logAuthorization(
      final PerryAccount perryAccount,
      final Set<StaffPrivilegeType> staffPrivilegeTypes,
      final PlacementHome placementHome,
      final boolean authorizationResult) {
    LOGGER.info(
        "StaffPerson [{}] with staffPrivilegeTypes = {} is creating Placement Home [{}] with condition = [{}]. "
            + "Authorization result = [{}]",
        perryAccount.getStaffId(),
        staffPrivilegeTypes,
        placementHome.getIdentifier(),
        authorizationResult
    );
  }
}
