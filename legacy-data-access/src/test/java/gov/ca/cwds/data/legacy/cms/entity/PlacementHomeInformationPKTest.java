package gov.ca.cwds.data.legacy.cms.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PlacementHomeInformationPKTest {
  final String thirdId = "1234567ABC";
  final String fksbPvdrt = "2345678ABC";
  final String fkplcHmT = "3456789ABC";
  final String newThirdId = "4567890ABC";
  final String newFksbPvdrt = "5678901ABC";
  final String newFkplcHmT = "67890123ABC";

  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(PlacementHomeInformationPK.class.newInstance(), is(notNullValue()));
    
  }
  
  @Test
  public void testPersistentConstructor() throws Exception {
    PlacementHomeInformationPK placement = new PlacementHomeInformationPK(thirdId, fksbPvdrt, fkplcHmT);
    assertThat(placement.getThirdId(), is(equalTo(thirdId)));
    assertThat(placement.getFksbPvdrt(), is(equalTo(fksbPvdrt)));
    assertThat(placement.getFkplcHmT(), is(equalTo(fkplcHmT)));
    
  }
  
  @Test
  public void testSetters() throws Exception {
    PlacementHomeInformationPK placement = new PlacementHomeInformationPK(thirdId, fksbPvdrt, fkplcHmT);
    placement.setThirdId(newThirdId);
    assertThat(placement.getThirdId(), is(equalTo(newThirdId)));
    placement.setFksbPvdrt(newFksbPvdrt);
    assertThat(placement.getFksbPvdrt(), is(equalTo(newFksbPvdrt)));
    placement.setFkplcHmT(newFkplcHmT);
    assertThat(placement.getFkplcHmT(), is(equalTo(newFkplcHmT)));
  }
  
  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
    PlacementHomeInformationPK placement = new PlacementHomeInformationPK();
    assertTrue(placement.equals(placement));
  }
  
  @Test
  public void shouldHaveEqualsHashcode() throws Exception {
    PlacementHomeInformationPK placement = new PlacementHomeInformationPK(thirdId, fksbPvdrt, fkplcHmT);
    PlacementHomeInformationPK placement1 = new PlacementHomeInformationPK(thirdId, fksbPvdrt, fkplcHmT);
    assertEquals("Expecting placement information to have same hash code", placement.hashCode(), placement1.hashCode());
  }
  
}
