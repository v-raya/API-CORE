package gov.ca.cwds.rest.validation;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class LogicalIdLovValidationTest {
  LogicalIdLovValidation validation;
  SystemCode systemCode;
  String logicalId = "123";

  @Before
  public void setup(){
    Set systemCodes = new HashSet<SystemCode>();
    validation = new LogicalIdLovValidation(systemCodes);

    systemCode = mock(SystemCode.class);
  }

  @Test
  public void shouldReturnFalseWhenLogicalIdIsNotAString(){
    Integer logicalId = new Integer(4);
    assertFalse(validation.isValidCode(logicalId, systemCode));
  }

  @Test
  public void shouldReturnFalseWhenLogicalIdIsNull(){
    assertFalse(validation.isValidCode(null, systemCode));
  }

  @Test
  public void shouldReturnTrueWhenLogicalIdIisActiveAndSystemCodeHasSameLogicalId(){
    when(systemCode.getLogicalId()).thenReturn(logicalId);
    when(systemCode.getInactiveIndicator()).thenReturn("N");
    assertTrue(validation.isValidCode(logicalId, systemCode));
  }

  @Test
  public void shouldReturnFalseWhenLogicalIdIsInActiveAndSystemCodeHasSameLogicalId(){
    when(systemCode.getLogicalId()).thenReturn(logicalId);
    when(systemCode.getInactiveIndicator()).thenReturn("Y");
    assertFalse(validation.isValidCode(logicalId, systemCode));
  }

  @Test
  public void shouldReturnTrueWhenLogicalIdIisActiveAndSystemCodeHasDifferntLogicalId(){
    when(systemCode.getLogicalId()).thenReturn("WrongId");
    when(systemCode.getInactiveIndicator()).thenReturn("N");
    assertFalse(validation.isValidCode(logicalId, systemCode));
  }
}