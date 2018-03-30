package gov.ca.cwds.cms.data.access.service.impl;

import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT_ID;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import gov.ca.cwds.data.legacy.cms.dao.TribalMembershipVerificationDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.security.annotations.Authorize;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Service for create/update/find ClientRelationship with business validation and data processing.
 *
 * @author CWDS TPT-3 Team
 */
public class ClientRelationshipCoreService
    extends DataAccessServiceBase<
        ClientRelationshipDao, ClientRelationship, ClientRelationshipAwareDTO> {

  private final BusinessValidationService businessValidationService;
  private final ClientDao clientDao;
  private final TribalMembershipVerificationDao tribalMembershipVerificationDao;


  /**
   * Constructor with injected services.
   *
   * @param clientRelationshipDao client relationship DAO
   * @param businessValidationService business validator
   * @param clientDao client DAO
   */
  @Inject
  public ClientRelationshipCoreService(
      final ClientRelationshipDao clientRelationshipDao,
      BusinessValidationService businessValidationService,
      ClientDao clientDao,
      TribalMembershipVerificationDao tribalMembershipVerificationDao) {
    super(clientRelationshipDao);
    this.businessValidationService = businessValidationService;
    this.clientDao = clientDao;
    this.tribalMembershipVerificationDao = tribalMembershipVerificationDao;
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
      validateAndAddIfNeededTribalMembershipVerification(bundle);
    }

    @Override
    public void dataProcessing(DataAccessBundle bundle, PerryAccount perryAccount)
        throws DroolsException {
      super.dataProcessing(bundle, perryAccount);
      businessValidationService.runBusinessValidation(
          bundle.getAwareDto(),
          PrincipalUtils.getPrincipal(),
          ClientRelationshipDroolsConfiguration.DATA_PROCESSING_INSTANCE);
    }

    private void enrichWithPrimaryAndSecondaryClients(DataAccessBundle bundle) {
      ClientRelationshipAwareDTO awareDTO = (ClientRelationshipAwareDTO) bundle.getAwareDto();
      ParametersValidator.checkEntityId(
          awareDTO.getEntity().getPrimaryClient(), awareDTO.getEntity().getClass().getName());
      Client primaryClient =
          clientDao.find(awareDTO.getEntity().getPrimaryClient().getPrimaryKey());
      ParametersValidator.checkEntityId(
          awareDTO.getEntity().getSecondaryClient(), awareDTO.getEntity().getClass().getName());
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

    /**
     * Rule-08840
     *
     * If cboRelationship = 'Mother/Daughter (Birth)', 'Mother/Son (Birth)',
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

      // get list tribal membership verifications
      ClientRelationshipAwareDTO awareDTO = (ClientRelationshipAwareDTO) bundle.getAwareDto();

      ParametersValidator.checkNotPersisted(awareDTO.getEntity().getPrimaryClient());
      Client client = clientDao.find(awareDTO.getEntity().getPrimaryClient().getIdentifier());

      List<TribalMembershipVerification> tribalMembershipVerifications =
          tribalMembershipVerificationDao.findParensByClientId(client.getIdentifier());

      // add extra rows for tribal verifications membership if needed
      tribalMembershipVerifications.forEach(e -> addRowIfNeedForTribalMembershipVerifications(e, awareDTO));
    }

    private void addRowIfNeedForTribalMembershipVerifications(TribalMembershipVerification parentRow, ClientRelationshipAwareDTO awareDTO) {
      if (StringUtils.isEmpty(parentRow.getFkFromTribalMembershipVerification())) {
        createRowForTribalMembershipVerification(parentRow);
      }
    }

    private void createRowForTribalMembershipVerification(TribalMembershipVerification parentRow) {
      TribalMembershipVerification child = new TribalMembershipVerification();
      LocalDate dateFromThirdId = extractDate(parentRow.getThirdId());
      if (dateFromThirdId.isBefore(LocalDate.of(2005, Month.NOVEMBER , 19))) {

      } else {
        child.setFkFromTribalMembershipVerification(parentRow.getThirdId());
        child.setFkSentTotribalOrganization(parentRow.getFkSentTotribalOrganization());
        child.setIndianEnrollmentStatus(parentRow.getIndianEnrollmentStatus());
        child.setIndianTribeType(parentRow.getIndianTribeType());
        child.setThirdId(IdGenerator.generateId());
        child.setClientId(parentRow.getClientId());
        child.setLastUpdateId(PrincipalUtils.getStaffPersonId());
        child.setLastUpdateTime(LocalDateTime.now());
      }
    }

    private LocalDate extractDate(String thirdId) {
      return null;//CmsKeyIdGenerator.getDateFromKey(thirdId).toLocalDate();
    }
  }
}
