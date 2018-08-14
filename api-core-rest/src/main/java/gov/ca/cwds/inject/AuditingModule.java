package gov.ca.cwds.inject;

import com.google.inject.AbstractModule;

import gov.ca.cwds.logging.AuditLogger;
import gov.ca.cwds.logging.AuditLoggerImpl;
import gov.ca.cwds.logging.LoggingContext;
import gov.ca.cwds.logging.MDCLoggingContext;

/**
 * DI for Auditing classes.
 * 
 * @author CWDS API Team
 */
public class AuditingModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(AuditLogger.class).to(AuditLoggerImpl.class);
    bind(LoggingContext.class).to(MDCLoggingContext.class);
  }

}
