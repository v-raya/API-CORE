package cwds.rest.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import gov.ca.cwds.rest.validation.AbstractBeanValidator;
import gov.ca.cwds.rest.validation.ValidationException;
import org.hamcrest.junit.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class AbstractBeanValidatorTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private AbstractBeanValidator abstractBeanValidator = new AbstractBeanValidator() {};

  private String property = "somevalue";

  @Test
  public void readBeanValueReturnsCorrectValue() throws Exception {
    assertThat(abstractBeanValidator.readBeanValue(this, "property"), is(equalTo("somevalue")));
  }

  @Test
  public void readBeanValueThrowsValidationExceptionWhenReflectionIssue() throws Exception {
    thrown.expect(ValidationException.class);
    abstractBeanValidator.readBeanValue(this, "foobar");
  }

  /**
   * @return the property
   */
  public String getProperty() {
    return property;
  }

}
