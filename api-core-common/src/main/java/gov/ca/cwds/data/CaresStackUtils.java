package gov.ca.cwds.data;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Find annoying bugs -- especially those nasty ones planted by your co-workers.
 * 
 * @author CWDS API Team
 */
public class CaresStackUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(CaresStackUtils.class);

  public static void logStack() {
    if (LOGGER.isTraceEnabled()) {
      try {
        final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        Arrays.stream(stack, 0, stack.length - 1)
            .filter(e -> e.getClassName().startsWith("gov.ca.cwds")
                && !e.getClassName().startsWith("gov.ca.cwds.rest.filters")
                && !e.getClassName().contains("$$"))
            .forEach(e -> LOGGER.info("\t{}", e));
      } catch (Exception e) {
        throw e;
      }
    }
  }

  public static String stackToString() {
    StringBuilder buf = new StringBuilder();
    try {
      final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
      Arrays.stream(stack, 0, stack.length - 1)
          .filter(e -> e.getClassName().startsWith("gov.ca.cwds")
              && !e.getClassName().startsWith("gov.ca.cwds.rest.filters")
              && !e.getClassName().contains("$$"))
          .forEach(e -> buf.append('\n').append(e));
    } catch (Exception e) {
      throw e;
    }

    return buf.toString();
  }

  public static StackTraceElement[] getStackTrace() {
    try {
      final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
      return Arrays.stream(stack, 0, stack.length - 1)
          .filter(e -> e.getClassName().startsWith("gov.ca.cwds")
              && !e.getClassName().startsWith("gov.ca.cwds.rest.filters")
              && !e.getClassName().contains("$$"))
          .collect(Collectors.toList()).toArray(new StackTraceElement[0]);
    } catch (Exception e) {
      throw e;
    }
  }

}
