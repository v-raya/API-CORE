package gov.ca.cwds.cms.data.access.service.impl.tribalmembership;

import gov.ca.cwds.cms.data.access.dto.TribalMembershipVerificationAwareDTO;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessBundle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.cms.data.access.service.rules.TribalMembershipVerificationConfiguration;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author CWDS TPT-3 Team */
public class CreateLifeCycle
    extends DefaultDataAccessLifeCycle<TribalMembershipVerificationAwareDTO> {

  private static final Logger LOG = LoggerFactory.getLogger(CreateLifeCycle.class);
  public static final String CLIENT_DAO_BUNDLE_KEY = "CLIENT_DAO_BUNDLE_KEY";

  @Override
  public void beforeDataProcessing(DataAccessBundle bundle) {
    super.beforeDataProcessing(bundle);
    setTribalMembershipVerificationIndicatorForClient(bundle);
  }

  @Override
  public void afterDataProcessing(DataAccessBundle bundle) {
    super.afterDataProcessing(bundle);
  }

  @Override
  public void beforeBusinessValidation(DataAccessBundle bundle) {
    super.beforeBusinessValidation(bundle);
  }

  @Override
  public void afterBusinessValidation(DataAccessBundle bundle) {
    super.afterBusinessValidation(bundle);
  }

  @Override
  public void dataProcessing(DataAccessBundle bundle, PerryAccount perryAccount) {
    super.dataProcessing(bundle, perryAccount);
  }

  @Override
  public void businessValidation(DataAccessBundle bundle, PerryAccount perryAccount) {
    super.businessValidation(bundle, perryAccount);
    if (bundle.getBusinessValidationService() == null) {
      LOG.warn(
          "No bussines validation for create TribalMembershipVerification business validation service is null package {}, class {}, method businessValidation",
          this.getClass().getPackage().getName(),
          this.getClass().getName());
      return;
    }
  }

  @Override
  public void afterStore(DataAccessBundle bundle) throws DataAccessServicesException {
    super.afterStore(bundle);
  }

  /**
   * R - 01128.
   *
   * <p>Set Tribal Membership Verification Indicator Variable to Yes when creating Tribal Membership
   * rows.
   */
  private void setTribalMembershipVerificationIndicatorForClient(DataAccessBundle bundle) {
    TribalMembershipVerification tribalMembershipVerification =
        (TribalMembershipVerification) bundle.getAwareDto().getEntity();

    ClientDao clientDao = (ClientDao) bundle.getDaos().get(CLIENT_DAO_BUNDLE_KEY);
    Client client = clientDao.find(tribalMembershipVerification.getClientId());
    if (client != null) {
      client.setTribalMembershipVerifcationIndicator(true);
    }
    clientDao.update(client);
  }
}
