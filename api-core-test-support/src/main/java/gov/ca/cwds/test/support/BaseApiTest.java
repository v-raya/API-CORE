package gov.ca.cwds.test.support;

import org.junit.After;
import org.junit.Rule;

import io.dropwizard.Configuration;

/**
 * @author CWDS CALS API Team
 */
public abstract class BaseApiTest<T extends Configuration> {

  protected abstract BaseDropwizardApplication<T> getApplication();

  @SuppressWarnings("unchecked")
  @Rule
  public RestClientTestRule clientTestRule = new RestClientTestRule(getApplication());

  public String transformDTOtoJSON(Object o) throws Exception {
    return clientTestRule.getMapper().writeValueAsString(o);
  }

  @After
  public void tearDown() throws Exception {}

}
