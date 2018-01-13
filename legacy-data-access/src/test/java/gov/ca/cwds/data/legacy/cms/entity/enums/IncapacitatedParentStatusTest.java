package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.IncapacitatedParentStatus.NO;
import static gov.ca.cwds.data.legacy.cms.entity.enums.IncapacitatedParentStatus.NOT_APPLICABLE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.IncapacitatedParentStatus.UNKNOWN;
import static gov.ca.cwds.data.legacy.cms.entity.enums.IncapacitatedParentStatus.YES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import gov.ca.cwds.data.legacy.cms.entity.enums.IncapacitatedParentStatus.IncapacitatedParentStatusConverter;
import org.junit.Before;
import org.junit.Test;

public class IncapacitatedParentStatusTest {

  private IncapacitatedParentStatusConverter converter;

  @Before
  public void before() {
    converter = new IncapacitatedParentStatusConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals("N", NO.getCode());
    assertEquals("NA", NOT_APPLICABLE.getCode());
    assertEquals("U", UNKNOWN.getCode());
    assertEquals("Y", YES.getCode());
  }

  @Test
  public void testGetDescription() {
    assertEquals("No", NO.getDescription());
    assertEquals("Not Applicable, not a parent", NOT_APPLICABLE.getDescription());
    assertEquals("Unknown, can not be determine at this time", UNKNOWN.getDescription());
    assertEquals("Yes", YES.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals("N", converter.convertToDatabaseColumn(NO));
    assertEquals("NA", converter.convertToDatabaseColumn(NOT_APPLICABLE));
    assertEquals("U", converter.convertToDatabaseColumn(UNKNOWN));
    assertEquals("Y", converter.convertToDatabaseColumn(YES));
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertNull(converter.convertToEntityAttribute(""));
    assertNull(converter.convertToEntityAttribute(" "));
    assertNull(converter.convertToEntityAttribute("  "));
    assertEquals(NO, converter.convertToEntityAttribute("N"));
    assertEquals(NOT_APPLICABLE, converter.convertToEntityAttribute("NA"));
    assertEquals(UNKNOWN, converter.convertToEntityAttribute("U"));
    assertEquals(YES, converter.convertToEntityAttribute("Y"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute("@");
  }
}
