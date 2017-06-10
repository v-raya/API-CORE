package gov.ca.cwds.logging;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.reflect.TypeToken;

import gov.ca.cwds.rest.api.ApiException;

/**
 * Logging utilities for CWDS projects.
 * 
 * @author CWDS API Team
 * @param <E> runtime exception type
 */
public class ApiLogUtils<E extends RuntimeException> {

  private static final Logger LOGGER = LogManager.getLogger(ApiLogUtils.class);

  private static final int DEFAULT_LOG_EVERY = 5000;

  private final ApiSupplier<E> supplier = new ApiSupplier<>(this::newInstance);

  // private static final ApiLogUtils instance;

  private ApiLogUtils() {
    // Static methods only, no class instantiation, Evil singleton, blah, blah, blah ... I can't
    // hear you!
  }

  @SuppressWarnings("unchecked")
  public Class<E> getTypeParameterClass() {
    Type type = getClass().getGenericSuperclass();
    ParameterizedType paramType = (ParameterizedType) type;
    return (Class<E>) paramType.getActualTypeArguments()[0];
  }

  public E newInstance() {
    E ret;
    try {
      ret = this.getTypeParameterClass().newInstance();
    } catch (Exception e) {
      throw new ApiException(e);
    }

    return ret;
  }

  static class StrawManParameterizedClass<T> {
    final TypeToken<T> type = new TypeToken<T>(getClass()) {};
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
  public void logEvery(int cntr, String action, Object... args) {
    logEvery(LOGGER, cntr, action, args);
  }

  /**
   * Format message and throw a runtime {@link ApiException}. Call to raise an error severe enough
   * to merit terminating a job or process.
   * 
   * @param log class logger
   * @param e any Throwable
   * @param pattern MessageFormat pattern
   * @param args error message, excluding throwable message
   * @throws ApiException runtime exception
   */
  public void throwFatalError(final Logger log, Throwable e, String pattern, Object... args) {
    final Object[] objs = ArrayUtils.isEmpty(args) ? new Object[0] : args;
    final String pat = !StringUtils.isEmpty(pattern) ? pattern : StringUtils.join(objs, "{}");
    final String msg = MessageFormat.format(pat, objs);
    final Logger logger = log != null ? log : LOGGER;
    logger.fatal(msg, e);

    try {
      final StrawManParameterizedClass<E> smpc = new StrawManParameterizedClass<E>() {};
      final String string = (String) smpc.type.getRawType().newInstance();
      System.out.format("string = \"%s\"", string);
    } catch (Exception e2) {
      // TODO: handle exception
    }

    // E ex = supplier.createContents();
    // throw new E(msg, e);
    throw new ApiException(msg, e);
  }

  /**
   * Convenience overload of {@link #throwFatalError(Logger, Throwable, String, Object...)}. Format
   * message and throw a runtime {@link ApiException}.
   * 
   * @param log class logger
   * @param e any Throwable
   * @param args error message or throwable message
   * @throws ApiException runtime exception
   */
  public void throwFatalError(final Logger log, Throwable e, Object... args) {
    throwFatalError(log, e, null, args);
  }

}
