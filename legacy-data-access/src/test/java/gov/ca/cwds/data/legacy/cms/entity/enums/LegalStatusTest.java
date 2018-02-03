package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.LegalStatus.ALLEGED;
import static gov.ca.cwds.data.legacy.cms.entity.enums.LegalStatus.PRESUMED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import gov.ca.cwds.data.legacy.cms.entity.enums.LegalStatus.LegalStatusConverter;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class LegalStatusTest {

  private LegalStatusConverter converter;

  @Before
  public void before() {
    converter = new LegalStatusConverter();
  }

  @Test
  public void getCode_success_forAllEnumValues() {
    assertThat(ALLEGED.getCode(), is(equalTo("A")));
    assertThat(PRESUMED.getCode(), is(equalTo("P")));
  }

  @Test
  public void getDescription_success_forAllEnumValues() {
    assertThat(ALLEGED.getDescription(), is(equalTo("Alleged")));
    assertThat(PRESUMED.getDescription(), is(equalTo("Presumed")));
  }

  @Test
  public void convertToDatabaseColumn_success_forAllEnumValues() {
    assertThat(converter.convertToDatabaseColumn(null), is(nullValue()));
    assertThat(converter.convertToDatabaseColumn(ALLEGED), is(equalTo("A")));
    assertThat(converter.convertToDatabaseColumn(PRESUMED), is(equalTo("P")));
  }

  @Test
  public void convertToEntityAttribute_success_forAllEnumValues() {
    assertThat(converter.convertToEntityAttribute(null), is(nullValue()));
    assertThat(converter.convertToEntityAttribute(" "), is(nullValue()));
    assertThat(converter.convertToEntityAttribute("A"), is(equalTo(ALLEGED)));
    assertThat(converter.convertToEntityAttribute("P"), is(equalTo(PRESUMED)));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void convertToEntityAttribute_exception_whenUnknownCode() {
    converter.convertToEntityAttribute("@");
  }

}