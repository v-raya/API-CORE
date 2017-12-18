package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.AwolAbducted.AWOL_BASED_ON_USER_ENTRY;
import static gov.ca.cwds.data.legacy.cms.entity.enums.AwolAbducted.ABDUCTED_BASED_ON_USER_ENTRY;
import static gov.ca.cwds.data.legacy.cms.entity.enums.AwolAbducted.ABDUCTED_BASED_ON_SYSTEM_SETTING;
import static gov.ca.cwds.data.legacy.cms.entity.enums.AwolAbducted.NOT_APPLICABLE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.AwolAbducted.AWOL_BASED_ON_SYSTEM_SETTING;
import static gov.ca.cwds.data.legacy.cms.entity.enums.AwolAbducted.AwolAbductedConverter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class AwolAbductedTest {

  private AwolAbductedConverter converter;

  @Before
  public void before() {
    converter = new AwolAbductedConverter();
  }

  @Test
  public void testGetCode() {
    assertEquals("A", AWOL_BASED_ON_USER_ENTRY.getCode());
    assertEquals("B", ABDUCTED_BASED_ON_USER_ENTRY.getCode());
    assertEquals("D", ABDUCTED_BASED_ON_SYSTEM_SETTING.getCode());
    assertEquals("N", NOT_APPLICABLE.getCode());
    assertEquals("P", AWOL_BASED_ON_SYSTEM_SETTING.getCode());
  }

  @Test
  public void testGetDescription() {
    assertEquals("AWOL - user-entered", AWOL_BASED_ON_USER_ENTRY.getDescription());
    assertEquals("Abducted - user-entered", ABDUCTED_BASED_ON_USER_ENTRY.getDescription());
    assertEquals("Abducted - system-entered", ABDUCTED_BASED_ON_SYSTEM_SETTING.getDescription());
    assertEquals("Not Applicable", NOT_APPLICABLE.getDescription());
    assertEquals("AWOL - system-entered", AWOL_BASED_ON_SYSTEM_SETTING.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals("A", converter.convertToDatabaseColumn(AWOL_BASED_ON_USER_ENTRY));
    assertEquals("B", converter.convertToDatabaseColumn(ABDUCTED_BASED_ON_USER_ENTRY));
    assertEquals("D", converter.convertToDatabaseColumn(ABDUCTED_BASED_ON_SYSTEM_SETTING));
    assertEquals("N", converter.convertToDatabaseColumn(NOT_APPLICABLE));
    assertEquals("P", converter.convertToDatabaseColumn(AWOL_BASED_ON_SYSTEM_SETTING));
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertEquals(AWOL_BASED_ON_USER_ENTRY, converter.convertToEntityAttribute("A"));
    assertEquals(ABDUCTED_BASED_ON_USER_ENTRY, converter.convertToEntityAttribute("B"));
    assertEquals(ABDUCTED_BASED_ON_SYSTEM_SETTING, converter.convertToEntityAttribute("D"));
    assertEquals(NOT_APPLICABLE, converter.convertToEntityAttribute("N"));
    assertEquals(AWOL_BASED_ON_SYSTEM_SETTING, converter.convertToEntityAttribute("P"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute("@");
  }
}
