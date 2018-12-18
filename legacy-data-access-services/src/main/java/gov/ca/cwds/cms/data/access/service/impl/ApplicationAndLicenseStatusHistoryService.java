package gov.ca.cwds.cms.data.access.service.impl;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dao.XaDaoProvider;
import gov.ca.cwds.cms.data.access.dto.AppAndLicHistoryAwareDTO;
import gov.ca.cwds.data.legacy.cms.dao.ApplicationAndLicenseStatusHistoryDao;
import gov.ca.cwds.data.legacy.cms.entity.ApplicationAndLicenseStatusHistory;
import java.util.function.Consumer;

public class ApplicationAndLicenseStatusHistoryService
  extends DefaultCmsDataAccessService<ApplicationAndLicenseStatusHistoryDao,
  ApplicationAndLicenseStatusHistory,
  AppAndLicHistoryAwareDTO> {

  @Inject
  public ApplicationAndLicenseStatusHistoryService(XaDaoProvider xaDaoProvider) {
    super(xaDaoProvider.getDao(ApplicationAndLicenseStatusHistoryDao.class));
  }

  @Override
  protected Consumer<String> idSetter(ApplicationAndLicenseStatusHistory entity) {
    return entity::setIdentifier;
  }
}
