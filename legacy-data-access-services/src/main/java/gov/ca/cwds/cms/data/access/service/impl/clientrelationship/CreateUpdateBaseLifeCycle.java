package gov.ca.cwds.cms.data.access.service.impl.clientrelationship;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dto.ClientRelationshipAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.impl.IdGenerator;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessBundle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.cms.data.access.service.rules.ClientRelationshipDroolsConfiguration;
import gov.ca.cwds.cms.data.access.utils.ParametersValidator;
import gov.ca.cwds.cms.data.access.utils.RelationshipUtil;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDao;
import gov.ca.cwds.data.legacy.cms.dao.PaternityDetailDao;
import gov.ca.cwds.data.legacy.cms.dao.TribalMembershipVerificationDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/** @author CWDS TPT-3 Team */
public abstract class CreateUpdateBaseLifeCycle
    extends DefaultDataAccessLifeCycle<ClientRelationshipAwareDTO> {

  protected final BusinessValidationService<ClientRelationship, ClientRelationshipAwareDTO>
      businessValidationService;
  protected final ClientDao clientDao;
  protected final TribalMembershipVerificationDao tribalMembershipVerificationDao;
  protected final PaternityDetailDao paternityDetailDao;
  protected final SearchClientRelationshipService searchClientRelationshipService;

  @Inject
  CreateUpdateBaseLifeCycle(
      final ClientRelationshipDao clientRelationshipDao,
      BusinessValidationService<ClientRelationship, ClientRelationshipAwareDTO>
          businessValidationService,
      ClientDao clientDao,
      TribalMembershipVerificationDao tribalMembershipVerificationDao,
      PaternityDetailDao paternityDetailDao,
      SearchClientRelationshipService searchClientRelationshipService) {
    this.businessValidationService = businessValidationService;
    this.clientDao = clientDao;
    this.tribalMembershipVerificationDao = tribalMembershipVerificationDao;
    this.paternityDetailDao = paternityDetailDao;
    this.searchClientRelationshipService = searchClientRelationshipService;
  }

  @Override
  public void beforeDataProcessing(DataAccessBundle bundle) throws DataAccessServicesException {
    super.beforeDataProcessing(bundle);
    enrichWithPrimaryAndSecondaryClients(bundle);
    enrichWithTribalsMembershipVerifications(bundle);
  }

  @Override
  public void dataProcessing(DataAccessBundle bundle, PerryAccount perryAccount) {
    super.dataProcessing(bundle, perryAccount);
    businessValidationService.runBusinessValidation(
        (ClientRelationshipAwareDTO) bundle.getAwareDto(),
        PrincipalUtils.getPrincipal(),
        ClientRelationshipDroolsConfiguration.DATA_PROCESSING_INSTANCE);
  }

  @Override
  public void afterDataProcessing(DataAccessBundle bundle) {
    super.afterDataProcessing(bundle);
    deleteTribalMembershipVerifications(
        ((ClientRelationshipAwareDTO) bundle.getAwareDto())
            .getTribalMembershipVerificationsForDelete());
  }

  @Override
  public void beforeBusinessValidation(DataAccessBundle bundle) {
    validateAndAddIfNeededTribalMembershipVerification(bundle);

    ClientRelationshipAwareDTO awareDTO = (ClientRelationshipAwareDTO) bundle.getAwareDto();

    Client primaryClient = awareDTO.getEntity().getPrimaryClient();
    String primaryClientIdentifier = primaryClient.getIdentifier();

    Client secondaryClient = awareDTO.getEntity().getSecondaryClient();
    String secondaryClientIdentifier = secondaryClient.getIdentifier();

    List<ClientRelationship> otherRelationshipsForThisClient = new ArrayList<>();
    otherRelationshipsForThisClient.addAll(
        searchClientRelationshipService.findRelationshipsByPrimaryClientId(
            primaryClientIdentifier));
    otherRelationshipsForThisClient.addAll(
        searchClientRelationshipService.findRelationshipsBySecondaryClientId(
            primaryClientIdentifier));

    otherRelationshipsForThisClient.removeIf(
        e -> e.getIdentifier().equals(awareDTO.getEntity().getIdentifier()));

    awareDTO
        .getPrimaryClientPaternityDetails()
        .addAll(paternityDetailDao.findByChildClientId(primaryClientIdentifier));

    awareDTO
        .getSecondaryClientPaternityDetails()
        .addAll(paternityDetailDao.findByChildClientId(secondaryClientIdentifier));

    awareDTO.getClientRelationshipList().addAll(otherRelationshipsForThisClient);
  }

  @Override
  public void businessValidation(DataAccessBundle bundle, PerryAccount perryAccount) {
    businessValidationService.runBusinessValidation(
        (ClientRelationshipAwareDTO) bundle.getAwareDto(),
        PrincipalUtils.getPrincipal(),
        ClientRelationshipDroolsConfiguration.INSTANCE);
  }

  private void enrichWithTribalsMembershipVerifications(DataAccessBundle bundle) {
    ClientRelationshipAwareDTO awareDTO = (ClientRelationshipAwareDTO) bundle.getAwareDto();
    List<TribalMembershipVerification> tribalsThatHasSubTribals =
        tribalMembershipVerificationDao.findTribalsThatHaveSubTribalsByClientId(
            awareDTO.getEntity().getPrimaryClient().getIdentifier(),
            awareDTO.getEntity().getSecondaryClient().getIdentifier());

    if (CollectionUtils.isNotEmpty(tribalsThatHasSubTribals)) {
      awareDTO.getTribalsThatHaveSubTribals().addAll(tribalsThatHasSubTribals);
    }
  }

  private void enrichWithPrimaryAndSecondaryClients(DataAccessBundle bundle) {
    ClientRelationshipAwareDTO awareDTO = (ClientRelationshipAwareDTO) bundle.getAwareDto();
    ParametersValidator.checkEntityId(
        awareDTO.getEntity().getPrimaryClient(), awareDTO.getEntity().getClass().getName());
    Client primaryClient = clientDao.find(awareDTO.getEntity().getPrimaryClient().getPrimaryKey());
    ParametersValidator.checkEntityId(
        awareDTO.getEntity().getSecondaryClient(), awareDTO.getEntity().getClass().getName());
    Client secondaryClient =
        clientDao.find(awareDTO.getEntity().getSecondaryClient().getPrimaryKey());
    awareDTO.getEntity().setPrimaryClient(primaryClient);
    awareDTO.getEntity().setSecondaryClient(secondaryClient);
  }

  /**
   * Rule-08840 When a relationship of Birth Mother, Alleged Mother, Presumed Mother, Birth Father,
   * Alleged Father or Presumed Father is selected and the parent has Tribal Membership Verification
   * data, create a duplicate Tribal Membership Verification row for all of their children.If the
   * child already has a Tribal Membership row that was created prior to R5.5 with the same Tribal
   * Affiliation/Tribe combination as the new row being added from the parent, then overwrite the
   * child's existing Tribal Membership row with the new Tribal Membership row created by the parent
   * and display error message. Keep the Membership Status, Status Date, and Enrollment Number from
   * the child's existing row if they exist.
   */
  private void validateAndAddIfNeededTribalMembershipVerification(DataAccessBundle bundle) {
    ClientRelationshipAwareDTO awareDTO = (ClientRelationshipAwareDTO) bundle.getAwareDto();
    if (!awareDTO.isNeedMembershipVerification()) {
      return;
    }

    ParametersValidator.checkEntityId(
        awareDTO.getEntity().getPrimaryClient(),
        awareDTO.getEntity().getPrimaryClient().getClass().getName());
    Client primaryClient = clientDao.find(awareDTO.getEntity().getPrimaryClient().getIdentifier());
    ParametersValidator.checkEntityId(
        awareDTO.getEntity().getSecondaryClient(),
        awareDTO.getEntity().getSecondaryClient().getClass().getName());
    Client secondaryClient =
        clientDao.find(awareDTO.getEntity().getSecondaryClient().getIdentifier());

    List<TribalMembershipVerification> secondaryTribals = new ArrayList<>();
    secondaryTribals.addAll(
        tribalMembershipVerificationDao.findByClientIdNoTribalEligFrom(
            secondaryClient.getIdentifier()));

    List<TribalMembershipVerification> primaryTribals = new ArrayList<>();
    primaryTribals.addAll(
        tribalMembershipVerificationDao.findByClientIdNoTribalEligFrom(
            primaryClient.getIdentifier()));

    // isPrimaryClientChild
    if (RelationshipUtil.isChildParent(
        ((ClientRelationshipAwareDTO) bundle.getAwareDto()).getEntity())) {
      updateTribals(primaryClient, secondaryTribals);
    } else {
      updateTribals(secondaryClient, primaryTribals);
    }
  }

  private void updateTribals(Client childClient, List<TribalMembershipVerification> parentTribals) {
    List<TribalMembershipVerification> childExtraTribals =
        getExtraRowsForChildClient(parentTribals, childClient.getIdentifier());

    childExtraTribals.forEach(
        e -> {
          TribalMembershipVerification newlyAdded = tribalMembershipVerificationDao.create(e);
          changedRows(newlyAdded, parentTribals);
        });
  }

  private void changedRows(
      TribalMembershipVerification newlyAdded, List<TribalMembershipVerification> childTribals) {
    if (newlyAdded == null || childTribals == null || childTribals.isEmpty()) {
      return;
    }
    childTribals.forEach(
        e -> {
          if (needToUpdateRow(e, newlyAdded)) {
            e.setStatusDate(newlyAdded.getStatusDate());
            e.setEnrollmentNumber(newlyAdded.getEnrollmentNumber());
            tribalMembershipVerificationDao.update(e);
          }
        });
  }

  private boolean needToUpdateRow(
      TribalMembershipVerification tribalForUpdate, TribalMembershipVerification newlyAddedTribal) {
    Date dateFromThirdId = CmsKeyIdGenerator.getDateFromKey(tribalForUpdate.getThirdId());
    if (dateFromThirdId == null) {
      return false;
    }

    LocalDate date = dateFromThirdId.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    if (date.isBefore(LocalDate.of(2005, Month.NOVEMBER, 19))) {
      if (tribalForUpdate.getIndianTribeType() == newlyAddedTribal.getIndianTribeType()
          && tribalForUpdate.getFkFromTribalMembershipVerification()
              == newlyAddedTribal.getFkFromTribalMembershipVerification()) return true;
    }

    return false;
  }

  private List<TribalMembershipVerification> getExtraRowsForChildClient(
      List<TribalMembershipVerification> secondaryTribals, String primaryClientId) {
    if (secondaryTribals == null || secondaryTribals.isEmpty()) {
      return new ArrayList<>();
    }

    final List<TribalMembershipVerification> extraRows = new ArrayList<>();
    secondaryTribals.forEach(
        a -> {
          if (StringUtils.isEmpty(a.getFkFromTribalMembershipVerification())) {
            TribalMembershipVerification extra = new TribalMembershipVerification();
            extra.setFkFromTribalMembershipVerification(a.getThirdId());
            extra.setFkSentToTribalOrganization(a.getFkSentToTribalOrganization());
            extra.setIndianEnrollmentStatus(a.getIndianEnrollmentStatus());
            extra.setIndianTribeType(a.getIndianTribeType());
            extra.setThirdId(IdGenerator.generateId());
            extra.setClientId(primaryClientId);
            extra.setLastUpdateId(PrincipalUtils.getStaffPersonId());
            extra.setLastUpdateTime(LocalDateTime.now());
            extraRows.add(extra);
          }
        });

    return extraRows;
  }

  private void deleteTribalMembershipVerifications(
      List<TribalMembershipVerification> tribalMembershipVerifications) {
    if (CollectionUtils.isEmpty(tribalMembershipVerifications)) {
      return;
    }

    tribalMembershipVerifications.forEach(
        e -> tribalMembershipVerificationDao.delete(e.getPrimaryKey()));
  }
}
