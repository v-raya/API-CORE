package gov.ca.cwds.logging;

import java.util.UUID;

import org.slf4j.MDC;

/**
 * MDC based logging context.
 * 
 * @author CWDS API Team
 */
public class MDCLoggingContext implements LoggingContext {

  public MDCLoggingContext() {
    // default ctor
  }

  @Override
  public String initialize() {
    String uniqueId = UUID.randomUUID().toString();
    MDC.put(LogParameter.UNIQUE_ID.name(), uniqueId);
    return uniqueId;
  }

  @Override
  public void close() {
    MDC.clear();
  }

  @Override
  public void setLogParameter(LogParameter logParam, String value) {
    MDC.put(logParam.name(), value);
  }

  @Override
  public String getLogParameter(LogParameter logParam) {
    return MDC.get(logParam.name());
  }

  @Override
  public String getUniqueId() {
    return getLogParameter(LogParameter.UNIQUE_ID);
  }

}
