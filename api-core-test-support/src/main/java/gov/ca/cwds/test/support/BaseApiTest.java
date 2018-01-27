package gov.ca.cwds.test.support;

import io.dropwizard.Configuration;
import org.junit.After;
import org.junit.Rule;

/**
 * @author CWDS CALS API Team
 */
public abstract class BaseApiTest<T extends Configuration> {

  protected abstract BaseDropwizardApplication<T> getApplication();

    @Rule
  public RestClientTestRule clientTestRule = new RestClientTestRule(getApplication());

  @After
  public void tearDown() throws Exception {
  }

}
