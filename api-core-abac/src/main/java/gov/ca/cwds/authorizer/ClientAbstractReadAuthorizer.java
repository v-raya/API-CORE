package gov.ca.cwds.authorizer;

import static gov.ca.cwds.authorizer.util.ClientConditionUtils.toClientCondition;
import static gov.ca.cwds.authorizer.util.StaffPrivilegeUtil.toStaffPersonPrivilegeTypes;

import com.google.inject.Inject;
import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.security.authorizer.BaseAuthorizer;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.realm.PerrySubject;
import gov.ca.cwds.service.ClientCountyDeterminationService;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CWDS TPT-3 Team
 */
public class ClientAbstractReadAuthorizer extends BaseAuthorizer<Client, Long> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClientAbstractReadAuthorizer.class);

  private final DroolsAuthorizationService droolsAuthorizationService;
  private final ClientCountyDeterminationService countyDeterminationService;

  @Inject
  public ClientAbstractReadAuthorizer(DroolsAuthorizationService droolsAuthorizationService,
      ClientCountyDeterminationService countyDeterminationService) {
    this.droolsAuthorizationService = droolsAuthorizationService;
    this.countyDeterminationService = countyDeterminationService;
  }

  @Override
  protected boolean checkInstance(final Client client) {
    final PerryAccount perryAccount = PerrySubject.getPerryAccount();
    final Set<StaffPrivilegeType> staffPrivilegeTypes = toStaffPersonPrivilegeTypes(perryAccount);
    if (staffPrivilegeTypes.isEmpty()) {
      return false;
    }

    final ClientCondition clientCondition = getClientCondition(client, perryAccount);
    return authorizeClientReadOperation(clientCondition, staffPrivilegeTypes, client, perryAccount);
  }

  private ClientCondition getClientCondition(final Client client, final PerryAccount perryAccount) {
    final Short clientCountyCode = countyDeterminationService.getClientCountyById(client.getIdentifier());
    final Short staffGovernmentEntityType = getStaffGovernmentEntityType(perryAccount.getGovernmentEntityType());
    return toClientCondition(client, clientCountyCode, staffGovernmentEntityType);
  }

  private static Short getStaffGovernmentEntityType(final String staffCountyCodeString) {
    return StringUtils.isNotBlank(staffCountyCodeString)
          ? Short.parseShort(staffCountyCodeString)
          : null;
  }

  private boolean authorizeClientReadOperation(
      final ClientCondition clientCondition,
      final Set<StaffPrivilegeType> staffPrivilegeTypes,
      final Client client,
      final PerryAccount perryAccount) {
    try {
      final boolean authorizationResult = droolsAuthorizationService
          .authorizeClientRead(clientCondition, staffPrivilegeTypes);
      logAuthorization(perryAccount, staffPrivilegeTypes, client, clientCondition, authorizationResult);
      return authorizationResult;
    } catch (DroolsException e) {
      throw new AuthorizationException(e.getMessage(), e);
    }
  }

  private static void logAuthorization(
      final PerryAccount perryAccount,
      final Set<StaffPrivilegeType> staffPrivilegeTypes,
      final Client client,
      final ClientCondition clientCondition,
      final boolean authorizationResult) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "StaffPerson [{}] with staffPrivilegeTypes = {} is requesting Client [{}] with condition = [{}]. "
              + "Authorization result = [{}]",
          perryAccount.getStaffId(),
          staffPrivilegeTypes,
          client.getIdentifier(),
          clientCondition,
          authorizationResult
      );
    }
  }
}
