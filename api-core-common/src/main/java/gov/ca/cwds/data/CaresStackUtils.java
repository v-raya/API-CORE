package gov.ca.cwds.data;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaresStackUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(CaresStackUtils.class);

  public static void logStack() {
    try {
      final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
      Arrays.stream(stack, 0, stack.length - 1)
          .filter(e -> e.getClassName().startsWith("gov.ca.cwds"))
          .forEach(e -> LOGGER.info("\t{}", e));
    } catch (Exception e) {
      throw e;
    }
  }

}
