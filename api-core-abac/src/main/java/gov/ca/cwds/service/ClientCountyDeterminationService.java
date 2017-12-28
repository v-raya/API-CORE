package gov.ca.cwds.service;

import com.google.inject.Inject;
import gov.ca.cwds.data.dao.cms.CountyDeterminationDao;
import java.util.List;

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
   * This method determines client county by Client ID
   *
   * @param clientId Client ID
   * @return client county
   */
  public Short getClientCountyById(final String clientId) {
    final List<Short> counties = countyDeterminationDao.getClientCounties(clientId);
    final Short countyId = counties.isEmpty() ? null : counties.get(0);
    return countyId == null || countyId == 0
        ? null
        : countyId;
  }
}
