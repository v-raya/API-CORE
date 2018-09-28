package gov.ca.cwds.rest.resources;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;

import com.google.inject.Inject;

import gov.ca.cwds.rest.SwaggerConfiguration;

/**
 * @author TPT-2 team
 */
@Path(value = "token")
@Produces(MediaType.TEXT_PLAIN)
public class TokenResource {

  private SwaggerConfiguration swaggerConfiguration;
  private JerseyClient client;

  @Inject
  public TokenResource(SwaggerConfiguration swaggerConfiguration) {
    this.swaggerConfiguration = swaggerConfiguration;
  }

  @GET
  public String getToken(@QueryParam("accessCode") String accessCode) {
    URI uri = UriBuilder.fromUri(swaggerConfiguration.getTokenUrl())
        .queryParam("accessCode", accessCode).build();
    return getClient().target(uri).request().get(String.class);
  }

  private synchronized Client getClient() {
    if (client == null) {
      JerseyClientBuilder clientBuilder =
          new JerseyClientBuilder().property(ClientProperties.CONNECT_TIMEOUT, 5000)
              .property(ClientProperties.READ_TIMEOUT, 30000)
              // Just ignore host verification, client will call trusted resources only
              .hostnameVerifier((hostName, sslSession) -> true);
      this.client = clientBuilder.build();
    }
    return client;
  }

}
