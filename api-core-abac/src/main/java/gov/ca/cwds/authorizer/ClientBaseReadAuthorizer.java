package gov.ca.cwds.authorizer;

import static gov.ca.cwds.authorizer.util.ClientConditionUtils.toClientCondition;

import com.google.inject.Inject;
import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.authorizer.drools.configuration.DroolsAuthorizer;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity;
import gov.ca.cwds.service.ClientCountyDeterminationService;
import gov.ca.cwds.service.ClientSensitivityDeterminationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Base class for Client Result and Client Abstract Authorizer.
 */
public class ClientBaseReadAuthorizer extends AbstractBaseAuthorizer<Client, String> {

  @Inject
  private ClientSensitivityDeterminationService sensitivityDeterminationService;

  @Inject
  private ClientCountyDeterminationService countyDeterminationService;

  @Inject
  public ClientBaseReadAuthorizer(DroolsAuthorizationService droolsAuthorizationService,
      DroolsAuthorizer droolsConfiguration) {
    super(droolsAuthorizationService, droolsConfiguration);
  }

  @Override
  protected boolean checkId(final String clientId) {
    Sensitivity sensitivity = sensitivityDeterminationService.getClientSensitivityById(clientId);
    if (sensitivity == null) {
      return true;
    }
    final ClientCondition clientCondition = getClientCondition(clientId, sensitivity);
    List<Object> authorizationFacts = new ArrayList<>();
    authorizationFacts.add(clientCondition);

    Client client = new Client();
    client.setIdentifier(clientId);
    client.setSensitivity(sensitivity);
    return authorizeInstanceOperation(client, authorizationFacts);
  }

  @Override
  protected List<Object> prepareFacts(Client client) {
    List<Object> authorizationFacts = new ArrayList<>();
    Optional.ofNullable(client).ifPresent(client1 -> {
      final ClientCondition clientCondition = getClientCondition(client.getIdentifier(),
          client.getSensitivity());
      authorizationFacts.add(clientCondition);
    });
    return authorizationFacts;
  }

  private ClientCondition getClientCondition(final String identifier, Sensitivity sensitivity) {
    final List<Short> clientCountyCodes =
        countyDeterminationService.getClientCountiesById(identifier);

    return toClientCondition(sensitivity, clientCountyCodes);
  }

  void setSensitivityDeterminationService(
      ClientSensitivityDeterminationService sensitivityDeterminationService) {
    this.sensitivityDeterminationService = sensitivityDeterminationService;
  }

  void setCountyDeterminationService(ClientCountyDeterminationService countyDeterminationService) {
    this.countyDeterminationService = countyDeterminationService;
  }
}
