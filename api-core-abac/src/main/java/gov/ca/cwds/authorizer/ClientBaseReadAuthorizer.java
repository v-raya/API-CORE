package gov.ca.cwds.authorizer;

import static gov.ca.cwds.authorizer.util.ClientConditionUtils.toClientCondition;

import com.google.inject.Inject;
import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.realm.PerrySubject;
import gov.ca.cwds.service.ClientCountyDeterminationService;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * @author CWDS TPT-2 Team
 *
 * Base class for Cleint Result and Client Abstract Authorizer.
 *
 */
public class ClientBaseReadAuthorizer extends AbstractBaseAuthorizer<Client, String> {

  @Inject
  private ClientDao clientDao;

  @Inject
  private ClientCountyDeterminationService countyDeterminationService;


  @Inject
  public ClientBaseReadAuthorizer(
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
    return authorizeInstanceOperation(client, getDroolsConfiguration(), authorizationFacts);
  }

  private ClientCondition getClientCondition(final Client client, final PerryAccount perryAccount) {
    final List<Short> clientCountyCodes = countyDeterminationService
        .getClientCountiesById(client.getIdentifier());
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

}
