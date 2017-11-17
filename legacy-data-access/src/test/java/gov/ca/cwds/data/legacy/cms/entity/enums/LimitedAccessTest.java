package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.LimitedAccess.NO_RESTRICTION;
import static gov.ca.cwds.data.legacy.cms.entity.enums.LimitedAccess.SEALED;
import static gov.ca.cwds.data.legacy.cms.entity.enums.LimitedAccess.SENSITIVE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.LimitedAccess.LimitedAccessConverter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class LimitedAccessTest {

  private LimitedAccessConverter converter;

  @Before
  public void before() {
    converter = new LimitedAccessConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals('N', NO_RESTRICTION.getCode().charValue());
    assertEquals('R', SEALED.getCode().charValue());
    assertEquals('S', SENSITIVE.getCode().charValue());
  }

  @Test
  public void testGetDescription() {
    assertEquals("N/A", NO_RESTRICTION.getDescription());
    assertEquals("Sealed", SEALED.getDescription());
    assertEquals("Sensitive", SENSITIVE.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals('N', converter.convertToDatabaseColumn(NO_RESTRICTION).charValue());
    assertEquals('R', converter.convertToDatabaseColumn(SEALED).charValue());
    assertEquals('S', converter.convertToDatabaseColumn(SENSITIVE).charValue());
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertEquals(NO_RESTRICTION, converter.convertToEntityAttribute('N'));
    assertEquals(SEALED, converter.convertToEntityAttribute('R'));
    assertEquals(SENSITIVE, converter.convertToEntityAttribute('S'));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute(' ');
  }
}
