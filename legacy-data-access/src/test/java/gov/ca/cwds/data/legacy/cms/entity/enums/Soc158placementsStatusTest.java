package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.Soc158placementsStatus.MIGRATED_TO_CLIENT_SERVICES;
import static gov.ca.cwds.data.legacy.cms.entity.enums.Soc158placementsStatus.NOT_YET_MIGRATED;
import static gov.ca.cwds.data.legacy.cms.entity.enums.Soc158placementsStatus.NO_SOC_158_PLACEMENTS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import gov.ca.cwds.data.legacy.cms.entity.enums.Soc158placementsStatus.Soc158placementsStatusConverter;
import org.junit.Before;
import org.junit.Test;

public class Soc158placementsStatusTest {

  private Soc158placementsStatusConverter converter;

  @Before
  public void before() {
    converter = new Soc158placementsStatusConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals("M", MIGRATED_TO_CLIENT_SERVICES.getCode());
    assertEquals("N", NO_SOC_158_PLACEMENTS.getCode());
    assertEquals("Y", NOT_YET_MIGRATED.getCode());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Migrated SOC 158 Placements to Client Services", MIGRATED_TO_CLIENT_SERVICES.getDescription());
    assertEquals("No SOC 158 Placements", NO_SOC_158_PLACEMENTS.getDescription());
    assertEquals("SOC 158 Placements not yet migrated", NOT_YET_MIGRATED.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals("M", converter.convertToDatabaseColumn(MIGRATED_TO_CLIENT_SERVICES));
    assertEquals("N", converter.convertToDatabaseColumn(NO_SOC_158_PLACEMENTS));
    assertEquals("Y", converter.convertToDatabaseColumn(NOT_YET_MIGRATED));
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertNull(converter.convertToEntityAttribute(" "));
    assertEquals(MIGRATED_TO_CLIENT_SERVICES, converter.convertToEntityAttribute("M"));
    assertEquals(NO_SOC_158_PLACEMENTS, converter.convertToEntityAttribute("N"));
    assertEquals(NOT_YET_MIGRATED, converter.convertToEntityAttribute("Y"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute("@");
  }
}
