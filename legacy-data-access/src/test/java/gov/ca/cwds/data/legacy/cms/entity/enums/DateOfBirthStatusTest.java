package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.DateOfBirthStatus.ACTUALLY_ENTERED;
import static gov.ca.cwds.data.legacy.cms.entity.enums.DateOfBirthStatus.NOT_PROVIDED;
import static gov.ca.cwds.data.legacy.cms.entity.enums.DateOfBirthStatus.ESTIMATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import gov.ca.cwds.data.legacy.cms.entity.enums.DateOfBirthStatus.DateOfBirthStatusConverter;
import org.junit.Before;
import org.junit.Test;

public class DateOfBirthStatusTest {

  private DateOfBirthStatusConverter converter;

  @Before
  public void before() {
    converter = new DateOfBirthStatusConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals("N", ACTUALLY_ENTERED.getCode());
    assertEquals("U", NOT_PROVIDED.getCode());
    assertEquals("Y", ESTIMATED.getCode());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Actually Entered", ACTUALLY_ENTERED.getDescription());
    assertEquals("Not Provided", NOT_PROVIDED.getDescription());
    assertEquals("Estimated", ESTIMATED.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals("N", converter.convertToDatabaseColumn(ACTUALLY_ENTERED));
    assertEquals("U", converter.convertToDatabaseColumn(NOT_PROVIDED));
    assertEquals("Y", converter.convertToDatabaseColumn(ESTIMATED));
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertNull(converter.convertToEntityAttribute(" "));
    assertEquals(ACTUALLY_ENTERED, converter.convertToEntityAttribute("N"));
    assertEquals(NOT_PROVIDED, converter.convertToEntityAttribute("U"));
    assertEquals(ESTIMATED, converter.convertToEntityAttribute("Y"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute("@");
  }
}
