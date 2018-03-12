package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDao;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.security.annotations.Authorize;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT;
import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT_ID;

public class ClientRelationshipService {

  private final ClientRelationshipDao clientRelationshipDao;

  @Inject
  public ClientRelationshipService(final ClientRelationshipDao clientRelationshipDao) {
    this.clientRelationshipDao = clientRelationshipDao;
  }

  public List<ClientRelationship> findRelationshipsByLeftSide(
      @Authorize(CLIENT_READ_CLIENT_ID) final String clientId) {
    return clientRelationshipDao.findRelationshipsByLeftSide(clientId, LocalDate.now());
  }

  public List<ClientRelationship> findRelationshipsByRightSide(
      @Authorize(CLIENT_READ_CLIENT_ID) final String clientId) {
    return clientRelationshipDao.findRelationshipsByRightSide(clientId, LocalDate.now());
  }

  public ClientRelationship create(final ClientRelationship clientRelationship) {
    return clientRelationshipDao.create(clientRelationship);
  }
}
