package gov.ca.cwds.data.legacy.cms.entity.enums;

import org.junit.Test;

import static gov.ca.cwds.data.legacy.cms.entity.enums.ResponsibleAgency.*;
import static org.junit.Assert.assertEquals;

public class ResponsibleAgencyTest {

  @Test
  public void testFromCode() {
    ResponsibleAgencyConverter converter = new ResponsibleAgencyConverter();

    assertEquals(PRIVATE_ADOPTION_AGENCY, converter.convertToEntityAttribute('A'));
    assertEquals(COUNTY_WELFARE_DEPARTMENT, converter.convertToEntityAttribute('C'));
    assertEquals(INDIAN_CHILD_WELFARE, converter.convertToEntityAttribute('I'));
    assertEquals(KIN_GAP, converter.convertToEntityAttribute('K'));
    assertEquals(MENTAL_HEALTH, converter.convertToEntityAttribute('M'));
    assertEquals(OUT_OF_STATE_AGENCY, converter.convertToEntityAttribute('O'));
    assertEquals(PROBATION, converter.convertToEntityAttribute('P'));
    assertEquals(STATE_ADOPTION_DISTRICT_OFFICE, converter.convertToEntityAttribute('S'));
  }
}
