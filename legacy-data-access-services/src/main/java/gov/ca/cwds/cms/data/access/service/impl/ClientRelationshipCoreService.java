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
import gov.ca.cwds.data.legacy.cms.dao.TribalMembershipVerificationDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.security.annotations.Authorize;

import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT_ID;

public class ClientRelationshipCoreService
    extends DataAccessServiceBase<
        ClientRelationshipDao, ClientRelationship, ClientRelationshipAwareDTO> {

  private final BusinessValidationService businessValidationService;
  private final TribalMembershipVerificationDao tribalMembershipVerificationDao;

  @Inject
  public ClientRelationshipCoreService(
      final ClientRelationshipDao clientRelationshipDao,
      BusinessValidationService businessValidationService,
      TribalMembershipVerificationDao tribalMembershipVerificationDao) {
    super(clientRelationshipDao);
    this.businessValidationService = businessValidationService;
    this.tribalMembershipVerificationDao = tribalMembershipVerificationDao;
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
      validateAndAddIfNeededTribalMembershipVerification(bundle);
    }

    @Override
    public void dataProcessing(DataAccessBundle bundle, PerryAccount perryAccount)
        throws DroolsException {
      super.dataProcessing(bundle, perryAccount);
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

    /**
     * Rule-08840 If cboRelationship = 'Mother/Daughter (Birth)', 'Mother/Son (Birth)',
     * 'Mother/Daughter (Alleged)', OR 'Mother/Son (Alleged)', 'Mother/Daughter (Presumed)',
     * 'Mother/Son (Presumed)', 'Father/Daughter (Birth)', 'Father/Son (Birth)', 'Father/Daughter
     * (Alleged)', 'Father/Son (Alleged)', 'Father/Daughter (Presumed)' OR 'Father/Son (Presumed)',
     * 'Daughter/Mother (Birth)', 'Son/Mother (Birth)', 'Daughter/Mother (Alleged)', 'Son/Mother
     * (Alleged)', 'Daughter/Mother (Presumed)', 'Son/Mother (Presumed)', 'Daughter/Father (Birth)',
     * 'Son/Father (Birth)', 'Daughter/Father (Alleged)', 'Son/Father (Alleged)', 'Daughter/Father
     * (Presumed)', or 'Son/Father (Presumed)' and (parent) CLIENT > TRIBAL_MEMBERSHIP_VERIFICATION
     * (where .FKTR_MBVRT = null) exists, create (child) CLIENT > TRIBAL_MEMBERSHIP_VERIFICATION.
     * Set FKTR_MBVRT = (parent) CLIENT > TRIBAL_MEMBERSHIP_VERIFICATION.Third_Id. Set Fkclient_T =
     * (child) CLIENT.Id Set .Indian_Tribe_Type, .Fktrb_Orgt = (parent)
     * TRIBAL_MEMBERSHIP_VERIFICATION.Indian_Tribe_Type, .Fktrb_Orgt.
     */
    private void validateAndAddIfNeededTribalMembershipVerification(DataAccessBundle bundle) {
      ClientRelationshipAwareDTO awareDTO = (ClientRelationshipAwareDTO) bundle.getAwareDto();

      Client client = awareDTO.getEntity().getPrimaryClient();
      String clientId = client.getIdentifier();
      
      List<TribalMembershipVerification> tribalMembershipVerifications =
          tribalMembershipVerificationDao.queryImmutableList(clientId);
    }
  }
}
