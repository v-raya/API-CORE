package gov.ca.cwds.cms.data.access.utils;

import java.util.List;

import gov.ca.cwds.cms.data.access.CWSIdentifier;

/**
 * @author CWDS CALS API Team
 */
public final class CountyUtil {

  private CountyUtil() {}

  public static String getFlag(List<CWSIdentifier> counties, int expectedId, String selectedValue,
      String rejectedValue) {
    for (CWSIdentifier county : counties) {
      if (county.getCwsId() == expectedId) {
        return selectedValue;
      }
    }
    return rejectedValue;
  }

}
