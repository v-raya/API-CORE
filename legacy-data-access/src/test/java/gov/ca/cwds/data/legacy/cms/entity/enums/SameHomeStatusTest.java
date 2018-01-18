package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.SameHomeStatus.NO;
import static gov.ca.cwds.data.legacy.cms.entity.enums.SameHomeStatus.UNKNOWN;
import static gov.ca.cwds.data.legacy.cms.entity.enums.SameHomeStatus.YES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import gov.ca.cwds.data.legacy.cms.entity.enums.Gender.GenderConverter;
import gov.ca.cwds.data.legacy.cms.entity.enums.SameHomeStatus.SameHomeStatusConverter;
import org.junit.Before;
import org.junit.Test;

public class SameHomeStatusTest {

  private SameHomeStatusConverter converter;

  @Before
  public void before() {
    converter = new SameHomeStatusConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals("N", NO.getCode());
    assertEquals("U", UNKNOWN.getCode());
    assertEquals("Y", YES.getCode());
  }

  @Test
  public void testGetDescription() {
    assertEquals("No", NO.getDescription());
    assertEquals("Unknown", UNKNOWN.getDescription());
    assertEquals("Yes", YES.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals("N", converter.convertToDatabaseColumn(NO));
    assertEquals("U", converter.convertToDatabaseColumn(UNKNOWN));
    assertEquals("Y", converter.convertToDatabaseColumn(YES));
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertNull(converter.convertToEntityAttribute(" "));
    assertEquals(NO, converter.convertToEntityAttribute("N"));
    assertEquals(UNKNOWN, converter.convertToEntityAttribute("U"));
    assertEquals(YES, converter.convertToEntityAttribute("Y"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute("@");
  }
}
