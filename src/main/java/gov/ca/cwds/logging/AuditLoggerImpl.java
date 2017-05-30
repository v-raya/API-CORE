package gov.ca.cwds.logging;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.google.inject.Inject;

public class AuditLoggerImpl implements gov.ca.cwds.logging.AuditLogger {

  private static final Logger LOGGER = LoggerFactory.getLogger("AUDIT");

  public static final String REMOTE_ADDRESS = "remoteAddress";
  public static final String USER_ID = "userid";
  public static final String SESSION_ID = "sessionid";
  public static final String REQUEST_ID = "requestid";
  public static final String UNIQUE_ID = "uniqueId";

  /**
   * Constructor
   * 
   */
  @Inject
  public AuditLoggerImpl() {
    super();
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.logging.AuditLogger#audit(java.lang.String)
   */
  @Override
  public void audit(String data) {
    LOGGER.info(data);
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.logging.AuditLogger#teardownMDC()
   */
  @Override
  public void teardownMDC() {
    MDC.clear();
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.logging.AuditLogger#uniqueId()
   */
  @Override
  public String uniqueId() {
    String uniqueId = UUID.randomUUID().toString();
    MDC.put(UNIQUE_ID, uniqueId);
    return uniqueId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.logging.AuditLogger#storeRemoteAddress(java.lang.String)
   */
  @Override
  public void storeRemoteAddress(String remoteAddress) {
    MDC.put(REMOTE_ADDRESS, remoteAddress);
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.logging.AuditLogger#storeUserId(java.lang.String)
   */
  @Override
  public void storeUserId(String userid) {
    MDC.put(USER_ID, userid);
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.logging.AuditLogger#storeRequestId(java.lang.String)
   */
  @Override
  public void storeRequestId(String requestId) {
    MDC.put(REQUEST_ID, requestId);
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.logging.AuditLogger#storeSessionId(java.lang.String)
   */
  @Override
  public void storeSessionId(String sessionId) {
    MDC.put(SESSION_ID, sessionId);
  }

}

