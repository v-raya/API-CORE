package gov.ca.cwds.service;

/**
 * @author CWDS TPT-2
 */
public interface ClientCountyDeterminationService {

  /**
   * This method determines client county by Client ID
   *
   * @param clientId Client ID
   * @return client county
   */
  Short getClientCountyById(String clientId);
}
