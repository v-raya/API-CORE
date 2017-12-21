package gov.ca.cwds.cms.data.access.service.impl;

import org.junit.Test;

/**
 * Created by TPT2 on 12/21/2017.
 */
public class R03232Test extends BaseDocToolRulesTest {
  
  //zip no
  @Test
  public void testZipNoShort() {
    placementHomeEntityAwareDTO.getEntity().setpZipNo("1");
    check("R-03232");
  }

  @Test
  public void testZipNoLong() {
    placementHomeEntityAwareDTO.getEntity().setpZipNo("11111111");
    check("R-03232");
  }

  @Test
  public void testZipNoFormat() {
    placementHomeEntityAwareDTO.getEntity().setpZipNo("aa");
    check("R-03232");
  }

  @Test
  public void testZipNoEmpty() {
    placementHomeEntityAwareDTO.getEntity().setZipNo(null);
    check("R-03232");
  }
  
  //P zip

  @Test
  public void testpZipNoShort() {
    placementHomeEntityAwareDTO.getEntity().setpZipNo("1");
    check("R-03232");
  }

  @Test
  public void testpZipNoLong() {
    placementHomeEntityAwareDTO.getEntity().setpZipNo("11111111");
    check("R-03232");
  }

  @Test
  public void testpZipNoFormat() {
    placementHomeEntityAwareDTO.getEntity().setpZipNo("aa");
    check("R-03232");
  }

  @Test
  public void testpZipNoEmpty() {
    placementHomeEntityAwareDTO.getEntity().setpZipNo(null);
    check("R-03232");
  }


  //La P zip

  @Test
  public void testLaPZipNoShort() {
    placementHomeEntityAwareDTO.getEntity().setLaPZipno("1");
    check("R-03232");
  }

  @Test
  public void testLaPZipNoLong() {
    placementHomeEntityAwareDTO.getEntity().setLaPZipno("11111111");
    check("R-03232");
  }

  @Test
  public void testLaPZipNoFormat() {
    placementHomeEntityAwareDTO.getEntity().setLaPZipno("aa");
    check("R-03232");
  }

  @Test
  public void testLaPZipNoEmpty() {
    placementHomeEntityAwareDTO.getEntity().setLaPZipno(null);
    check("R-03232");
  }

  //SCP zip no

  @Test
  public void testSCPZipNoShort() {
    placementHomeEntityAwareDTO.getEntity().getPrimarySubstituteCareProvider().setZipNo("1");
    check("R-03232");
  }

  @Test
  public void testSCPZipNoLong() {
    placementHomeEntityAwareDTO.getEntity().getPrimarySubstituteCareProvider().setZipNo("11111111");
    check("R-03232");
  }

  @Test
  public void testSCPZipNoFormat() {
    placementHomeEntityAwareDTO.getEntity().getPrimarySubstituteCareProvider().setZipNo("aaa");
    check("R-03232");
  }

  @Test
  public void testSCPZipNoEmpty() {
    placementHomeEntityAwareDTO.getEntity().getPrimarySubstituteCareProvider().setZipNo(null);
    check("R-03232");
  }
}
