package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.CompetencyType.ADOPTION_WORKER;
import static gov.ca.cwds.data.legacy.cms.entity.enums.CompetencyType.NEITHER;
import static gov.ca.cwds.data.legacy.cms.entity.enums.CompetencyType.PROFESSIONAL;
import gov.ca.cwds.data.legacy.cms.entity.enums.CompetencyType.CompetencyTypeConverter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class CompetencyTypeTest {

  private CompetencyTypeConverter converter;

  @Before
  public void before() {
    converter = new CompetencyTypeConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals("A", ADOPTION_WORKER.getCode());
    assertEquals("N", NEITHER.getCode());
    assertEquals("P", PROFESSIONAL.getCode());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Adoption worker", ADOPTION_WORKER.getDescription());
    assertEquals("Neither", NEITHER.getDescription());
    assertEquals("Professional", PROFESSIONAL.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals("A", converter.convertToDatabaseColumn(ADOPTION_WORKER));
    assertEquals("N", converter.convertToDatabaseColumn(NEITHER));
    assertEquals("P", converter.convertToDatabaseColumn(PROFESSIONAL));
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertNull(converter.convertToEntityAttribute(""));
    assertNull(converter.convertToEntityAttribute(" "));
    assertNull(converter.convertToEntityAttribute("  "));
    assertEquals(ADOPTION_WORKER, converter.convertToEntityAttribute("A"));
    assertEquals(NEITHER, converter.convertToEntityAttribute("N"));
    assertEquals(PROFESSIONAL, converter.convertToEntityAttribute("P"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute("@");
  }
}
