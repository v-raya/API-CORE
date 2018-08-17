package gov.ca.cwds.logging;

/**
 * Logging context
 * 
 * @author API Team
 */
public interface LoggingContext {

  /**
   * Allowed log parameters.
   */
  public enum LogParameter {
    USER_ID,

    STAFF_ID,

    STAFF_COUNTY,

    REMOTE_ADDRESS,

    SESSION_ID,

    REQUEST_ID,

    UNIQUE_ID,

    REQUEST_START_TIME,

    RESPONSE_STATUS
  }

  /**
   * Initialize the logging context
   * 
   * @return Unique id.
   */
  public String initialize();

  /**
   * Clear this logging context.
   */
  public void close();

  /**
   * Set given log parameter in this logging context.
   * 
   * @param logParam log param
   * @param value value to set
   */
  public void setLogParameter(LogParameter logParam, String value);

  /**
   * Get log parameter value stored in this context
   * 
   * @param logParam The log parameter
   * @return Value of log parameter
   */
  public String getLogParameter(LogParameter logParam);

  /**
   * Get LogParameter.UNIQUE_ID stored in this context
   *
   * @return Value of UNIQUE_ID log parameter
   */
  public String getUniqueId();
}
