package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.UnableToDetermineReason.ABANDONMENT;
import static gov.ca.cwds.data.legacy.cms.entity.enums.UnableToDetermineReason.INCAPACITATION;
import static gov.ca.cwds.data.legacy.cms.entity.enums.UnableToDetermineReason.INDIVIDUAL_DOES_NOT_KNOW;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import gov.ca.cwds.data.legacy.cms.entity.enums.UnableToDetermineReason.UnableToDetermineReasonConverter;
import org.junit.Before;
import org.junit.Test;

public class UnableToDetermineReasonTest {

  private UnableToDetermineReasonConverter converter;

  @Before
  public void before() {
    converter = new UnableToDetermineReasonConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals('A', ABANDONMENT.getCode().charValue());
    assertEquals('I', INCAPACITATION.getCode().charValue());
    assertEquals('K', INDIVIDUAL_DOES_NOT_KNOW.getCode().charValue());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Abandonment", ABANDONMENT.getDescription());
    assertEquals("Incapacitation", INCAPACITATION.getDescription());
    assertEquals("Individual Does Not Know", INDIVIDUAL_DOES_NOT_KNOW.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals('A', converter.convertToDatabaseColumn(ABANDONMENT).charValue());
    assertEquals('I', converter.convertToDatabaseColumn(INCAPACITATION).charValue());
    assertEquals('K', converter.convertToDatabaseColumn(INDIVIDUAL_DOES_NOT_KNOW).charValue());
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertNull(converter.convertToEntityAttribute(' '));
    assertEquals(ABANDONMENT, converter.convertToEntityAttribute('A'));
    assertEquals(INCAPACITATION, converter.convertToEntityAttribute('I'));
    assertEquals(INDIVIDUAL_DOES_NOT_KNOW, converter.convertToEntityAttribute('K'));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute('@');
  }
}
