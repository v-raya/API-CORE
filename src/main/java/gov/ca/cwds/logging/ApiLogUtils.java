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

  // private final ApiSupplier<E> supplier = new ApiSupplier<>(this::newInstance);

  static class Typinator<E> {
    @SuppressWarnings("serial")
    public final TypeToken<E> type = new TypeToken<E>(getClass()) {};
  }

  private final Typinator<E> typed;

  public ApiLogUtils() {
    this.typed = new Typinator<>();
  }

  @SuppressWarnings("unchecked")
  public Class<E> getTypeParameterClass() {
    Type type = getClass().getGenericSuperclass();
    ParameterizedType paramType = (ParameterizedType) type;
    return (Class<E>) paramType.getActualTypeArguments()[0];
  }

  protected E newInstance() {
    E ret;
    try {
      ret = this.getTypeParameterClass().newInstance();
    } catch (Exception e) {
      throw new ApiException(e);
    }

    return ret;
  }

  protected E newInstance(String msg, Throwable t) {
    E ret = null;
    // final Typinator<E> typinator = new Typinator<E>() {};
    final Class<?> klazz = typed.type.getRawType();
    typed.type.getType();
    typed.type.getTypes();
    typed.type.getSubtype(RuntimeException.class);
    // typinator.type.getSubtype(RuntimeException.class); // bombs
    // final Class<?> klazz2 = typinator.type.getSubtype(RuntimeException.class).getRawType();
    final Class<?> class1 = getTypeParameterClass();
    try {
      ret =
          (E) klazz.getConstructor(new Class[] {String.class, Throwable.class}).newInstance(msg, t);
    } catch (Exception e) {
      throw new ApiException("Failed to construct type " + klazz.getName(), e);
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
  public void throwFatalError(final Logger log, Throwable t, String pattern, Object... args) {
    final Object[] objs = ArrayUtils.isEmpty(args) ? new Object[0] : args;
    final String pat = !StringUtils.isEmpty(pattern) ? pattern : StringUtils.join(objs, "{}");
    final String msg = MessageFormat.format(pat, objs);
    final Logger logger = log != null ? log : LOGGER;
    logger.fatal(msg, t);
    throw newInstance(msg, t);
  }

  /**
   * Convenience overload of {@link #throwFatalError(Logger, Throwable, String, Object...)}. Format
   * message and throw a runtime {@link ApiException}.
   * 
   * @param log class logger
   * @param t any Throwable
   * @param args error message or throwable message
   * @throws ApiException runtime exception
   */
  public void throwFatalError(final Logger log, Throwable t, Object... args) {
    throwFatalError(log, t, null, args);
  }

}
