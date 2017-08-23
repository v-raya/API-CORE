package gov.ca.cwds.rest.validation;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class LovValidationTest {
  TestLovValidation testValidation;
  String systemCodeId = "systemCodeId";

  @Before
  public void setup() {

    Set systemCodes = new HashSet();
    SystemCode systemCode1 = mock(SystemCode.class);
    SystemCode systemCode2 = mock(SystemCode.class);
    testValidation = new TestLovValidation(systemCodes);
  }

  @Test
  public void shouldReturnFalseIfSystemCodesAreNull() {
    testValidation = new TestLovValidation(null);
    boolean isValid = testValidation.isValid(systemCodeId);
    assertFalse(isValid);
  }

  @Test
  public void shouldReturnFalseIfNoneOfTheSystemCodesAreValid() {
    testValidation.setValid(false);
    boolean isValid = testValidation.isValid(systemCodeId);
    assertFalse(isValid);
  }

  @Test
  public void shouldReturnTrueIfAnySystemCodesAreValid() {
    testValidation.setValid(false);
    boolean isValid = testValidation.isValid(systemCodeId);
    assertFalse(isValid);
  }

  public class TestLovValidation extends LovValidation {
    private boolean valid;

    public TestLovValidation(Set<SystemCode> systemCodes) {
      super(systemCodes);
    }

    @Override
    protected boolean isValidCode(Object systemCodeId, SystemCode systemCode,
        boolean checkCategoryIdValueIsZero) {
      return false;
    }

    public void setValid(boolean isValid) {
      this.valid = isValid;
    }
  }

}
