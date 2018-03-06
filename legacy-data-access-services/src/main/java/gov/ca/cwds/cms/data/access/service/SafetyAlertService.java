package gov.ca.cwds.cms.data.access.service;

import gov.ca.cwds.data.legacy.cms.dao.SafetyAlertDao;
import gov.ca.cwds.data.legacy.cms.entity.SafetyAlert;
import gov.ca.cwds.security.annotations.Authorize;

import javax.inject.Inject;
import java.util.Collection;

import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT_ID;

public class SafetyAlertService {

  private final SafetyAlertDao safetyAlertDao;

  @Inject
  public SafetyAlertService(SafetyAlertDao safetyAlertDao) {
    this.safetyAlertDao = safetyAlertDao;
  }

  public Collection<SafetyAlert> findSafetyAlertsByClientId(
      @Authorize(CLIENT_READ_CLIENT_ID) final String clientId) {
    return safetyAlertDao.findByClientId(clientId);
  }
}
