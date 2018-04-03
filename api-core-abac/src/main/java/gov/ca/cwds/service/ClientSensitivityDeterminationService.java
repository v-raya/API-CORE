package gov.ca.cwds.service;

import com.google.inject.Inject;
import gov.ca.cwds.data.dao.cms.SensitivityDeterminationDao;
import gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity;
import javax.persistence.NoResultException;

/**
 * Service to determine client sensitivity.
 *
 * @author CWDS API Team
 */
public class ClientSensitivityDeterminationService {

  private SensitivityDeterminationDao sensitivityDeterminationDao;

  @Inject
  public ClientSensitivityDeterminationService(
      SensitivityDeterminationDao sensitivityDeterminationDao) {
    this.sensitivityDeterminationDao = sensitivityDeterminationDao;
  }

  /**
   * Method to determine client sensitivity.
   *
   * @param clientId client id
   * @return sensitivity or null if client is not found
   */
  public Sensitivity getClientSensitivityById(String clientId) {
    try {
      return sensitivityDeterminationDao.getSensitivity(clientId);
    } catch (NoResultException e) {
      return null;
    }
  }
}
