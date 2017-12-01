package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.Disability.NOT_YET_DETERMINED;
import static gov.ca.cwds.data.legacy.cms.entity.enums.Disability.NO;
import static gov.ca.cwds.data.legacy.cms.entity.enums.Disability.YES;
import static gov.ca.cwds.data.legacy.cms.entity.enums.Disability.DisabilityConverter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class DisabilityTest {

  private DisabilityConverter converter;

  @Before
  public void before() {
    converter = new DisabilityConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals('D', NOT_YET_DETERMINED.getCode().charValue());
    assertEquals('N', NO.getCode().charValue());
    assertEquals('Y', YES.getCode().charValue());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Not Yet Determined", NOT_YET_DETERMINED.getDescription());
    assertEquals("No", NO.getDescription());
    assertEquals("Yes", YES.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals('D', converter.convertToDatabaseColumn(NOT_YET_DETERMINED).charValue());
    assertEquals('N', converter.convertToDatabaseColumn(NO).charValue());
    assertEquals('Y', converter.convertToDatabaseColumn(YES).charValue());
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertEquals(NOT_YET_DETERMINED, converter.convertToEntityAttribute('D'));
    assertEquals(NO, converter.convertToEntityAttribute('N'));
    assertEquals(YES, converter.convertToEntityAttribute('Y'));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute('@');
  }
}
