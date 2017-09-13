package gov.ca.cwds.rest.exception;

import gov.ca.cwds.rest.exception.mapper.CustomJerseyViolationExceptionMapper;
import gov.ca.cwds.rest.exception.mapper.CustomJsonProcessingExceptionMapper;
import javax.ws.rs.ext.ExceptionMapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * @author CWDS CALS API Team
 */

public class CustomExceptionMapperBinder extends AbstractBinder {

  private final boolean showDetails;

  public CustomExceptionMapperBinder(boolean showDetails) {
    this.showDetails = showDetails;
  }

  @Override
  protected void configure() {
    bind(new CustomJerseyViolationExceptionMapper()).to(ExceptionMapper.class);
    bind(new CustomJsonProcessingExceptionMapper(showDetails)).to(ExceptionMapper.class);
  }

}
