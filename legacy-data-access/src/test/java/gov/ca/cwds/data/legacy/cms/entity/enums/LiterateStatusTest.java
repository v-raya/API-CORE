package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.LiterateStatus.NOT_APPLICABLE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.LiterateStatus.ILLITERATE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.LiterateStatus.UNKNOWN;
import static gov.ca.cwds.data.legacy.cms.entity.enums.LiterateStatus.LITERATE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import gov.ca.cwds.data.legacy.cms.entity.enums.LiterateStatus.LiterateStatusConverter;
import org.junit.Before;
import org.junit.Test;

public class LiterateStatusTest {

  private LiterateStatusConverter converter;

  @Before
  public void before() {
    converter = new LiterateStatusConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals("D", NOT_APPLICABLE.getCode());
    assertEquals("N", ILLITERATE.getCode());
    assertEquals("U", UNKNOWN.getCode());
    assertEquals("Y", LITERATE.getCode());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Not Applicable", NOT_APPLICABLE.getDescription());
    assertEquals("Illiterate", ILLITERATE.getDescription());
    assertEquals("Unknown", UNKNOWN.getDescription());
    assertEquals("Literate", LITERATE.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals("D", converter.convertToDatabaseColumn(NOT_APPLICABLE));
    assertEquals("N", converter.convertToDatabaseColumn(ILLITERATE));
    assertEquals("U", converter.convertToDatabaseColumn(UNKNOWN));
    assertEquals("Y", converter.convertToDatabaseColumn(LITERATE));
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertNull(converter.convertToEntityAttribute(" "));
    assertEquals(NOT_APPLICABLE, converter.convertToEntityAttribute("D"));
    assertEquals(ILLITERATE, converter.convertToEntityAttribute("N"));
    assertEquals(UNKNOWN, converter.convertToEntityAttribute("U"));
    assertEquals(LITERATE, converter.convertToEntityAttribute("Y"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute("@");
  }
}
