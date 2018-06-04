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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Life Cycle for create update relationships.
 *
 * @author CWDS TPT-3 Team
 */
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
    ClientRelationshipAwareDTO awareDto = (ClientRelationshipAwareDTO) bundle.getAwareDto();

    if (RelationshipUtil.isChildParent(awareDto.getEntity())
        || RelationshipUtil.isParentChild(awareDto.getEntity())) {
      awareDto.setParent(RelationshipUtil.getParent(awareDto.getEntity()));
      awareDto.setChild(RelationshipUtil.getChild(awareDto.getEntity()));
    }

    enrichWithExistingRelationships(bundle);
    enrichWithExistingPaternityDetails(bundle);
  }

  @Override
  public void businessValidation(DataAccessBundle bundle, PerryAccount perryAccount) {
    businessValidationService.runBusinessValidation(
        (ClientRelationshipAwareDTO) bundle.getAwareDto(),
        PrincipalUtils.getPrincipal(),
        ClientRelationshipDroolsConfiguration.INSTANCE);
  }

  protected void deleteTribals(DataAccessBundle bundle) {
    ClientRelationshipAwareDTO awareDto = (ClientRelationshipAwareDTO) bundle.getAwareDto();
    awareDto
        .getTribalMembershipVerificationsForDelete()
        .forEach(e -> tribalMembershipVerificationDao.delete(e.getPrimaryKey()));
  }

  protected void createTribals(DataAccessBundle bundle) {
    ClientRelationshipAwareDTO awareDto = (ClientRelationshipAwareDTO) bundle.getAwareDto();
    awareDto
        .getTribalMembershipVerificationsForCreate()
        .forEach(tribalMembershipVerificationDao::create);
  }

  private void enrichWithExistingRelationships(DataAccessBundle bundle) {
    ClientRelationshipAwareDTO awareDto = (ClientRelationshipAwareDTO) bundle.getAwareDto();
    Client primaryClient = awareDto.getEntity().getPrimaryClient();
    Client secondaryClient = awareDto.getEntity().getSecondaryClient();

    awareDto
        .getClientRelationshipList()
        .addAll(searchClientRelationshipService.findRelationshipsByPrimaryClient(primaryClient));
    awareDto
        .getClientRelationshipList()
        .addAll(
            searchClientRelationshipService.findRelationshipsBySecondaryClient(secondaryClient));
    awareDto
        .getClientRelationshipList()
        .removeIf(e -> e.getIdentifier().equals(awareDto.getEntity().getIdentifier()));
  }

  private void enrichWithExistingPaternityDetails(DataAccessBundle bundle) {
    ClientRelationshipAwareDTO awareDto = (ClientRelationshipAwareDTO) bundle.getAwareDto();
    Client primaryClient = awareDto.getEntity().getPrimaryClient();
    Client secondaryClient = awareDto.getEntity().getSecondaryClient();

    awareDto
        .getPrimaryClientPaternityDetails()
        .addAll(paternityDetailDao.findByChildClientId(primaryClient.getIdentifier()));

    awareDto
        .getSecondaryClientPaternityDetails()
        .addAll(paternityDetailDao.findByChildClientId(secondaryClient.getIdentifier()));
  }

  private void enrichWithPrimaryAndSecondaryClients(DataAccessBundle bundle) {
    ClientRelationshipAwareDTO awareDto = (ClientRelationshipAwareDTO) bundle.getAwareDto();
    ParametersValidator.checkEntityId(
        awareDto.getEntity().getPrimaryClient(), awareDto.getEntity().getClass().getName());
    Client primaryClient = clientDao.find(awareDto.getEntity().getPrimaryClient().getPrimaryKey());
    ParametersValidator.checkEntityId(
        awareDto.getEntity().getSecondaryClient(), awareDto.getEntity().getClass().getName());
    Client secondaryClient =
        clientDao.find(awareDto.getEntity().getSecondaryClient().getPrimaryKey());
    awareDto.getEntity().setPrimaryClient(primaryClient);
    awareDto.getEntity().setSecondaryClient(secondaryClient);
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
    ClientRelationshipAwareDTO awareDto = (ClientRelationshipAwareDTO) bundle.getAwareDto();
    if (!awareDto.isNeedMembershipVerification()) {
      return;
    }

    Set<TribalMembershipVerification> extraTribals = getExtraTribalsForCreate(awareDto);
    List<TribalMembershipVerification> existingChildTribals = getExistingTribals(extraTribals);

    extraTribals.forEach(e -> filterTribal(awareDto, existingChildTribals, e));
  }

  private void filterTribal(
      ClientRelationshipAwareDTO awareDto,
      List<TribalMembershipVerification> existingChildTribals,
      TribalMembershipVerification extra) {
    final boolean[] exist = {false};
    if (CollectionUtils.isEmpty(existingChildTribals)) {
      awareDto.getTribalMembershipVerificationsForCreate().add(extra);
      return;
    }

    existingChildTribals.forEach(
        existingChild -> {
          if (StringUtils.isEmpty(existingChild.getFkFromTribalMembershipVerification())) {
            Date dateFromThirdId = CmsKeyIdGenerator.getDateFromKey(existingChild.getThirdId());
            if (dateFromThirdId == null) {
              return;
            }

            LocalDate date =
                dateFromThirdId.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (date.isBefore(LocalDate.of(2005, Month.NOVEMBER, 19))) {
              awareDto.getTribalMembershipVerificationsForDelete().add(existingChild);
              extra.setIndianEnrollmentStatus(existingChild.getIndianEnrollmentStatus());
              extra.setStatusDate(existingChild.getStatusDate());
              extra.setEnrollmentNumber(existingChild.getEnrollmentNumber());
              awareDto.getTribalMembershipVerificationsForCreate().add(extra);
              exist[0] = true;
            }
          } else if (existingChild.getClientId().equals(extra.getClientId())
              && StringUtils.isNotEmpty(existingChild.getFkFromTribalMembershipVerification())) {
            exist[0] = true;
          }
        });

    if (!exist[0]) {
      awareDto.getTribalMembershipVerificationsForCreate().add(extra);
    }
  }

  private Set<TribalMembershipVerification> getExtraTribalsForCreate(
      ClientRelationshipAwareDTO awareDto) {

    Client parent = RelationshipUtil.getParent(awareDto.getEntity());
    Client child = RelationshipUtil.getChild(awareDto.getEntity());

    List<TribalMembershipVerification> parentTribals =
        tribalMembershipVerificationDao.findByClientId(parent.getIdentifier());

    if (parentTribals.isEmpty()) {
      return new HashSet<>();
    }

    Set<TribalMembershipVerification> extraTribals = new HashSet<>();
    TribalMembershipVerification parentTribal = parentTribals.get(0);

    List<ClientRelationship> allParenPrimaryRelationships =
        searchClientRelationshipService.findRelationshipsByPrimaryClient(parent);
    List<ClientRelationship> allParenSecondaryRelationships =
        searchClientRelationshipService.findRelationshipsBySecondaryClient(parent);

    List<ClientRelationship> allParentRelationships =
        Stream.concat(
                allParenPrimaryRelationships.stream(), allParenSecondaryRelationships.stream())
            .collect(Collectors.toList());

    allParentRelationships.forEach(
        e -> {
          Client additionalChild = RelationshipUtil.getChild(e);
          extraTribals.add(createExtraTribal(parentTribal, additionalChild));
        });
    extraTribals.add(createExtraTribal(parentTribal, child));

    return extraTribals;
  }

  private List<TribalMembershipVerification> getExistingTribals(
      Set<TribalMembershipVerification> extraTribals) {
    return tribalMembershipVerificationDao.findByClientIds(
        extraTribals
            .stream()
            .map(TribalMembershipVerification::getClientId)
            .toArray(String[]::new));
  }

  private TribalMembershipVerification createExtraTribal(
      TribalMembershipVerification parentTribal, Client child) {
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
