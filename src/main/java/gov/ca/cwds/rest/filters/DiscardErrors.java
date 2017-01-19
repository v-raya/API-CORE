package gov.ca.cwds.rest.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// @Provider
public class DiscardErrors implements ContainerResponseFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(DiscardErrors.class);

  private String basePath;

  public DiscardErrors() {

  }

  public DiscardErrors(String basePath) {
    this.basePath = basePath;
  }

  /*
   * TODO User story #129093035 fix the Entity response status.
   */
  @Override
  public void filter(ContainerRequestContext containerRequestContext,
      ContainerResponseContext containerResponseContext) throws IOException {
    LOGGER.debug("*Comi***n**g**** **************** ");

    if (containerResponseContext.getStatus() != 200) {
      LOGGER.error("response status={}", containerResponseContext.getStatus());
      containerResponseContext.setStatus(500); // Force 200 status code
      containerResponseContext.setEntity("My Own Data"); // Empty body
    }

  }

}
