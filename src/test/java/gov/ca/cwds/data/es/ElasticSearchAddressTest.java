package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.hamcrest.junit.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.rest.api.domain.es.ESSearchRequest;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ElasticSearchAddressTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testType() throws Exception {
    assertThat(ElasticSearchAddress.class, notNullValue());
  }

  @Test
  public void testInstantiation() throws Exception {
    String streetAddress = null;
    String city = null;
    String state = null;
    Integer zip = null;
    ElasticSearchAddress target = new ElasticSearchAddress(streetAddress, city, state, zip);
    assertThat(target, notNullValue());
  }

  @Test
  public void testEquals_Args$Object() throws Exception {
    EqualsVerifier.forClass(ESSearchRequest.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
