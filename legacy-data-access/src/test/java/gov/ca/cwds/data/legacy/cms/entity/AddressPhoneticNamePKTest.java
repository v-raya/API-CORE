package gov.ca.cwds.data.legacy.cms.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.Test;

/**
 * @author CWDS CALS API Team
 */
public class AddressPhoneticNamePKTest {
  final String phoneticName = "phonname";
  final String newPhoneticName = "newname";
  final String primaryNameId = "primnameid";
  final String newPrimaryNameId = "newnameid";
  final String primaryNameCode = "P";
  final String newPrimaryNameCode = "N";

  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(AddressPhoneticNamePK.class.newInstance(), is(notNullValue()));   
  }
  
  @Test
  public void testPersistentConstructor() throws Exception {
    AddressPhoneticNamePK addressPhoneticNamePK = new AddressPhoneticNamePK(phoneticName,
        primaryNameId,
        primaryNameCode);
    assertThat(addressPhoneticNamePK.getPhonetcNm(), is(equalTo(phoneticName)));
    assertThat(addressPhoneticNamePK.getPrmryNmid(), is(equalTo(primaryNameId)));
    assertThat(addressPhoneticNamePK.getPrmryNmcd(), is(equalTo(primaryNameCode)));
  }
  
  @Test
  public void testSetters() throws Exception {
    AddressPhoneticNamePK addressPhoneticNamePK = new AddressPhoneticNamePK(phoneticName,
        primaryNameId,
        primaryNameCode);
    addressPhoneticNamePK.setPhonetcNm(newPhoneticName);
    assertThat(addressPhoneticNamePK.getPhonetcNm(), is(equalTo(newPhoneticName)));
    addressPhoneticNamePK.setPrmryNmid(newPrimaryNameId);
    assertThat(addressPhoneticNamePK.getPrmryNmid(), is(equalTo(newPrimaryNameId)));
    addressPhoneticNamePK.setPrmryNmcd(newPrimaryNameCode);
    assertThat(addressPhoneticNamePK.getPrmryNmcd(), is(equalTo(newPrimaryNameCode)));
  }
  
}
