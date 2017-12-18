package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.AssignmentType.PRIMARY;
import static gov.ca.cwds.data.legacy.cms.entity.enums.AssignmentType.SECONDARY;
import static gov.ca.cwds.data.legacy.cms.entity.enums.AssignmentType.READ_ONLY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import gov.ca.cwds.data.legacy.cms.entity.enums.AssignmentType.AssignmentTypeConverter;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class AssignmentTypeTest {

  private AssignmentTypeConverter converter;

  @Before
  public void before() {
    converter = new AssignmentTypeConverter();
  }

  @Test
  public void getCode_success_forAllEnumValues() {
    assertThat(PRIMARY.getCode(), is(equalTo("P")));
    assertThat(SECONDARY.getCode(), is(equalTo("S")));
    assertThat(READ_ONLY.getCode(), is(equalTo("R")));
  }

  @Test
  public void getDescription_success_forAllEnumValues() {
    assertThat(PRIMARY.getDescription(), is(equalTo("Primary")));
    assertThat(SECONDARY.getDescription(), is(equalTo("Secondary")));
    assertThat(READ_ONLY.getDescription(), is(equalTo("Read Only")));
  }

  @Test
  public void convertToDatabaseColumn_success_forAllEnumValues() {
    assertThat(converter.convertToDatabaseColumn(null), is(nullValue()));
    assertThat(converter.convertToDatabaseColumn(PRIMARY), is(equalTo("P")));
    assertThat(converter.convertToDatabaseColumn(SECONDARY), is(equalTo("S")));
    assertThat(converter.convertToDatabaseColumn(READ_ONLY), is(equalTo("R")));
  }

  @Test
  public void convertToEntityAttribute_success_forAllEnumValues() {
    assertThat(converter.convertToEntityAttribute(null), is(nullValue()));
    assertThat(converter.convertToEntityAttribute(" "), is(nullValue()));
    assertThat(converter.convertToEntityAttribute("P"), is(equalTo(PRIMARY)));
    assertThat(converter.convertToEntityAttribute("S"), is(equalTo(SECONDARY)));
    assertThat(converter.convertToEntityAttribute("R"), is(equalTo(READ_ONLY)));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void convertToEntityAttribute_exception_whenUnknownCode() {
    converter.convertToEntityAttribute("@");
  }

}