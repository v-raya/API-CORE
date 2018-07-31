package gov.ca.cwds.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.dao.cms.CountyDeterminationDao;

/**
 * @author CWDS TPT-2
 */
public class ClientCountyDeterminationService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ClientCountyDeterminationService.class);

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
    LOGGER.debug("ClientCountyDeterminationService.getClientCountiesById: clientId: {}", clientId);
    return countyDeterminationDao.getClientCounties(clientId);
  }

  /**
   *
   * @param clientIds - collection of client id-s
   * @return map where key is a client id and value is a list of county codes
   */
  public Map<String, List<Short>> getClientCountiesMapByIds(final Collection<String> clientIds) {
    LOGGER.debug("ClientCountyDeterminationService.getClientCountiesMapByIds: clientIds: {}",
        clientIds);
    return countyDeterminationDao.getClientCountiesMap(clientIds);
  }

}
