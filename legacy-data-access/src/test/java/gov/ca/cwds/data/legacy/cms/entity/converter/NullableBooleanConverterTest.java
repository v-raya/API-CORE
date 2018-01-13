package gov.ca.cwds.data.legacy.cms.entity.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class NullableBooleanConverterTest {

  private NullableBooleanConverter converter;

  @Before
  public void before() {
    converter = new NullableBooleanConverter();
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertEquals("Y", (converter.convertToDatabaseColumn(Boolean.TRUE)));
    assertEquals("N", (converter.convertToDatabaseColumn(Boolean.FALSE)));
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertNull(converter.convertToEntityAttribute(""));
    assertNull(converter.convertToEntityAttribute(" "));
    assertEquals(Boolean.TRUE, (converter.convertToEntityAttribute("Y")));
    assertEquals(Boolean.TRUE, (converter.convertToEntityAttribute("y")));
    assertEquals(Boolean.TRUE, (converter.convertToEntityAttribute("Y ")));
    assertEquals(Boolean.FALSE, (converter.convertToEntityAttribute("N")));
    assertEquals(Boolean.FALSE, (converter.convertToEntityAttribute("N ")));
    assertEquals(Boolean.FALSE, (converter.convertToEntityAttribute("foo")));
  }
}
