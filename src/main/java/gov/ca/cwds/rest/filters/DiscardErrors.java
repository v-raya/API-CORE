package gov.ca.cwds.rest.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class DiscardErrors implements ContainerResponseFilter {
  private String basePath;

  public DiscardErrors() {

  }

  public DiscardErrors(String basePath) {
    this.basePath = basePath;
  }

  /*
   * TODO User story #129093035 fix the Entity response
   * 
   */
  @Override
  public void filter(ContainerRequestContext containerRequestContext,
      ContainerResponseContext containerResponseContext) throws IOException {
    System.out.println("*Comi***n**g**** **************** ");
    if (containerResponseContext.getStatus() != 200) {
      containerResponseContext.setStatus(500); // Force 200 status code
      containerResponseContext.setEntity("My Own Data"); // Empty body
    }

  }

}
