package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.MilitaryStatus.MILITARY_ACTIVE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.MilitaryStatus.MILITARY_DEPENDENT;
import static gov.ca.cwds.data.legacy.cms.entity.enums.MilitaryStatus.NO_MILITARY_INVOLVEMENT;
import static gov.ca.cwds.data.legacy.cms.entity.enums.MilitaryStatus.NO_INFORMATION_AVAILABLE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.MilitaryStatus.VETERAN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import gov.ca.cwds.data.legacy.cms.entity.enums.MilitaryStatus.MilitaryStatusConverter;
import org.junit.Before;
import org.junit.Test;

public class MilitaryStatusTest {

  private MilitaryStatusConverter converter;

  @Before
  public void before() {
    converter = new MilitaryStatusConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals("A", MILITARY_ACTIVE.getCode());
    assertEquals("D", MILITARY_DEPENDENT.getCode());
    assertEquals("N", NO_MILITARY_INVOLVEMENT.getCode());
    assertEquals("U", NO_INFORMATION_AVAILABLE.getCode());
    assertEquals("V", VETERAN.getCode());
  }

  @Test
  public void testGetDescription() {
    assertEquals("Military active", MILITARY_ACTIVE.getDescription());
    assertEquals("Military dependent", MILITARY_DEPENDENT.getDescription());
    assertEquals("No military involvement", NO_MILITARY_INVOLVEMENT.getDescription());
    assertEquals("No information available", NO_INFORMATION_AVAILABLE.getDescription());
    assertEquals("Veteran", VETERAN.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals("A", converter.convertToDatabaseColumn(MILITARY_ACTIVE));
    assertEquals("D", converter.convertToDatabaseColumn(MILITARY_DEPENDENT));
    assertEquals("N", converter.convertToDatabaseColumn(NO_MILITARY_INVOLVEMENT));
    assertEquals("U", converter.convertToDatabaseColumn(NO_INFORMATION_AVAILABLE));
    assertEquals("V", converter.convertToDatabaseColumn(VETERAN));
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertNull(converter.convertToEntityAttribute(" "));
    assertEquals(MILITARY_ACTIVE, converter.convertToEntityAttribute("A"));
    assertEquals(MILITARY_DEPENDENT, converter.convertToEntityAttribute("D"));
    assertEquals(NO_MILITARY_INVOLVEMENT, converter.convertToEntityAttribute("N"));
    assertEquals(NO_INFORMATION_AVAILABLE, converter.convertToEntityAttribute("U"));
    assertEquals(VETERAN, converter.convertToEntityAttribute("V"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute("@");
  }
}
