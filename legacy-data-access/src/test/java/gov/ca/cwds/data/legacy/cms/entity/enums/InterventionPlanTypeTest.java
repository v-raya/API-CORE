package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.InterventionPlanType.INITIAL_MENTAL_HEALTH;
import static gov.ca.cwds.data.legacy.cms.entity.enums.InterventionPlanType.INITIAL_DEVELOPMENTAL;
import static gov.ca.cwds.data.legacy.cms.entity.enums.InterventionPlanType.UPDATED_MENTAL_HEALTH;
import static gov.ca.cwds.data.legacy.cms.entity.enums.InterventionPlanType.UPDATED_DEVELOPMENTAL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import gov.ca.cwds.data.legacy.cms.entity.enums.InterventionPlanType.InterventionPlanTypeConverter;
import org.junit.Before;
import org.junit.Test;

public class InterventionPlanTypeTest {

  private InterventionPlanTypeConverter converter;

  @Before
  public void before() {
    converter = new InterventionPlanTypeConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals("F", INITIAL_MENTAL_HEALTH.getCode());
    assertEquals("I", INITIAL_DEVELOPMENTAL.getCode());
    assertEquals("L", UPDATED_MENTAL_HEALTH.getCode());
    assertEquals("U", UPDATED_DEVELOPMENTAL.getCode());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Initial Mental Health", INITIAL_MENTAL_HEALTH.getDescription());
    assertEquals("Initial Developmental", INITIAL_DEVELOPMENTAL.getDescription());
    assertEquals("Updated Mental Health", UPDATED_MENTAL_HEALTH.getDescription());
    assertEquals("Updated Developmental", UPDATED_DEVELOPMENTAL.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals("F", converter.convertToDatabaseColumn(INITIAL_MENTAL_HEALTH));
    assertEquals("I", converter.convertToDatabaseColumn(INITIAL_DEVELOPMENTAL));
    assertEquals("L", converter.convertToDatabaseColumn(UPDATED_MENTAL_HEALTH));
    assertEquals("U", converter.convertToDatabaseColumn(UPDATED_DEVELOPMENTAL));
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertNull(converter.convertToEntityAttribute(" "));
    assertEquals(INITIAL_MENTAL_HEALTH, converter.convertToEntityAttribute("F"));
    assertEquals(INITIAL_DEVELOPMENTAL, converter.convertToEntityAttribute("I"));
    assertEquals(UPDATED_MENTAL_HEALTH, converter.convertToEntityAttribute("L"));
    assertEquals(UPDATED_DEVELOPMENTAL, converter.convertToEntityAttribute("U"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute("@");
  }
}
