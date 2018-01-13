package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.AdoptionStatus.TOTALLY_FREE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.AdoptionStatus.PARTIALLY_FREE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.AdoptionStatus.NOT_FREE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.AdoptionStatus.NOT_APPLICABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import gov.ca.cwds.data.legacy.cms.entity.enums.AdoptionStatus.AdoptionStatusConverter;
import org.junit.Before;
import org.junit.Test;

public class AdoptionStatusTest {

  private AdoptionStatusConverter converter;

  @Before
  public void before() {
    converter = new AdoptionStatusConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals("T", TOTALLY_FREE.getCode());
    assertEquals("P", PARTIALLY_FREE.getCode());
    assertEquals("N", NOT_FREE.getCode());
    assertEquals("A", NOT_APPLICABLE.getCode());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Totally Free", TOTALLY_FREE.getDescription());
    assertEquals("Partially Free", PARTIALLY_FREE.getDescription());
    assertEquals("Not Free", NOT_FREE.getDescription());
    assertEquals("Not Applicable", NOT_APPLICABLE.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals("T", converter.convertToDatabaseColumn(TOTALLY_FREE));
    assertEquals("P", converter.convertToDatabaseColumn(PARTIALLY_FREE));
    assertEquals("N", converter.convertToDatabaseColumn(NOT_FREE));
    assertEquals("A", converter.convertToDatabaseColumn(NOT_APPLICABLE));
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertNull(converter.convertToEntityAttribute(" "));
    assertEquals(TOTALLY_FREE, converter.convertToEntityAttribute("T"));
    assertEquals(PARTIALLY_FREE, converter.convertToEntityAttribute("P"));
    assertEquals(NOT_FREE, converter.convertToEntityAttribute("N"));
    assertEquals(NOT_APPLICABLE, converter.convertToEntityAttribute("A"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute("@");
  }
}
