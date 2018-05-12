package gov.ca.cwds.cms.data.access.service.impl.tribalmembership;

import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.cms.data.access.dto.TribalMembershipVerificationAwareDto;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.impl.ClientCoreService;
import gov.ca.cwds.cms.data.access.service.impl.IdGenerator;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessBundle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.cms.data.access.service.rules.TribalMembershipVerificationConfiguration;
import gov.ca.cwds.data.legacy.cms.dao.TribalMembershipVerificationDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.time.LocalDateTime;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.collections4.CollectionUtils;

/**
 * Life Cycle for create Tribal membership verification.
 *
 * @author CWDS TPT-3 Team
 */
public class CreateLifeCycle
    extends DefaultDataAccessLifeCycle<TribalMembershipVerificationAwareDto> {

  private final ClientCoreService clientCoreService;
  private final BusinessValidationService<
          TribalMembershipVerification, TribalMembershipVerificationAwareDto>
      businessValidationService;
  private TribalMembershipVerificationDao tribalMembershipVerificationDao;

  @Inject
  public CreateLifeCycle(
      TribalMembershipVerificationDao tribalMembershipVerificationDao,
      ClientCoreService clientCoreService,
      BusinessValidationService<TribalMembershipVerification, TribalMembershipVerificationAwareDto>
          businessValidationService) {
    this.clientCoreService = clientCoreService;
    this.businessValidationService = businessValidationService;
    this.tribalMembershipVerificationDao = tribalMembershipVerificationDao;
  }

  @Override
  public void beforeDataProcessing(DataAccessBundle bundle) throws DataAccessServicesException {
    super.beforeDataProcessing(bundle);
    setTribalMembershipVerificationIndicatorForClient(bundle);
    enrichWithClients(bundle);
    enrichWithParentAndChildTribals(bundle);
    createChildTribalForDuplicate(bundle);
  }

  @Override
  public void dataProcessing(DataAccessBundle bundle, PerryAccount perryAccount) {
    super.dataProcessing(bundle, perryAccount);
    businessValidationService.runBusinessValidation(
      (TribalMembershipVerificationAwareDto) bundle.getAwareDto(),
      PrincipalUtils.getPrincipal(),
      TribalMembershipVerificationConfiguration.DATA_PROCESSING_INSTANCE);
  }

  @Override
  public void businessValidation(DataAccessBundle bundle, PerryAccount perryAccount) {
    super.businessValidation(bundle, perryAccount);
    businessValidationService.runBusinessValidation(
        (TribalMembershipVerificationAwareDto) bundle.getAwareDto(),
        perryAccount,
        TribalMembershipVerificationConfiguration.INSTANCE);
  }

  @Override
  public void afterStore(DataAccessBundle bundle) throws DataAccessServicesException {
    super.afterStore(bundle);
    storeChildDuplicatedTribal(bundle);
  }

  private void createChildTribalForDuplicate(DataAccessBundle bundle) {
    TribalMembershipVerificationAwareDto awareDto =
      (TribalMembershipVerificationAwareDto) bundle.getAwareDto();

    awareDto.getEntity().setThirdId(IdGenerator.generateId());

    TribalMembershipVerification childTribal = new TribalMembershipVerification();
    childTribal.setFkFromTribalMembershipVerification(awareDto.getEntity().getThirdId());
    childTribal.setFkSentToTribalOrganization(awareDto.getEntity().getFkSentToTribalOrganization());
    childTribal.setIndianEnrollmentStatus(awareDto.getEntity().getIndianEnrollmentStatus());
    childTribal.setIndianTribeType(awareDto.getEntity().getIndianTribeType());
    childTribal.setStatusDate(awareDto.getEntity().getStatusDate());
    childTribal.setThirdId(IdGenerator.generateId());
    childTribal.setClientId(awareDto.getChildId());
    childTribal.setLastUpdateTime(LocalDateTime.now());
    childTribal.setLastUpdateId(PrincipalUtils.getStaffPersonId());

    awareDto.setChildTribalForDuplicate(childTribal);
  }

  private void enrichWithParentAndChildTribals(DataAccessBundle bundle) {
    TribalMembershipVerificationAwareDto awareDto =
      (TribalMembershipVerificationAwareDto) bundle.getAwareDto();

    List<TribalMembershipVerification> allTribals = tribalMembershipVerificationDao.findByClientIds(awareDto.getChildId(), awareDto.getParentId());
    if (CollectionUtils.isEmpty(allTribals)) {
      return;
    }

    allTribals.forEach(e->{
      if(awareDto.getChildId().equals(e.getClientId())) {
        awareDto.getChildTribals().add(e);
      } else {
        awareDto.getParentTribals().add(e);
      }
    });
  }

  private void enrichWithClients(DataAccessBundle bundle) {
    TribalMembershipVerificationAwareDto awareDto =
      (TribalMembershipVerificationAwareDto) bundle.getAwareDto();

    awareDto.setParentClient(clientCoreService.find(awareDto.getParentId()));
    awareDto.setChildClient(clientCoreService.find(awareDto.getChildId()));
  }

  private void storeChildDuplicatedTribal(DataAccessBundle bundle) {
    TribalMembershipVerificationAwareDto awareDto =
      (TribalMembershipVerificationAwareDto) bundle.getAwareDto();
    tribalMembershipVerificationDao.create(awareDto.getChildTribalForDuplicate());
  }

  /**
   * R - 01128.
   *
   * <p>Set Tribal Membership Verification Indicator Variable to Yes when creating Tribal Membership
   * rows.
   */
  private void setTribalMembershipVerificationIndicatorForClient(DataAccessBundle bundle)
      throws DataAccessServicesException {
    TribalMembershipVerification tribalMembershipVerification =
        (TribalMembershipVerification) bundle.getAwareDto().getEntity();

    Client client = clientCoreService.find(tribalMembershipVerification.getClientId());
    if (client == null || client.getTribalMembershipVerifcationIndicator()) {
      return;
    }

    client.setTribalMembershipVerifcationIndicator(true);

    ClientEntityAwareDTO clientEntityAwareDTO = new ClientEntityAwareDTO();
    clientEntityAwareDTO.setEntity(client);
    clientCoreService.update(clientEntityAwareDTO);
  }

}
