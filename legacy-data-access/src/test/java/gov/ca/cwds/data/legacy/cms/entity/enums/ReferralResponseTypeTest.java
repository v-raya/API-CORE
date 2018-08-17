package gov.ca.cwds.data.legacy.cms.entity.enums;

import static gov.ca.cwds.data.legacy.cms.entity.enums.ReferralResponseType.EVALUATE_OUT;
import static gov.ca.cwds.data.legacy.cms.entity.enums.ReferralResponseType.IMMEDIATE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.ReferralResponseType.RESPONSE_TIME_10_DAYS;
import static gov.ca.cwds.data.legacy.cms.entity.enums.ReferralResponseType.RESPONSE_TIME_3_DAYS;
import static gov.ca.cwds.data.legacy.cms.entity.enums.ReferralResponseType.RESPONSE_TIME_5_DAYS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.legacy.cms.entity.enums.ReferralResponseType.ReferralResponseTypeConverter;

/**
 * @author CWDS API Team
 *
 */
public class ReferralResponseTypeTest {

  private ReferralResponseTypeConverter converter;

  @Before
  public void before() {
    converter = new ReferralResponseTypeConverter();
  }

  @Test
  public void testGetCode() {
    assertThat((short) 1519, is(equalTo(EVALUATE_OUT.getCode())));
    assertThat((short) 1516, is(equalTo(RESPONSE_TIME_3_DAYS.getCode())));
    assertThat((short) 1517, is(equalTo(RESPONSE_TIME_5_DAYS.getCode())));
    assertThat((short) 1518, is(equalTo(RESPONSE_TIME_10_DAYS.getCode())));
    assertThat((short) 1520, is(equalTo(IMMEDIATE.getCode())));
  }

  @Test
  public void testGetDescription() {
    assertEquals("Evaluate Out", EVALUATE_OUT.getDescription());
    assertEquals("5 Day", RESPONSE_TIME_5_DAYS.getDescription());
    assertEquals("10 Day", RESPONSE_TIME_10_DAYS.getDescription());
    assertEquals("3 Day", RESPONSE_TIME_3_DAYS.getDescription());
    assertEquals("Immediate", IMMEDIATE.getDescription());
  }

  @Test
  public void testConvertToDatabaseColumn() {
    assertNull(converter.convertToDatabaseColumn(null));
    assertThat((short) 1519, is(equalTo(converter.convertToDatabaseColumn(EVALUATE_OUT))));
    assertThat((short) 1516, is(equalTo(converter.convertToDatabaseColumn(RESPONSE_TIME_3_DAYS))));
    assertThat((short) 1517, is(equalTo(converter.convertToDatabaseColumn(RESPONSE_TIME_5_DAYS))));
    assertThat((short) 1518, is(equalTo(converter.convertToDatabaseColumn(RESPONSE_TIME_10_DAYS))));
    assertThat((short) 1520, is(equalTo(converter.convertToDatabaseColumn(IMMEDIATE))));
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertNull(converter.convertToEntityAttribute(null));
    assertThat(EVALUATE_OUT, is(equalTo(converter.convertToEntityAttribute((short) 1519))));
    assertThat(RESPONSE_TIME_3_DAYS, is(equalTo(converter.convertToEntityAttribute((short) 1516))));
    assertThat(RESPONSE_TIME_5_DAYS, is(equalTo(converter.convertToEntityAttribute((short) 1517))));
    assertThat(RESPONSE_TIME_10_DAYS,
        is(equalTo(converter.convertToEntityAttribute((short) 1518))));
    assertThat(IMMEDIATE, is(equalTo(converter.convertToEntityAttribute((short) 1520))));
  }


  @Test(expected = UnsupportedOperationException.class)
  public void testInvalidCode() {
    converter.convertToEntityAttribute((short) 123);
  }

}
