package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.Adoptable.NOT_ADOPTABLE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.Adoptable.NOT_ASSESSED;
import static gov.ca.cwds.data.legacy.cms.entity.enums.Adoptable.ADOPTABLE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.Adoptable.AdoptableConverter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class AdoptableTest {

  private AdoptableConverter converter;

  @Before
  public void before() {
    converter = new AdoptableConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals("N", NOT_ADOPTABLE.getCode());
    assertEquals("NA", NOT_ASSESSED.getCode());
    assertEquals("Y", ADOPTABLE.getCode());
  }

  @Test
  public void testGetDescription() {
    assertEquals("No", NOT_ADOPTABLE.getDescription());
    assertEquals("NA", NOT_ASSESSED.getDescription());
    assertEquals("Yes", ADOPTABLE.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals("N", converter.convertToDatabaseColumn(NOT_ADOPTABLE));
    assertEquals("NA", converter.convertToDatabaseColumn(NOT_ASSESSED));
    assertEquals("Y", converter.convertToDatabaseColumn(ADOPTABLE));
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertNull(converter.convertToEntityAttribute(""));
    assertNull(converter.convertToEntityAttribute(" "));
    assertNull(converter.convertToEntityAttribute("  "));
    assertEquals(NOT_ADOPTABLE, converter.convertToEntityAttribute("N"));
    assertEquals(NOT_ASSESSED, converter.convertToEntityAttribute("NA"));
    assertEquals(ADOPTABLE, converter.convertToEntityAttribute("Y"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute("@");
  }
}
