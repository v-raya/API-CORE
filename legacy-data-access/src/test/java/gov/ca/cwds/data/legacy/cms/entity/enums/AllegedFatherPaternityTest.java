package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.AllegedFatherPaternityStatus.NO;
import static gov.ca.cwds.data.legacy.cms.entity.enums.AllegedFatherPaternityStatus.UNKNOWN;
import static gov.ca.cwds.data.legacy.cms.entity.enums.AllegedFatherPaternityStatus.YES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import gov.ca.cwds.data.legacy.cms.entity.enums.AllegedFatherPaternityStatus.AllegedFatherPaternityStatusConverter;
import org.junit.Before;
import org.junit.Test;

public class AllegedFatherPaternityTest {

  private AllegedFatherPaternityStatusConverter converter;

  @Before
  public void before() {
    converter = new AllegedFatherPaternityStatusConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals("N", NO.getCode());
    assertEquals("U", UNKNOWN.getCode());
    assertEquals("Y", YES.getCode());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Alleged father in Not Legal father", NO.getDescription());
    assertEquals("Undetermined/Inconclusive", UNKNOWN.getDescription());
    assertEquals("Alleged father is Legal father", YES.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals("N", converter.convertToDatabaseColumn(NO));
    assertEquals("U", converter.convertToDatabaseColumn(UNKNOWN));
    assertEquals("Y", converter.convertToDatabaseColumn(YES));
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertNull(converter.convertToEntityAttribute(" "));
    assertEquals(NO, converter.convertToEntityAttribute("N"));
    assertEquals(UNKNOWN, converter.convertToEntityAttribute("U"));
    assertEquals(YES, converter.convertToEntityAttribute("Y"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute("@");
  }
}
