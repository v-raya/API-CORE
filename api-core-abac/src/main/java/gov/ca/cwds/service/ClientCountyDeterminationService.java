package gov.ca.cwds.service;

import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.entity.facade.ClientCounty;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;

import gov.ca.cwds.data.dao.cms.CountyDeterminationDao;
import java.util.Map;

/**
 * @author CWDS TPT-2
 */
public class ClientCountyDeterminationService {

  private final CountyDeterminationDao countyDeterminationDao;
  private final ClientDao clientDao;

  @Inject
  public ClientCountyDeterminationService(CountyDeterminationDao countyDeterminationDao, ClientDao clientDao) {
    this.countyDeterminationDao = countyDeterminationDao;
    this.clientDao = clientDao;
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

  /**
   * This method determines client county by Client ID from main operational database
   * instead of replicated table
   *
   * @param clientId Client ID
   * @return ClientCounty one Client county
   */
  public ClientCounty getClientCountiesRealtimeById(final String clientId) {
    return clientDao.getClientCounty(clientId);
  }

  /**
   *
   * @param clientIds - collection of client id-s
   * @return map where key is a client id and value is a list of county codes
   */
  public Map<String, List<Short>> getClientCountiesMapByIds(final Collection<String> clientIds) {
    return countyDeterminationDao.getClientCountiesMap(clientIds);
  }

}
