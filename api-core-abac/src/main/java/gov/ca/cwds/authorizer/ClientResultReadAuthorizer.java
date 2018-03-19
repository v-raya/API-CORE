package gov.ca.cwds.authorizer;

import static gov.ca.cwds.authorizer.util.ClientConditionUtils.toClientCondition;

import com.google.inject.Inject;
import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.authorizer.drools.configuration.ClientResultAuthorizationDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.realm.PerrySubject;
import gov.ca.cwds.service.ClientCountyDeterminationService;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * @author CWDS TPT-2 Team
 */
public class ClientResultReadAuthorizer extends AbstractBaseAuthorizer<Client, String> {

  @Inject
  private ClientCountyDeterminationService countyDeterminationService;

  @Inject
  private ClientResultAuthorizationDroolsConfiguration droolsConfiguration;

  @Inject
  public ClientResultReadAuthorizer(
      DroolsAuthorizationService droolsAuthorizationService) {
    super(droolsAuthorizationService);
  }

  @Override
  protected boolean checkInstance(final Client client) {
    if (client == null) {
      return false;
    }
    final PerryAccount perryAccount = PerrySubject.getPerryAccount();
    final ClientCondition clientCondition = getClientCondition(client, perryAccount);
    List authorizationFacts = new ArrayList<>();
    authorizationFacts.add(clientCondition);
    return authorizeInstanceOperation(client, droolsConfiguration, authorizationFacts);
  }

  private ClientCondition getClientCondition(final Client client, final PerryAccount perryAccount) {
    final List<Short> clientCountyCodes = countyDeterminationService.getClientCountiesById(client.getIdentifier());
    final Short staffPersonCountyCode = getStaffPersonCountyCode(perryAccount.getCountyCwsCode());
    return toClientCondition(client, clientCountyCodes, staffPersonCountyCode);
  }

  private static Short getStaffPersonCountyCode(final String staffCountyCodeString) {
    return StringUtils.isNotBlank(staffCountyCodeString)
          ? Short.valueOf(staffCountyCodeString)
          : null;
  }

  void setCountyDeterminationService(ClientCountyDeterminationService countyDeterminationService) {
    this.countyDeterminationService = countyDeterminationService;
  }

  void setDroolsConfiguration(
      ClientResultAuthorizationDroolsConfiguration droolsConfiguration) {
    this.droolsConfiguration = droolsConfiguration;
  }
}
