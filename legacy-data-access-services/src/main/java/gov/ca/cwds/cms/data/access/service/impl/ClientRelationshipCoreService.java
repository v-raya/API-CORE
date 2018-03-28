package gov.ca.cwds.cms.data.access.service.impl;

import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT_ID;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dto.ClientRelationshipAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.DataAccessServiceBase;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessBundle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessServiceLifecycle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.cms.data.access.service.rules.ClientRelationshipDroolsConfiguration;
import gov.ca.cwds.cms.data.access.utils.ParametersValidator;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.security.annotations.Authorize;

import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CWDS TPT-3 Team
 * */
public class ClientRelationshipCoreService
    extends DataAccessServiceBase<
        ClientRelationshipDao, ClientRelationship, ClientRelationshipAwareDTO> {

  private final BusinessValidationService businessValidationService;
  private final ClientDao clientDao;

  @Inject
  public ClientRelationshipCoreService(
      final ClientRelationshipDao clientRelationshipDao,
      BusinessValidationService businessValidationService,
      ClientDao clientDao) {
    super(clientRelationshipDao);
    this.businessValidationService = businessValidationService;
    this.clientDao = clientDao;
  }

  @Override
  public ClientRelationship create(ClientRelationshipAwareDTO entityAwareDto)
      throws DataAccessServicesException {
    entityAwareDto.getEntity().setLastUpdateTime(LocalDateTime.now());
    entityAwareDto.getEntity().setLastUpdateId(PrincipalUtils.getStaffPersonId());
    return super.create(entityAwareDto);
  }

  @Override
  public ClientRelationship update(ClientRelationshipAwareDTO entityAwareDto)
      throws DataAccessServicesException, DroolsException {
    entityAwareDto.getEntity().setLastUpdateTime(LocalDateTime.now());
    entityAwareDto.getEntity().setLastUpdateId(PrincipalUtils.getStaffPersonId());
    return super.update(entityAwareDto);
  }

  @Override
  protected DataAccessServiceLifecycle<ClientRelationshipAwareDTO> getUpdateLifeCycle() {
    return new UpdateLificycle();
  }

  @Override
  protected DataAccessServiceLifecycle<ClientRelationshipAwareDTO> getCreateLifeCycle() {
    return new DefaultDataAccessLifeCycle<>();
  }

  @Override
  protected DataAccessServiceLifecycle<ClientRelationshipAwareDTO> getDeleteLifeCycle() {
    return new DefaultDataAccessLifeCycle<>();
  }

  public List<ClientRelationship> findRelationshipsByLeftSide(
      @Authorize(CLIENT_READ_CLIENT_ID) final String clientId) {
    return crudDao.findRelationshipsBySecondaryClientId(clientId, LocalDate.now());
  }

  public List<ClientRelationship> findRelationshipsByRightSide(
      @Authorize(CLIENT_READ_CLIENT_ID) final String clientId) {
    return crudDao.findRelationshipsByPrimaryClientId(clientId, LocalDate.now());
  }

  private class UpdateLificycle extends DefaultDataAccessLifeCycle {

    @Override
    public void beforeDataProcessing(DataAccessBundle bundle) {
      super.beforeDataProcessing(bundle);
      enrichWithPrimaryAndSecondaryClients(bundle);
    }

    private void enrichWithPrimaryAndSecondaryClients(DataAccessBundle bundle) {
      ClientRelationshipAwareDTO awareDTO = (ClientRelationshipAwareDTO) bundle.getAwareDto();
      ParametersValidator.checkNotPersisted(awareDTO.getEntity().getPrimaryClient());
      Client primaryClient =
          clientDao.find(awareDTO.getEntity().getPrimaryClient().getPrimaryKey());
      ParametersValidator.checkNotPersisted(awareDTO.getEntity().getSecondaryClient());
      Client secondaryClient =
          clientDao.find(awareDTO.getEntity().getSecondaryClient().getPrimaryKey());
      awareDTO.getEntity().setPrimaryClient(primaryClient);
      awareDTO.getEntity().setSecondaryClient(secondaryClient);
    }

    @Override
    public void beforeBusinessValidation(DataAccessBundle bundle) {

      ClientRelationshipAwareDTO awareDTO = (ClientRelationshipAwareDTO) bundle.getAwareDto();
      Client client = awareDTO.getEntity().getPrimaryClient();
      String clientId = client.getIdentifier();

      List<ClientRelationship> otherRelationshipsForThisClient =
          new ArrayList<>(findRelationshipsByRightSide(clientId));
      otherRelationshipsForThisClient.addAll(findRelationshipsByRightSide(clientId));

      otherRelationshipsForThisClient.removeIf(
          e -> e.getIdentifier().equals(awareDTO.getEntity().getIdentifier()));

      awareDTO.getClientRelationshipList().addAll(otherRelationshipsForThisClient);
    }

    @Override
    public void businessValidation(DataAccessBundle bundle, PerryAccount perryAccount)
        throws DroolsException {
      businessValidationService.runBusinessValidation(
          bundle.getAwareDto(),
          PrincipalUtils.getPrincipal(),
          ClientRelationshipDroolsConfiguration.INSTANCE);
    }
  }
}
