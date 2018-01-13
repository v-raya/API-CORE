package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.ParentUnemployedStatus.NO;
import static gov.ca.cwds.data.legacy.cms.entity.enums.ParentUnemployedStatus.NOT_APPLICABLE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.ParentUnemployedStatus.UNKNOWN;
import static gov.ca.cwds.data.legacy.cms.entity.enums.ParentUnemployedStatus.YES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import gov.ca.cwds.data.legacy.cms.entity.enums.ParentUnemployedStatus.ParentUnemployedStatusConverter;
import org.junit.Before;
import org.junit.Test;

public class ParentUnemployedStatusTest {

  private ParentUnemployedStatusConverter converter;

  @Before
  public void before() {
    converter = new ParentUnemployedStatusConverter();
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
