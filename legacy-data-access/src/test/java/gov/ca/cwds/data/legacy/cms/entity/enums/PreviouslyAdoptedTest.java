package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.PreviouslyAdopted.NO;
import static gov.ca.cwds.data.legacy.cms.entity.enums.PreviouslyAdopted.UNKNOWN;
import static gov.ca.cwds.data.legacy.cms.entity.enums.PreviouslyAdopted.NO_USER_SELECTION;
import static gov.ca.cwds.data.legacy.cms.entity.enums.PreviouslyAdopted.YES;
import static gov.ca.cwds.data.legacy.cms.entity.enums.PreviouslyAdopted.PreviouslyAdoptedConverter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class PreviouslyAdoptedTest {

  private PreviouslyAdoptedConverter converter;

  @Before
  public void before() {
    converter = new PreviouslyAdoptedConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals('N', NO.getCode().charValue());
    assertEquals('U', UNKNOWN.getCode().charValue());
    assertEquals('X', NO_USER_SELECTION.getCode().charValue());
    assertEquals('Y', YES.getCode().charValue());
  }

  @Test
  public void testGetDescription() {
    assertEquals("No", NO.getDescription());
    assertEquals("Unknown", UNKNOWN.getDescription());
    assertEquals("No User Selection", NO_USER_SELECTION.getDescription());
    assertEquals("Eligible", YES.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals('N', converter.convertToDatabaseColumn(NO).charValue());
    assertEquals('U', converter.convertToDatabaseColumn(UNKNOWN).charValue());
    assertEquals('X', converter.convertToDatabaseColumn(NO_USER_SELECTION).charValue());
    assertEquals('Y', converter.convertToDatabaseColumn(YES).charValue());
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertEquals(NO, converter.convertToEntityAttribute('N'));
    assertEquals(UNKNOWN, converter.convertToEntityAttribute('U'));
    assertEquals(NO_USER_SELECTION, converter.convertToEntityAttribute('X'));
    assertEquals(YES, converter.convertToEntityAttribute('Y'));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute('@');
  }
}
