package gov.ca.cwds.authorizer;

import static gov.ca.cwds.authorizer.util.ClientConditionUtils.toClientCondition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.google.inject.Inject;

import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.authorizer.drools.configuration.DroolsAuthorizer;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity;
import gov.ca.cwds.service.ClientCountyDeterminationService;
import gov.ca.cwds.service.ClientSensitivityDeterminationService;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Base class for Client Result and Client Abstract Authorizer.
 *
 * @author CWDS TPT-3 Team
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
    final Sensitivity sensitivity =
      sensitivityDeterminationService.getClientSensitivityById(clientId);
    final ClientCondition clientCondition = getClientCondition(clientId, sensitivity);
    return checkId(clientId, sensitivity, clientCondition);
  }

  @Override
  protected Collection<String> filterIds(Collection<String> ids) {
    Map<String, Sensitivity> sensitivityMap = sensitivityDeterminationService
      .getClientSensitivityMapByIds(ids);
    Map<String, List<Short>> clientCountiesMap = countyDeterminationService
      .getClientCountiesMapByIds(ids);

    Stream<String> filteredStream = ids.stream().filter(Objects::nonNull)
      .filter(clientId -> {
        Sensitivity sensitivity = sensitivityMap.get(clientId);
        ClientCondition clientCondition = toClientCondition(sensitivity,
          clientCountiesMap.get(clientId));
        return checkId(clientId, sensitivity, clientCondition);
      });
    return ids instanceof Set ? filteredStream.collect(Collectors.toSet())
      : filteredStream.collect(Collectors.toList());
  }

  @Override
  protected Collection<Client> filterInstances(Collection<Client> instances) {
    Collection<String> clientIds = instances.stream().map(Client::getIdentifier)
      .collect(Collectors.toSet());
    Map<String, List<Short>> clientCountiesMap = countyDeterminationService
      .getClientCountiesMapByIds(clientIds);
    Stream<Client> filteredStream = instances.stream().filter(Objects::nonNull)
      .filter(client -> {
        ClientCondition clientCondition = toClientCondition(client.getSensitivity(),
          clientCountiesMap.get(client.getIdentifier()));
        return authorizeInstanceOperation(client, prepareFacts(client, clientCondition));
      });
    return instances instanceof Set ? filteredStream.collect(Collectors.toSet())
      : filteredStream.collect(Collectors.toList());
  }

  private boolean checkId(final String clientId, final Sensitivity sensitivity,
    final ClientCondition clientCondition) {
    if (sensitivity == null) {
      return true;
    }

    final List<Object> authorizationFacts = new ArrayList<>();
    authorizationFacts.add(clientCondition);

    final Client client = new Client();
    client.setIdentifier(clientId);
    client.setSensitivity(sensitivity);
    return authorizeInstanceOperation(client, authorizationFacts);
  }

  private ClientCondition getClientCondition(final String clientId, Sensitivity sensitivity) {
    return toClientCondition(sensitivity,
      countyDeterminationService.getClientCountiesById(clientId));
  }

  @Override
  protected List<Object> prepareFacts(Client client) {
    final ClientCondition clientCondition = getClientCondition(client.getIdentifier(),
      client.getSensitivity());
    return prepareFacts(client, clientCondition);
  }

  private List<Object> prepareFacts(Client client, ClientCondition clientCondition) {
    final List<Object> authorizationFacts = new ArrayList<>();
    Optional.ofNullable(client).ifPresent(client1 -> authorizationFacts.add(clientCondition));
    return authorizationFacts;
  }

  void setSensitivityDeterminationService(
    ClientSensitivityDeterminationService sensitivityDeterminationService) {
    this.sensitivityDeterminationService = sensitivityDeterminationService;
  }

  void setCountyDeterminationService(ClientCountyDeterminationService countyDeterminationService) {
    this.countyDeterminationService = countyDeterminationService;
  }

}
