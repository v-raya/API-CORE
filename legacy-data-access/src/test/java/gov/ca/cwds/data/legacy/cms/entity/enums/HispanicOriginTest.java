package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.HispanicOrigin.DECLINES_TO_STATE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.HispanicOrigin.NO;
import static gov.ca.cwds.data.legacy.cms.entity.enums.HispanicOrigin.NO_USER_SELECTION;
import static gov.ca.cwds.data.legacy.cms.entity.enums.HispanicOrigin.UNABLE_TO_DETERMINE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.HispanicOrigin.UNDETERMINED;
import static gov.ca.cwds.data.legacy.cms.entity.enums.HispanicOrigin.YES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import gov.ca.cwds.data.legacy.cms.entity.enums.HispanicOrigin.HispanicOriginConverter;
import org.junit.Before;
import org.junit.Test;

public class HispanicOriginTest {

  private HispanicOriginConverter converter;

  @Before
  public void before() {
    converter = new HispanicOriginConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals("D", DECLINES_TO_STATE.getCode());
    assertEquals("N", NO.getCode());
    assertEquals("U", UNDETERMINED.getCode());
    assertEquals("X", NO_USER_SELECTION.getCode());
    assertEquals("Y", YES.getCode());
    assertEquals("Z", UNABLE_TO_DETERMINE.getCode());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Declines to State", DECLINES_TO_STATE.getDescription());
    assertEquals("No", NO.getDescription());
    assertEquals("Undetermined", UNDETERMINED.getDescription());
    assertEquals("No User Selection", NO_USER_SELECTION.getDescription());
    assertEquals("Yes", YES.getDescription());
    assertEquals("Unable to Determine", UNABLE_TO_DETERMINE.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals("D", converter.convertToDatabaseColumn(DECLINES_TO_STATE));
    assertEquals("N", converter.convertToDatabaseColumn(NO));
    assertEquals("U", converter.convertToDatabaseColumn(UNDETERMINED));
    assertEquals("X", converter.convertToDatabaseColumn(NO_USER_SELECTION));
    assertEquals("Y", converter.convertToDatabaseColumn(YES));
    assertEquals("Z", converter.convertToDatabaseColumn(UNABLE_TO_DETERMINE));
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertNull(converter.convertToEntityAttribute(" "));
    assertEquals(DECLINES_TO_STATE, converter.convertToEntityAttribute("D"));
    assertEquals(NO, converter.convertToEntityAttribute("N"));
    assertEquals(UNDETERMINED, converter.convertToEntityAttribute("U"));
    assertEquals(NO_USER_SELECTION, converter.convertToEntityAttribute("X"));
    assertEquals(YES, converter.convertToEntityAttribute("Y"));
    assertEquals(UNABLE_TO_DETERMINE, converter.convertToEntityAttribute("Z"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute("@");
  }
}
