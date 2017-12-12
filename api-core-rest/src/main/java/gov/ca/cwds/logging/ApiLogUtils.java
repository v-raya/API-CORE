package gov.ca.cwds.logging;

import java.text.MessageFormat;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.ApiException;

/**
 * Logging utilities for CWDS projects.
 * 
 * @author CWDS API Team
 * @param <E> runtime exception type
 */
public class ApiLogUtils<E extends ApiException> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiLogUtils.class);

  private static final int DEFAULT_LOG_EVERY = 5000;

  private Class<E> klazz;

  /**
   * Construct with the exception class to throw. Unfortunately, Java generics lose runtime type
   * information without this verbose syntax.
   * 
   * @param klazz exception class to throw
   */
  public ApiLogUtils(Class<E> klazz) {
    this.klazz = klazz;
  }

  /**
   * Construct a new instance of specified RuntimeException class.
   * 
   * @param msg message
   * @param t Throwable
   * @return new instance of the RuntimeException class
   */
  protected E newInstance(String msg, Throwable t) {
    E ret = null;

    try {
      ret = klazz.getConstructor(new Class[] {String.class, Throwable.class}).newInstance(msg, t);
    } catch (Exception e) {
      throw new ApiException("Failed to construct type", e);
    }

    return ret;
  }

  /**
   * Log every {@link #DEFAULT_LOG_EVERY} records. Useful for batch jobs, processing large data
   * sets, or long-running processes.
   * 
   * @param log Logger
   * @param cntr record count
   * @param action action message (extract, transform, load, etc)
   * @param args variable message arguments
   */
  public static void logEvery(Logger log, int cntr, String action, Object... args) {
    if (cntr > 0 && (cntr % DEFAULT_LOG_EVERY) == 0) {
      log.info("{} {} {}", action, cntr, args);
    }
  }

  /**
   * Convenience overload of {@link #logEvery(Logger, int, String, Object...)}, using this Logger
   * for this class. Log every {@link #DEFAULT_LOG_EVERY} records.
   * 
   * @param cntr record count
   * @param action action message (extract, transform, load, etc)
   * @param args variable message arguments
   */
  public static void logEvery(int cntr, String action, Object... args) {
    logEvery(LOGGER, cntr, action, args);
  }

  /**
   * Format message and throw a runtime {@link ApiException}. Call to raise an error severe enough
   * to merit terminating a job or process.
   * 
   * @param log class logger
   * @param t any Throwable
   * @param pattern MessageFormat pattern
   * @param args error message, excluding throwable message
   * @throws ApiException runtime exception
   */
  public void raiseError(final org.slf4j.Logger log, Throwable t, String pattern, Object... args) {
    final Object[] objs = ArrayUtils.isEmpty(args) ? new Object[0] : args;
    final String pat = !StringUtils.isEmpty(pattern) ? pattern : StringUtils.join(objs, "{}");
    final String msg = MessageFormat.format(pat, objs);
    log.error(msg, t);
    throw newInstance(msg, t);
  }

  /**
   * Convenience overload of {@link #raiseError(Logger, Throwable, String, Object...)}. Format
   * message and throw a runtime {@link ApiException}.
   * 
   * @param log class logger
   * @param t any Throwable
   * @param args error message or throwable message
   * @throws ApiException runtime exception
   */
  public void raiseError(final org.slf4j.Logger log, Throwable t, Object... args) {
    raiseError(log, t, null, args);
  }

}
