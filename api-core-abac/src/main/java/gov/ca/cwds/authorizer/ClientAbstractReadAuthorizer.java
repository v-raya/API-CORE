package gov.ca.cwds.authorizer;

import static gov.ca.cwds.authorizer.util.ClientConditionUtils.toClientCondition;

import com.google.inject.Inject;
import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.authorizer.drools.configuration.ClientAuthorizationDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.realm.PerrySubject;
import gov.ca.cwds.service.ClientCountyDeterminationService;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * @author CWDS TPT-3 Team
 */
public class ClientAbstractReadAuthorizer extends AbstractBaseAuthorizer<Client, String> {

  @Inject
  private ClientDao clientDao;

  @Inject
  private ClientCountyDeterminationService countyDeterminationService;

  @Inject
  private ClientAuthorizationDroolsConfiguration droolsConfiguration;

  @Inject
  public ClientAbstractReadAuthorizer(
      DroolsAuthorizationService droolsAuthorizationService) {
    super(droolsAuthorizationService);
  }

  @Override
  protected boolean checkId(final String clientId) {
    final Client client = clientDao.find(clientId);
    return client == null || checkInstance(client);
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

  void setClientDao(ClientDao clientDao) {
    this.clientDao = clientDao;
  }

  void setCountyDeterminationService(ClientCountyDeterminationService countyDeterminationService) {
    this.countyDeterminationService = countyDeterminationService;
  }

  void setDroolsConfiguration(
      ClientAuthorizationDroolsConfiguration droolsConfiguration) {
    this.droolsConfiguration = droolsConfiguration;
  }
}
