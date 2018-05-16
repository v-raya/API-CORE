package gov.ca.cwds.cms.data.access.service.impl.clientrelationship;

import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT;
import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT_ID;

import com.google.inject.Inject;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.security.annotations.Authorize;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import org.apache.commons.collections4.CollectionUtils;

/**
 * @author CWDS TPT-3 Team
 */
class SearchClientRelationshipService {

  private final ClientRelationshipDao clientRelationshipDao;

  @Inject
  SearchClientRelationshipService(
    ClientRelationshipDao clientRelationshipDao) {
    this.clientRelationshipDao = clientRelationshipDao;
  }

  List<ClientRelationship> findRelationshipsBySecondaryClientId(
    @Authorize(CLIENT_READ_CLIENT_ID) final String clientId) {
    return deleteNotPermittedClientData(
      clientRelationshipDao.findRelationshipsBySecondaryClientId(clientId, LocalDate.now()));
  }

  List<ClientRelationship> findRelationshipsByPrimaryClientId(
    @Authorize(CLIENT_READ_CLIENT_ID) final String clientId) {
    return deleteNotPermittedClientData(
      clientRelationshipDao.findRelationshipsByPrimaryClientId(clientId, LocalDate.now()));
  }

  ClientRelationship getRelationshipById(String relationshipId) {
    return clientRelationshipDao.find(relationshipId);
  }

  private List<ClientRelationship> deleteNotPermittedClientData(
    List<ClientRelationship> relationships) {
    if (CollectionUtils.isEmpty(relationships)) {
      return relationships;
    }

    List<Client> permittedClients = checkPermissionForRelatedClient(relationships);
    relationships.forEach(r -> filterSecondaryClients.accept(r, permittedClients));
    return relationships;
  }

  private final BiConsumer<ClientRelationship, List<Client>> filterSecondaryClients =
    (relationship, permittedClients) -> {
      String secondaryClientId = relationship.getSecondaryClient().getIdentifier();
      relationship.setSecondaryClient(
        permittedClients
          .stream()
          .filter(e -> isClientId.test(e, secondaryClientId))
          .findFirst()
          .orElse(new Client()));
    };

  private static final BiPredicate<Client, String> isClientId =
    (client, identifier) -> client.getIdentifier().equals(identifier);

  @Authorize(CLIENT_READ_CLIENT)
  private List<Client> checkPermissionForRelatedClient(List<ClientRelationship> relationships) {
    if (CollectionUtils.isEmpty(relationships)) {
      return new ArrayList<>();
    }

    final List<Client> clients = new ArrayList<>();
    relationships.forEach(
      clientRelationship -> clients.add(clientRelationship.getSecondaryClient()));
    return clients;
  }

}
