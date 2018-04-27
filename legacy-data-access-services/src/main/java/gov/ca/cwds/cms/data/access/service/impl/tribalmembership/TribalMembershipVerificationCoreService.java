package gov.ca.cwds.cms.data.access.service.impl.tribalmembership;

import static gov.ca.cwds.cms.data.access.service.impl.tribalmembership.CreateLifeCycle.CLIENT_DAO_BUNDLE_KEY;

import gov.ca.cwds.cms.data.access.dto.TribalMembershipVerificationAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.DataAccessServiceBase;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessBundle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessServiceLifecycle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.TribalMembershipVerificationDao;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import javax.inject.Inject;

/** @author CWDS TPT-3 Team */
public class TribalMembershipVerificationCoreService
    extends DataAccessServiceBase<
        TribalMembershipVerificationDao, TribalMembershipVerification,
        TribalMembershipVerificationAwareDTO> {

  private final ClientDao clientDao;
  private final BusinessValidationService<TribalMembershipVerification, TribalMembershipVerificationAwareDTO>
      businessValidationService;

  @Inject
  public TribalMembershipVerificationCoreService(
      TribalMembershipVerificationDao tribalMembershipVerificationDao, ClientDao clientDao,
      BusinessValidationService<TribalMembershipVerification, TribalMembershipVerificationAwareDTO> businessValidationService) {
    super(tribalMembershipVerificationDao);
    this.clientDao = clientDao;
    this.businessValidationService = businessValidationService;
  }

  @Override
  protected void doEnrichDataAccessBundle(
      DataAccessBundle<TribalMembershipVerificationAwareDTO> dataAccessBundle) {
    super.doEnrichDataAccessBundle(dataAccessBundle);
    dataAccessBundle
        .getDaos()
        .put(CLIENT_DAO_BUNDLE_KEY, clientDao);
    dataAccessBundle.setBusinessValidationService(businessValidationService);
  }

  @Override
  protected DataAccessServiceLifecycle<TribalMembershipVerificationAwareDTO> getUpdateLifeCycle() {
    return new DefaultDataAccessLifeCycle<>();
  }

  @Override
  protected DataAccessServiceLifecycle<TribalMembershipVerificationAwareDTO> getCreateLifeCycle() {
    return new CreateLifeCycle();
  }

  @Override
  protected DataAccessServiceLifecycle<TribalMembershipVerificationAwareDTO> getDeleteLifeCycle() {
    return new DefaultDataAccessLifeCycle<>();
  }
}
