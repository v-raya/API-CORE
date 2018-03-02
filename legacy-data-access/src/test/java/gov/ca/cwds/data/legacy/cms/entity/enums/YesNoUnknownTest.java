package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.YesNoUnknown.NO;
import static gov.ca.cwds.data.legacy.cms.entity.enums.YesNoUnknown.UNKNOWN;
import static gov.ca.cwds.data.legacy.cms.entity.enums.YesNoUnknown.YES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import gov.ca.cwds.data.legacy.cms.entity.enums.YesNoUnknown.YesNoUnknownConverter;
import org.junit.Before;
import org.junit.Test;

public class YesNoUnknownTest {

  private YesNoUnknownConverter converter;

  @Before
  public void before() {
    converter = new YesNoUnknownConverter();
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
