package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.Gender.FEMALE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.Gender.MALE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.Gender.UNKNOWN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import gov.ca.cwds.data.legacy.cms.entity.enums.Gender.GenderConverter;
import org.junit.Before;
import org.junit.Test;

public class GenderTest {

  private GenderConverter converter;

  @Before
  public void before() {
    converter = new GenderConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals('F', FEMALE.getCode().charValue());
    assertEquals('M', MALE.getCode().charValue());
    assertEquals('U', UNKNOWN.getCode().charValue());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Female", FEMALE.getDescription());
    assertEquals("Male", MALE.getDescription());
    assertEquals("Unknown", UNKNOWN.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals('F', converter.convertToDatabaseColumn(FEMALE).charValue());
    assertEquals('M', converter.convertToDatabaseColumn(MALE).charValue());
    assertEquals('U', converter.convertToDatabaseColumn(UNKNOWN).charValue());
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertNull(converter.convertToEntityAttribute(' '));
    assertEquals(FEMALE, converter.convertToEntityAttribute('F'));
    assertEquals(MALE, converter.convertToEntityAttribute('M'));
    assertEquals(UNKNOWN, converter.convertToEntityAttribute('U'));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute('@');
  }
}
