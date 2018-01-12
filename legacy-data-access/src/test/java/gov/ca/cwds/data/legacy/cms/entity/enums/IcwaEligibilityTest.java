package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.IcwaEligibility.NOT_ELIGIBLE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.IcwaEligibility.PENDING;
import static gov.ca.cwds.data.legacy.cms.entity.enums.IcwaEligibility.UNKNOWN;
import static gov.ca.cwds.data.legacy.cms.entity.enums.IcwaEligibility.ELIGIBLE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.IcwaEligibility.IcwaEligibilityConverter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class IcwaEligibilityTest {

  private IcwaEligibilityConverter converter;

  @Before
  public void before() {
    converter = new IcwaEligibilityConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals("N", NOT_ELIGIBLE.getCode());
    assertEquals("P", PENDING.getCode());
    assertEquals("U", UNKNOWN.getCode());
    assertEquals("Y", ELIGIBLE.getCode());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Not Eligible", NOT_ELIGIBLE.getDescription());
    assertEquals("Pending", PENDING.getDescription());
    assertEquals("Not asked, unknown", UNKNOWN.getDescription());
    assertEquals("Eligible", ELIGIBLE.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals("N", converter.convertToDatabaseColumn(NOT_ELIGIBLE));
    assertEquals("P", converter.convertToDatabaseColumn(PENDING));
    assertEquals("U", converter.convertToDatabaseColumn(UNKNOWN));
    assertEquals("Y", converter.convertToDatabaseColumn(ELIGIBLE));
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertEquals(NOT_ELIGIBLE, converter.convertToEntityAttribute("N"));
    assertEquals(PENDING, converter.convertToEntityAttribute("P"));
    assertEquals(UNKNOWN, converter.convertToEntityAttribute("U"));
    assertEquals(ELIGIBLE, converter.convertToEntityAttribute("Y"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute("@");
  }
}
