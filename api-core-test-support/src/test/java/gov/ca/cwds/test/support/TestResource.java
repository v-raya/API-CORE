package gov.ca.cwds.test.support;

import gov.ca.cwds.security.utils.PrincipalUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;

/**
 * @author CWDS CALS API Team
 */
@Path("test")
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {

  @GET
  public String checkDefaultPrincipal() {
    assertEquals("000", PrincipalUtils.getPrincipal().getStaffId());
    return "Ok";
  }

  @GET
  @Path("/custom")
  public String checkCustomPrincipal() {
    assertEquals("Custom", PrincipalUtils.getPrincipal().getStaffId());
    return "Ok";
  }


}
