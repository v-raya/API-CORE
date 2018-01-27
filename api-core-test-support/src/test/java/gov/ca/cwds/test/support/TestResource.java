package gov.ca.cwds.test.support;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author CWDS CALS API Team
 */
@Path("test")
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {

  @GET
  public String get() {
    return "Ok";
  }

}
