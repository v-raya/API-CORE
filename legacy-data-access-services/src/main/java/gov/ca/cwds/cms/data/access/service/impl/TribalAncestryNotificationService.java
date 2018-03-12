package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.data.legacy.cms.dao.TribalAncestryNotificationDao;
import gov.ca.cwds.data.legacy.cms.entity.TribalAncestryNotification;
import gov.ca.cwds.security.annotations.Authorize;

import javax.inject.Inject;
import java.util.List;

import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT_ID;

public class TribalAncestryNotificationService {

  private TribalAncestryNotificationDao tribalAncestryNotificationDao;

  @Inject
  public TribalAncestryNotificationService(
      TribalAncestryNotificationDao tribalAncestryNotificationDao) {
    this.tribalAncestryNotificationDao = tribalAncestryNotificationDao;
  }

  public List<TribalAncestryNotification> findByClientId(
      @Authorize(CLIENT_READ_CLIENT_ID) final String clientId) {

    return tribalAncestryNotificationDao.findByChildClientId(clientId);
  }
}
