package gov.ca.cwds.service;

import java.util.List;

import com.google.inject.Inject;

import gov.ca.cwds.data.dao.cms.CountyDeterminationDao;

/**
 * @author CWDS TPT-2
 */
public class ClientCountyDeterminationService {

  private final CountyDeterminationDao countyDeterminationDao;

  @Inject
  public ClientCountyDeterminationService(CountyDeterminationDao countyDeterminationDao) {
    this.countyDeterminationDao = countyDeterminationDao;
  }

  /**
   * This method determines client counties by Client ID.
   *
   * @param clientId Client ID
   * @return client counties
   */
  public List<Short> getClientCountiesById(final String clientId) {
    return countyDeterminationDao.getClientCounties(clientId);
  }

}
