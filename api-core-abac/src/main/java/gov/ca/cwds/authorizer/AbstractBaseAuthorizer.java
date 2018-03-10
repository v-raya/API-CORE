package gov.ca.cwds.authorizer;

import static gov.ca.cwds.authorizer.util.StaffPrivilegeUtil.toStaffPersonPrivilegeTypes;

import com.google.inject.Inject;
import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.authorizer.drools.configuration.DroolsAuthorizer;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.security.authorizer.BaseAuthorizer;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.realm.PerrySubject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CWDS TPT-2 Team
 */
public abstract class AbstractBaseAuthorizer<TYPE, ID> extends BaseAuthorizer<TYPE, ID> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBaseAuthorizer.class);

  private DroolsAuthorizationService droolsAuthorizationService;

  @Inject
  public AbstractBaseAuthorizer(DroolsAuthorizationService droolsAuthorizationService) {
    this.droolsAuthorizationService = droolsAuthorizationService;
  }

  private void logAuthorization(
      final PerryAccount perryAccount,
      final Set<StaffPrivilegeType> staffPrivilegeTypes,
      final TYPE instance,
      final boolean authorizationResult) {
    LOGGER.info(
        "StaffPerson [{}] with staffPrivilegeTypes = {} is creating object [{}]. "
            + "Authorization result = [{}]",
        perryAccount.getStaffId(),
        staffPrivilegeTypes,
        instance,
        authorizationResult
    );
  }

  protected boolean authorizeInstanceOperation(
      final TYPE instance,
      final DroolsAuthorizer droolsConfiguration,
      List<Object> authorizationFacts) {
    try {
      final PerryAccount perryAccount = PerrySubject.getPerryAccount();
      final Set<StaffPrivilegeType> staffPrivilegeTypes = toStaffPersonPrivilegeTypes(perryAccount);
      if (staffPrivilegeTypes.isEmpty()) {
        return false;
      }
      if (authorizationFacts == null) {
        authorizationFacts = new ArrayList<>();
      }
      authorizationFacts.add(instance);
      authorizationFacts.add(perryAccount);
      final boolean authorizationResult = droolsAuthorizationService
          .authorizeObjectOperation(staffPrivilegeTypes, droolsConfiguration, authorizationFacts);
      logAuthorization(perryAccount, staffPrivilegeTypes, instance, authorizationResult);
      return authorizationResult;
    } catch (DroolsException e) {
      throw new AuthorizationException(e.getMessage(), e);
    }
  }

  @Override
  abstract protected boolean checkInstance(TYPE instance);

  void setDroolsAuthorizationService(DroolsAuthorizationService droolsAuthorizationService) {
    this.droolsAuthorizationService = droolsAuthorizationService;
  }

}
