package gov.ca.cwds.logging;

/**
 * Interface for audit logger
 * 
 * @author CWDS API Team
 */
public interface AuditLogger {

  /**
   * Writes given message to the audit log with the given event type
   * 
   * @param data The data to audit
   *
   */
  public void audit(String data);

  /**
   * Generate a unique Id
   * 
   * @return A unique id for this request
   */
  public String uniqueId();

  /**
   * Store the remote address
   * 
   * @param remoteAddress The remote address
   * 
   */
  public void storeRemoteAddress(String remoteAddress);

  /**
   * Store the user id
   * 
   * @param userid The user id
   */
  public void storeUserId(String userid);

  /**
   * Store the request id
   * 
   * @param requestId The request id
   */
  public void storeRequestId(String requestId);

  /**
   * Store the session id
   * 
   * @param sessionId The session id
   */
  public void storeSessionId(String sessionId);

  /**
   * Clear the thread's MDC.
   */
  public void teardownMDC();

}
