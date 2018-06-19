package gov.ca.cwds.cms.data.access.service.impl;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dto.AppAndLicHistoryAwareDTO;
import gov.ca.cwds.cms.data.access.service.DefaultCmsDataAccessService;
import gov.ca.cwds.data.legacy.cms.dao.ApplicationAndLicenseStatusHistoryDao;
import gov.ca.cwds.data.legacy.cms.entity.ApplicationAndLicenseStatusHistory;

public class ApplicationAndLicenseStatusHistoryService
  extends DefaultCmsDataAccessService<ApplicationAndLicenseStatusHistoryDao,
  ApplicationAndLicenseStatusHistory,
  AppAndLicHistoryAwareDTO> {

  @Inject
  public ApplicationAndLicenseStatusHistoryService(
    ApplicationAndLicenseStatusHistoryDao applicationAndLicenseStatusHistoryDao) {
    super(applicationAndLicenseStatusHistoryDao);
  }

  @Override
  protected void setId(ApplicationAndLicenseStatusHistory entity, String id) {
    entity.setIdentifier(id);
  }
}
