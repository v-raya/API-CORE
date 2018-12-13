package gov.ca.cwds.cms.data.access.service.impl.tribalmembership;

import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT_ID;

import gov.ca.cwds.cms.data.access.dto.TribalMembershipVerificationAwareDto;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.impl.DataAccessServiceBase;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessServiceLifecycle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.data.legacy.cms.dao.TribalMembershipVerificationDao;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.security.annotations.Authorize;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.time.LocalDateTime;
import java.util.List;
import javax.inject.Inject;

/**
 * CRUD service for Tribal membership verification.
 *
 * @author CWDS TPT-3 Team
 */
public class TribalMembershipVerificationCoreService
    extends DataAccessServiceBase<
        TribalMembershipVerificationDao, TribalMembershipVerification,
        TribalMembershipVerificationAwareDto> {

  private final CreateLifeCycle createLifeCycle;
  private final UpdateLifeCycle updateLifeCycle;

  @Inject
  public TribalMembershipVerificationCoreService(
      TribalMembershipVerificationDao tribalMembershipVerificationDao,
      CreateLifeCycle createLifeCycle,
      UpdateLifeCycle updateLifeCycle) {
    super(tribalMembershipVerificationDao);
    this.createLifeCycle = createLifeCycle;
    this.updateLifeCycle = updateLifeCycle;
  }

  @Override
  public TribalMembershipVerification create(TribalMembershipVerificationAwareDto entityAwareDto)
      throws DataAccessServicesException {
    String staffPerson = PrincipalUtils.getStaffPersonId();
    entityAwareDto.getEntity().setLastUpdateTime(LocalDateTime.now());
    entityAwareDto.getEntity().setLastUpdateId(staffPerson);
    entityAwareDto.getEntity().setThirdId(CmsKeyIdGenerator.getNextValue(staffPerson));
    return super.create(entityAwareDto);
  }

  @Override
  public TribalMembershipVerification update(TribalMembershipVerificationAwareDto entityAwareDto)
      throws DataAccessServicesException {
    String staffPerson = PrincipalUtils.getStaffPersonId();
    entityAwareDto.getEntity().setLastUpdateTime(LocalDateTime.now());
    entityAwareDto.getEntity().setLastUpdateId(staffPerson);
    return super.update(entityAwareDto);
  }


  public List<TribalMembershipVerification> getByClientId(
      @Authorize(CLIENT_READ_CLIENT_ID) final String clientId) {
    return getCrudDao(true).findByClientId(clientId);
  }

  @Override
  protected DataAccessServiceLifecycle<TribalMembershipVerificationAwareDto> getUpdateLifeCycle() {
    return updateLifeCycle;
  }

  @Override
  protected DataAccessServiceLifecycle<TribalMembershipVerificationAwareDto> getCreateLifeCycle() {
    return createLifeCycle;
  }

  @Override
  protected DataAccessServiceLifecycle<TribalMembershipVerificationAwareDto> getDeleteLifeCycle() {
    return new DefaultDataAccessLifeCycle<>();
  }
}
