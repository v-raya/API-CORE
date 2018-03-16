package gov.ca.cwds.cms.data.access.service.impl;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dto.ClientRelationshipAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.DataAccessServiceBase;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessBundle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessServiceLifecycle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.cms.data.access.service.rules.ClientRelationshipDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.security.annotations.Authorize;

import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT_ID;

public class ClientRelationshipCoreService
    extends DataAccessServiceBase<
        ClientRelationshipDao, ClientRelationship, ClientRelationshipAwareDTO> {

  private final BusinessValidationService businessValidationService;

  @Inject
  public ClientRelationshipCoreService(
      final ClientRelationshipDao clientRelationshipDao,
      BusinessValidationService businessValidationService) {
    super(clientRelationshipDao);
    this.businessValidationService = businessValidationService;
  }

  @Override
  public ClientRelationship create(ClientRelationshipAwareDTO entityAwareDTO)
      throws DataAccessServicesException {
    entityAwareDTO.getEntity().setLastUpdateTime(LocalDateTime.now());
    entityAwareDTO.getEntity().setLastUpdateId(PrincipalUtils.getStaffPersonId());
    return super.create(entityAwareDTO);
  }

  @Override
  public ClientRelationship update(ClientRelationshipAwareDTO entityAwareDTO)
      throws DataAccessServicesException, DroolsException {
    entityAwareDTO.getEntity().setLastUpdateTime(LocalDateTime.now());
    entityAwareDTO.getEntity().setLastUpdateId(PrincipalUtils.getStaffPersonId());
    return super.update(entityAwareDTO);
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
    return crudDao.findRelationshipsByLeftSide(clientId, LocalDate.now());
  }

  public List<ClientRelationship> findRelationshipsByRightSide(
      @Authorize(CLIENT_READ_CLIENT_ID) final String clientId) {
    return crudDao.findRelationshipsByRightSide(clientId, LocalDate.now());
  }

  private class UpdateLificycle extends DefaultDataAccessLifeCycle {

    @Override
    public void beforeBusinessValidation(DataAccessBundle bundle) {

      ClientRelationshipAwareDTO awareDTO = (ClientRelationshipAwareDTO) bundle.getAwareDto();
      Client client = awareDTO.getEntity().getRightSide();
      String clientId = client.getIdentifier();

      List<ClientRelationship> otherRelationshipsForThisClient =
          findRelationshipsByRightSide(clientId);

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
