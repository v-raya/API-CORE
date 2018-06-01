package gov.ca.cwds.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class AuditLoggerImpl implements gov.ca.cwds.logging.AuditLogger {

  private static final Logger LOGGER = LoggerFactory.getLogger("AUDIT");

  /**
   * Constructor
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

}
