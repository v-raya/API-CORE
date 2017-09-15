package gov.ca.cwds.rest.exception;

import javax.ws.rs.ext.ExceptionMapper;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import gov.ca.cwds.logging.LoggingContext;
import gov.ca.cwds.rest.exception.mapper.CustomJerseyViolationExceptionMapper;
import gov.ca.cwds.rest.exception.mapper.CustomJsonProcessingExceptionMapper;

/**
 * @author CWDS CALS API Team
 */

public class CustomExceptionMapperBinder extends AbstractBinder {

  private LoggingContext loggingContext;
  private final boolean showDetails;

  public CustomExceptionMapperBinder(LoggingContext loggingContext, boolean showDetails) {
    this.loggingContext = loggingContext;
    this.showDetails = showDetails;
  }

  @Override
  protected void configure() {
    bind(new CustomJerseyViolationExceptionMapper(loggingContext)).to(ExceptionMapper.class);
    bind(new CustomJsonProcessingExceptionMapper(loggingContext, showDetails))
        .to(ExceptionMapper.class);
  }
}
