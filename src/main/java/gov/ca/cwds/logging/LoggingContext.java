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
    USER_ID, REMOTE_ADDRESS, SESSION_ID, REQUEST_ID, UNIQUE_ID
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
   * @param logParam
   * @param value
   */
  public void setLogParameter(LogParameter logParam, String value);

  /**
   * Get log parameter value stored in this context
   * 
   * @param logParam The log parameter
   * @return Value of log parameter
   */
  public String getLogParameter(LogParameter logParam);

}
