package gov.ca.cwds.auth.clients;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.text.MessageFormat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import gov.ca.cwds.auth.ApiAuthenticationException;
import gov.ca.cwds.auth.InvalidTokenException;
import gov.ca.cwds.auth.PerryShiroToken;

public class PerryClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(PerryClient.class);

  private volatile Client client; // DRS: why volatile?? Used across threads?

  private String perryUrl;
  private String serviceProviderId;

  private synchronized void buildClient() {
    // double null check to ensure that the client only gets built once
    if (client == null) {
      client = ClientBuilder.newClient();
    }
  }

  private Client getClient() {
    if (client == null) {
      buildClient();
    }

    return client;
  }

  public String validateToken(PerryShiroToken token) {
    checkNotNull(token, "Token cannot be null.");
    checkState(!Strings.isNullOrEmpty(perryUrl), "PerryUrl must be set.");

    LOGGER.debug("Requesting token validation on token: {}", token.getToken());
    Response response = null;

    try {
      WebTarget webTarget = getClient().target(perryUrl).queryParam("token", token.getToken());
      if (serviceProviderId != null) {
        webTarget = webTarget.queryParam("sp_id", serviceProviderId);
      }

      response = webTarget.request(MediaType.TEXT_PLAIN_TYPE).get();
    } catch (Exception e) {
      LOGGER.error("Error validating a token.", e);
      throw new ApiAuthenticationException("Error validating a token.", e);
    }

    if (response != null) {
      if (response.getStatus() == 200) {
        final String subject = response.readEntity(String.class);
        LOGGER.debug("Successfully received validation of token: '{}', for subject: '{}'.",
            token.getToken(), subject);
        return subject;
      } else {
        LOGGER.error("Connection to Perry failed with HTTP code: {}.", response.getStatus());
        throw new InvalidTokenException(
            "Connection to Perry failed with HTTP code: " + response.getStatus());
      }
    } else {
      LOGGER.error("Null response validating token: {}", token.getToken());
      throw new ApiAuthenticationException(
          MessageFormat.format("Null response validating token: {0}", token.getToken()));
    }
  }

  public void setPerryUrl(String perryUrl) {
    checkArgument(!Strings.isNullOrEmpty(perryUrl), "PerryUrl cannot be null or empty.");
    this.perryUrl = perryUrl;
  }

  public String getServiceProviderId() {
    return serviceProviderId;
  }

  public void setServiceProviderId(String serviceProviderId) {
    this.serviceProviderId = serviceProviderId;
  }

}
