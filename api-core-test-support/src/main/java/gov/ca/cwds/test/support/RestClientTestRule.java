package gov.ca.cwds.test.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import io.dropwizard.Configuration;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * @author CWDS CALS API Team
 */
public class RestClientTestRule<T extends Configuration> implements TestRule {

  private static final Logger LOG = LoggerFactory.getLogger(RestClientTestRule.class);

  private final DropwizardAppRule<T> dropWizardApplication;

  private Client client;
  private ObjectMapper mapper;

  public RestClientTestRule(DropwizardAppRule<T> dropWizardApplication) {
    this.dropWizardApplication = dropWizardApplication;
  }

  public WebTarget target(String pathInfo) {
    String restUrl = getUriString() + pathInfo;
    return client.target(restUrl);
  }

  private String composeUriString() {
    return String.format("http://localhost:%s/", dropWizardApplication.getLocalPort());
  }

  protected String getUriString() {
    return isRemoteMode()? getRemoteUrl(): composeUriString();
  }

  private boolean isRemoteMode() {
    return StringUtils.isNotEmpty(getRemoteUrl());
  }

  protected String getRemoteUrl() {
    return System.getProperty("remote.app.url");
  }

  public ObjectMapper getMapper() {
    return mapper;
  }

  @Override
  public Statement apply(Statement statement, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {

        JerseyClientBuilder clientBuilder = new JerseyClientBuilder()
            .property(ClientProperties.CONNECT_TIMEOUT, 5000)
            .property(ClientProperties.READ_TIMEOUT, 60000)
            .hostnameVerifier((hostName, sslSession) -> {
              // Just ignore host verification for test purposes
              return true;
            });

        client = clientBuilder.build();
        mapper = dropWizardApplication.getObjectMapper();
        client.register(new JacksonJsonProvider(mapper));
        statement.evaluate();
      }
    };
  }
}
