package gov.ca.cwds.cms.data.access.service;

import com.google.inject.Inject;
import gov.ca.cwds.data.legacy.cms.dao.CsecHistoryDao;
import gov.ca.cwds.data.legacy.cms.entity.CsecHistory;
import gov.ca.cwds.security.annotations.Authorize;

import java.util.List;

import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT_ID;

/** @author CWDS TPT-3 Team */
public class CsecHistoryService {

  private final CsecHistoryDao csecHistoryDao;

  @Inject
  public CsecHistoryService(CsecHistoryDao csecHistoryDao) {
    this.csecHistoryDao = csecHistoryDao;
  }

  public List<CsecHistory> findByClientId(@Authorize(CLIENT_READ_CLIENT_ID) final String clientId) {
    return csecHistoryDao.findByClientId(clientId);
  }
}
