package gov.ca.cwds.authorizer;

import static gov.ca.cwds.authorizer.util.StaffPrivilegeUtil.toStaffPersonPrivilegeTypes;

import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.authorizer.drools.configuration.DroolsAuthorizer;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.security.authorizer.BaseAuthorizer;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.realm.PerrySubject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CWDS TPT-2 Team
 */
public abstract class AbstractBaseAuthorizer<T, ID> extends BaseAuthorizer<T, ID> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBaseAuthorizer.class);

  private DroolsAuthorizationService droolsAuthorizationService;

  private DroolsAuthorizer droolsConfiguration;


  public AbstractBaseAuthorizer(DroolsAuthorizationService droolsAuthorizationService,
      DroolsAuthorizer droolsConfiguration) {
    this.droolsAuthorizationService = droolsAuthorizationService;
    this.droolsConfiguration = droolsConfiguration;
  }

  private void logAuthorization(
      final PerryAccount perryAccount,
      final Set<StaffPrivilegeType> staffPrivilegeTypes,
      final T instance,
      final boolean authorizationResult) {
    String instanceName = Optional.ofNullable(instance).map(t -> t.getClass().getSimpleName())
        .orElse(null);
    LOGGER.info(
        "StaffPerson [{}] with staffPrivilegeTypes = {} is performing action on object [{}]. "
            + "Authorization result = [{}]. {}",
        perryAccount.getStaffId(),
        staffPrivilegeTypes,
        instanceName,
        authorizationResult,
        perryAccount
    );
  }

  protected boolean authorizeInstanceOperation(
      final T instance,
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
  protected boolean checkInstance(T instance) {
    if (instance == null) {
      return true;
    }
    return authorizeInstanceOperation(instance, prepareFacts(instance));
  }

  protected abstract List<Object> prepareFacts(T instance);
}
