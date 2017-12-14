package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity.NOT_APPLICABLE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity.SEALED;
import static gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity.SENSITIVE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity.SensitivityConverter;
import org.junit.Before;
import org.junit.Test;

public class SensitivityTest {

  private SensitivityConverter converter;

  @Before
  public void before() {
    converter = new SensitivityConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals('N', NOT_APPLICABLE.getCode().charValue());
    assertEquals('R', SEALED.getCode().charValue());
    assertEquals('S', SENSITIVE.getCode().charValue());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Not Applicable", NOT_APPLICABLE.getDescription());
    assertEquals("This specifies a SEALED Case", SEALED.getDescription());
    assertEquals("This specifies a SENSITIVE Case", SENSITIVE.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals('N', converter.convertToDatabaseColumn(NOT_APPLICABLE).charValue());
    assertEquals('R', converter.convertToDatabaseColumn(SEALED).charValue());
    assertEquals('S', converter.convertToDatabaseColumn(SENSITIVE).charValue());
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertNull(converter.convertToEntityAttribute(' '));
    assertEquals(NOT_APPLICABLE, converter.convertToEntityAttribute('N'));
    assertEquals(SEALED, converter.convertToEntityAttribute('R'));
    assertEquals(SENSITIVE, converter.convertToEntityAttribute('S'));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute('@');
  }
}
