package gov.ca.cwds.data.legacy.cms.entity.enums;

import org.junit.Before;
import org.junit.Test;

import static gov.ca.cwds.data.legacy.cms.entity.enums.ResponsibleAgency.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ResponsibleAgencyTest {

  private ResponsibleAgencyConverter converter;

  @Before
  public void before() {
    converter = new ResponsibleAgencyConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals('A', PRIVATE_ADOPTION_AGENCY.getCode().charValue());
    assertEquals('C', COUNTY_WELFARE_DEPARTMENT.getCode().charValue());
    assertEquals('I', INDIAN_CHILD_WELFARE.getCode().charValue());
    assertEquals('K', KIN_GAP.getCode().charValue());
    assertEquals('M', MENTAL_HEALTH.getCode().charValue());
    assertEquals('O', OUT_OF_STATE_AGENCY.getCode().charValue());
    assertEquals('P', PROBATION.getCode().charValue());
    assertEquals('S', STATE_ADOPTION_DISTRICT_OFFICE.getCode().charValue());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Private Adoption Agency", PRIVATE_ADOPTION_AGENCY.getDescription());
    assertEquals("County Welfare Department", COUNTY_WELFARE_DEPARTMENT.getDescription());
    assertEquals("Indian Child Welfare", INDIAN_CHILD_WELFARE.getDescription());
    assertEquals("Kin-Gap", KIN_GAP.getDescription());
    assertEquals("Mental Health", MENTAL_HEALTH.getDescription());
    assertEquals("Out of State Agency", OUT_OF_STATE_AGENCY.getDescription());
    assertEquals("Probation", PROBATION.getDescription());
    assertEquals("State Adoption District Office", STATE_ADOPTION_DISTRICT_OFFICE.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals('A', converter.convertToDatabaseColumn(PRIVATE_ADOPTION_AGENCY).charValue());
    assertEquals('C', converter.convertToDatabaseColumn(COUNTY_WELFARE_DEPARTMENT).charValue());
    assertEquals('I', converter.convertToDatabaseColumn(INDIAN_CHILD_WELFARE).charValue());
    assertEquals('K', converter.convertToDatabaseColumn(KIN_GAP).charValue());
    assertEquals('M', converter.convertToDatabaseColumn(MENTAL_HEALTH).charValue());
    assertEquals('O', converter.convertToDatabaseColumn(OUT_OF_STATE_AGENCY).charValue());
    assertEquals('P', converter.convertToDatabaseColumn(PROBATION).charValue());
    assertEquals('S', converter.convertToDatabaseColumn(STATE_ADOPTION_DISTRICT_OFFICE).charValue());
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertEquals(PRIVATE_ADOPTION_AGENCY, converter.convertToEntityAttribute('A'));
    assertEquals(COUNTY_WELFARE_DEPARTMENT, converter.convertToEntityAttribute('C'));
    assertEquals(INDIAN_CHILD_WELFARE, converter.convertToEntityAttribute('I'));
    assertEquals(KIN_GAP, converter.convertToEntityAttribute('K'));
    assertEquals(MENTAL_HEALTH, converter.convertToEntityAttribute('M'));
    assertEquals(OUT_OF_STATE_AGENCY, converter.convertToEntityAttribute('O'));
    assertEquals(PROBATION, converter.convertToEntityAttribute('P'));
    assertEquals(STATE_ADOPTION_DISTRICT_OFFICE, converter.convertToEntityAttribute('S'));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute('@');
  }
}
