package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.AssignmentRecipient.CASE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.AssignmentRecipient.REFERRAL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import gov.ca.cwds.data.legacy.cms.entity.enums.AssignmentRecipient.AssignmentRecipientConverter;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class AssignmentRecipientTest {

  private AssignmentRecipientConverter converter;

  @Before
  public void before() {
    converter = new AssignmentRecipientConverter();
  }

  @Test
  public void getCode_success_forAllEnumValues() {
    assertThat(CASE.getCode(), is(equalTo("C")));
    assertThat(REFERRAL.getCode(), is(equalTo("R")));
  }

  @Test
  public void getDescription_success_forAllEnumValues() {
    assertThat(CASE.getDescription(), is(equalTo("Case")));
    assertThat(REFERRAL.getDescription(), is(equalTo("Referral")));
  }

  @Test
  public void convertToDatabaseColumn_success_forAllEnumValues() {
    assertThat(converter.convertToDatabaseColumn(null), is(nullValue()));
    assertThat(converter.convertToDatabaseColumn(CASE), is(equalTo("C")));
    assertThat(converter.convertToDatabaseColumn(REFERRAL), is(equalTo("R")));
  }

  @Test
  public void convertToEntityAttribute_success_forAllEnumValues() {
    assertThat(converter.convertToEntityAttribute(null), is(nullValue()));
    assertThat(converter.convertToEntityAttribute(" "), is(nullValue()));
    assertThat(converter.convertToEntityAttribute("C"), is(equalTo(CASE)));
    assertThat(converter.convertToEntityAttribute("R"), is(equalTo(REFERRAL)));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void convertToEntityAttribute_exception_whenUnknownCode() {
    converter.convertToEntityAttribute("@");
  }

}