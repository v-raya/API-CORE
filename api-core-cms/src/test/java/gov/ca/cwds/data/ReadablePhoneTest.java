package gov.ca.cwds.data;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.std.ApiPhoneAware.PhoneType;
import io.dropwizard.jackson.Jackson;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ReadablePhoneTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private final String phoneNumber = "408 690-1234";
  private final String phoneNumberExtension = "987";
  private final PhoneType pt = PhoneType.Cell;

  @Test
  public void testConstructor() {
    ReadablePhone rp = new ReadablePhone(null, phoneNumber, phoneNumberExtension, pt);
    assertThat(rp.getPhoneNumber(), is(equalTo(phoneNumber)));
    assertThat(rp.getPhoneNumberExtension(), is(equalTo(phoneNumberExtension)));
    assertThat(rp.getPhoneType(), is(equalTo(pt)));
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER
        .readValue(fixture("fixtures/data/ReadablePhone/valid/valid.json"), ReadablePhone.class));
    assertThat(MAPPER.writeValueAsString(validReadablePhone()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    final ReadablePhone rp = validReadablePhone();
    assertThat(MAPPER.readValue(fixture("fixtures/data/ReadablePhone/valid/valid.json"),
        ReadablePhone.class), is(equalTo(rp)));
  }

  @Test
  public void equalsHashCodeWork() {
    // EqualsVerifier.forClass(ReadablePhone.class).suppress(Warning.NONFINAL_FIELDS).verify();

    final ReadablePhone rp1 = validReadablePhone();
    assertThat(rp1.hashCode(), is(not(0)));

    final ReadablePhone rp2 = new ReadablePhone(null, "916 691-2234", "981", PhoneType.Cell);
    assertThat(rp1.hashCode(), is(not(rp2.hashCode())));
    assertThat(rp1, is(not(equalTo(rp2))));
  }

  private ReadablePhone validReadablePhone() {
    return new ReadablePhone(null, "408 690-1234", "987", PhoneType.Cell);
  }

}
