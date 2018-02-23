package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.ScreeningResult.NO_REFERRAL_NEEDED;
import static gov.ca.cwds.data.legacy.cms.entity.enums.ScreeningResult.REFERRAL_FOR_SERVICES;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import gov.ca.cwds.data.legacy.cms.entity.enums.ScreeningResult.ScreeningResultConverter;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class ScreeningResultTest {

  private ScreeningResultConverter converter;

  @Before
  public void before() {
    converter = new ScreeningResultConverter();
  }

  @Test
  public void getCode_success_forAllEnumValues() {
    assertThat(NO_REFERRAL_NEEDED.getCode(), is(equalTo("N")));
    assertThat(REFERRAL_FOR_SERVICES.getCode(), is(equalTo("P")));
  }

  @Test
  public void getDescription_success_forAllEnumValues() {
    assertThat(NO_REFERRAL_NEEDED.getDescription(), is(equalTo("No Referral Needed")));
    assertThat(REFERRAL_FOR_SERVICES.getDescription(), is(equalTo("Referral for Services")));
  }

  @Test
  public void convertToDatabaseColumn_success_forAllEnumValues() {
    assertThat(converter.convertToDatabaseColumn(null), is(nullValue()));
    assertThat(converter.convertToDatabaseColumn(NO_REFERRAL_NEEDED), is(equalTo("N")));
    assertThat(converter.convertToDatabaseColumn(REFERRAL_FOR_SERVICES), is(equalTo("P")));
  }

  @Test
  public void convertToEntityAttribute_success_forAllEnumValues() {
    assertThat(converter.convertToEntityAttribute(null), is(nullValue()));
    assertThat(converter.convertToEntityAttribute(" "), is(nullValue()));
    assertThat(converter.convertToEntityAttribute("N"), is(equalTo(NO_REFERRAL_NEEDED)));
    assertThat(converter.convertToEntityAttribute("P"), is(equalTo(REFERRAL_FOR_SERVICES)));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void convertToEntityAttribute_exception_whenUnknownCode() {
    converter.convertToEntityAttribute("@");
  }
}