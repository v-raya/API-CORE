package gov.ca.cwds.rest.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class SystemCodeIdLovLValidationTest {
  SystemCodeIdLovLValidation validation;
  SystemCode systemCode;
  Short systemCodeId = 123;
  Short wrongSystemId = 999;
  boolean checkCategoryIdValueIsZero;

  @Before
  public void setup() {
    Set systemCodes = new HashSet<SystemCode>();
    validation = new SystemCodeIdLovLValidation(systemCodes);

    systemCode = mock(SystemCode.class);
    checkCategoryIdValueIsZero = false;
  }

  @Test
  public void shouldReturnFalseWhenSystemCodeIdIsNotAString() {
    Integer systemCodeId = new Integer(4);
    assertFalse(validation.isValidCode(systemCodeId, systemCode, checkCategoryIdValueIsZero));
  }

  @Test
  public void shouldReturnFalseWhenSystemCodeIdIsNull() {
    assertFalse(validation.isValidCode(null, systemCode, checkCategoryIdValueIsZero));
  }

  @Test
  public void shouldReturnTrueWhenSystemCodeIdIsActiveAndSystemCodeHasSameSystemCodeId() {
    when(systemCode.getSystemId()).thenReturn(systemCodeId);
    when(systemCode.getInactiveIndicator()).thenReturn("N");
    assertTrue(validation.isValidCode(systemCodeId, systemCode, checkCategoryIdValueIsZero));
  }

  @Test
  public void shouldReturnFalseWhenSystemCodeIdIsInActiveAndSystemCodeHasSameSystemCodeId() {
    when(systemCode.getSystemId()).thenReturn(systemCodeId);
    when(systemCode.getInactiveIndicator()).thenReturn("Y");
    assertFalse(validation.isValidCode(systemCodeId, systemCode, checkCategoryIdValueIsZero));
  }

  @Test
  public void shouldReturnTrueWhenSystemCodeIdIsActiveAndSystemCodeHasDifferntSystemCodeId() {
    when(systemCode.getSystemId()).thenReturn(wrongSystemId);
    when(systemCode.getInactiveIndicator()).thenReturn("N");
    assertFalse(validation.isValidCode(systemCodeId, systemCode, checkCategoryIdValueIsZero));
  }

}
