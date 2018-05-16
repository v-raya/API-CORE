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
    enrichWithCurrentRelationship(bundle);
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
            secondaryClientIdentifier));

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

  @Override
  public void afterBusinessValidation(DataAccessBundle bundle) {
    super.afterBusinessValidation(bundle);
    deleteTribals(bundle);
  }

  private void deleteTribals(DataAccessBundle bundle) {
    ClientRelationshipAwareDTO awareDTO = (ClientRelationshipAwareDTO) bundle.getAwareDto();
    awareDTO
        .getTribalMembershipVerificationsForDelete()
        .forEach(e -> tribalMembershipVerificationDao.delete(e.getPrimaryKey()));
  }

  /**
   * Rule - 08861 "1) If a parent has one of the following relationships: Birth Mother, Alleged
   * Mother, Presumed Mother, Birth Father, Alleged Father or Presumed Father and the child in the
   * relationship has a membership status entered on their associated Tribal Membership Verification
   * row and if user tries to change relationship to something other than relationships listed
   * above, display error and reset to previous value. 2) If a parent has one of the following
   * relationships: Birth Mother, Alleged Mother, Presumed Mother, Birth Father, Alleged Father or
   * Presumed Father and the child in the relationship does not have a membership status entered on
   * their associated Tribal Membership Verification row, and if user tries to change relationship
   * to something other than relationships listed above, allow change in client relationship and
   * delete the associated child's Tribal Membership Verification rows created from this parent. 3)
   * If the relationship changes to one of the relationships listed above, then no action needs to
   * be taken."
   */
  private void enrichWithTribalsMembershipVerifications(DataAccessBundle bundle) {
    ClientRelationshipAwareDTO awareDTO = (ClientRelationshipAwareDTO) bundle.getAwareDto();

    ClientRelationship relationshipThatHasToBeChanged =
        awareDTO.getRelationshipThatHasToBeChanged();
    if (relationshipThatHasToBeChanged == null) {
      return;
    }

    Client parent = RelationshipUtil.getParent(relationshipThatHasToBeChanged);
    Client child = RelationshipUtil.getChild(relationshipThatHasToBeChanged);

    List<TribalMembershipVerification> tribalsThatHasSubTribals =
        tribalMembershipVerificationDao.findTribalsThatHaveSubTribalsByClientId(
            child.getIdentifier(), parent.getIdentifier());

    if (CollectionUtils.isNotEmpty(tribalsThatHasSubTribals)) {
      awareDTO.getTribalsThatHaveSubTribals().addAll(tribalsThatHasSubTribals);
    }
  }

  private void enrichWithCurrentRelationship(DataAccessBundle bundle) {
    ClientRelationshipAwareDTO awareDTO = (ClientRelationshipAwareDTO) bundle.getAwareDto();
    if (StringUtils.isEmpty(awareDTO.getEntity().getIdentifier())) {
      return;
    }

    awareDTO.setRelationshipThatHasToBeChanged(
        searchClientRelationshipService.getRelationshipById(awareDTO.getEntity().getIdentifier()));
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

    Client parent = RelationshipUtil.getParent(awareDTO.getEntity());
    Client child = RelationshipUtil.getChild(awareDTO.getEntity());

    List<TribalMembershipVerification> parentTribals =
        tribalMembershipVerificationDao.findByClientId(parent.getIdentifier());

    List<TribalMembershipVerification> childTribals =
        tribalMembershipVerificationDao.findByClientId(child.getIdentifier());

    parentTribals.forEach(
        parentTribal -> createExtraTribalIfNeeded(parent, child, parentTribal, childTribals));
  }

  private void createExtraTribalIfNeeded(
      Client parent,
      Client child,
      TribalMembershipVerification parentTribal,
      List<TribalMembershipVerification> childTribals) {

    TribalMembershipVerification newlyAdded = createExtraTribal(parentTribal, parent, child);

    if (CollectionUtils.isNotEmpty(childTribals)) {
      childTribals.forEach(
          childTribal -> changeChildTribalIfNeeded(parent, child, childTribal, newlyAdded));
    }

    tribalMembershipVerificationDao.create(newlyAdded);
  }

  private void changeChildTribalIfNeeded(
      Client parent,
      Client child,
      TribalMembershipVerification childTribal,
      TribalMembershipVerification newlyAdded) {
    Date dateFromThirdId = CmsKeyIdGenerator.getDateFromKey(childTribal.getThirdId());
    if (dateFromThirdId == null) {
      return;
    }

    LocalDate date = dateFromThirdId.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    if (childTribal.getFkFromTribalMembershipVerification() != null
        && date.isBefore(LocalDate.of(2005, Month.NOVEMBER, 19))) {
      newlyAdded.setStatusDate(childTribal.getStatusDate());
      newlyAdded.setEnrollmentNumber(childTribal.getEnrollmentNumber());
      newlyAdded.setIndianEnrollmentStatus(childTribal.getIndianEnrollmentStatus());

      tribalMembershipVerificationDao.delete(childTribal.getThirdId());
    }
  }

  private TribalMembershipVerification createExtraTribal(
      TribalMembershipVerification parentTribal, Client parent, Client child) {
    TribalMembershipVerification extra = new TribalMembershipVerification();
    extra.setFkFromTribalMembershipVerification(parentTribal.getThirdId());
    extra.setFkSentToTribalOrganization(parentTribal.getFkSentToTribalOrganization());
    extra.setIndianTribeType(parentTribal.getIndianTribeType());
    extra.setThirdId(IdGenerator.generateId());
    extra.setClientId(child.getIdentifier());
    extra.setLastUpdateId(PrincipalUtils.getStaffPersonId());
    extra.setLastUpdateTime(LocalDateTime.now());
    return extra;
  }
}
