package gov.ca.cwds.rest.exception.mapper;

import javax.ws.rs.core.Response;

import org.apache.shiro.authz.AuthorizationException;
import org.secnod.shiro.jaxrs.ShiroExceptionMapper;

import gov.ca.cwds.rest.exception.IssueType;

/**
 * @author CWDS API Team
 */
public class ApiSecurityExceptionMapper extends ShiroExceptionMapper {

  @Override
  public Response toResponse(AuthorizationException exception) {
    int status = super.toResponse(exception).getStatus();
    return ExceptionMapperUtils.createGenericResponse(exception, IssueType.SECURITY_EXCEPTION,
        status, null);
  }
}
