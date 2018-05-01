package gov.ca.cwds.cms.data.access.service.impl.tribalmembership;

import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.cms.data.access.dto.TribalMembershipVerificationAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.impl.ClientCoreService;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessBundle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.cms.data.access.service.rules.TribalMembershipVerificationConfiguration;
import gov.ca.cwds.data.legacy.cms.dao.TribalMembershipVerificationDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import gov.ca.cwds.security.realm.PerryAccount;
import javax.inject.Inject;

/** @author CWDS TPT-3 Team */
public class CreateLifeCycle
    extends DefaultDataAccessLifeCycle<TribalMembershipVerificationAwareDTO> {

  private final ClientCoreService clientCoreService;
  private final BusinessValidationService<
          TribalMembershipVerification, TribalMembershipVerificationAwareDTO>
      businessValidationService;

  @Inject
  public CreateLifeCycle(
      TribalMembershipVerificationDao tribalMembershipVerificationDao,
      ClientCoreService clientCoreService,
      BusinessValidationService<TribalMembershipVerification, TribalMembershipVerificationAwareDTO>
          businessValidationService) {
    this.clientCoreService = clientCoreService;
    this.businessValidationService = businessValidationService;
  }

  @Override
  public void beforeDataProcessing(DataAccessBundle bundle) throws DataAccessServicesException {
    super.beforeDataProcessing(bundle);
    setTribalMembershipVerificationIndicatorForClient(bundle);
  }

  @Override
  public void businessValidation(DataAccessBundle bundle, PerryAccount perryAccount) {
    super.businessValidation(bundle, perryAccount);
    businessValidationService.runBusinessValidation(
        (TribalMembershipVerificationAwareDTO) bundle.getAwareDto(),
        perryAccount,
        TribalMembershipVerificationConfiguration.INSTANCE);
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
    if (client == null) {
      return;
    }

    client.setTribalMembershipVerifcationIndicator(true);

    ClientEntityAwareDTO clientEntityAwareDTO = new ClientEntityAwareDTO();
    clientEntityAwareDTO.setEntity(client);
    clientCoreService.update(clientEntityAwareDTO);
  }
}
