package gov.ca.cwds.data.std;

import org.apache.commons.lang3.StringUtils;

/**
 * Cobertura doesn't recognize static methods on interfaces without ASM 5.x. Until Cobertura fully
 * supports Java 8 features, put static address support methods here.
 * 
 * @author CWDS API Team
 */
public class ApiAddressUtils {

  private ApiAddressUtils() {
    // Hidden.
  }

  /**
   * Format zip4 as a zero-padded String.
   * 
   * @param zip4 zip4 to format
   * @return zip4 formatted as String
   */
  public static String formatZip4(Short zip4) {
    return zip4 != null && zip4 != 0 ? StringUtils.leftPad(String.valueOf(zip4.intValue()), 4, '0')
        : null;
  }

  /**
   * Format zip5 as a zero-padded String.
   * 
   * @param zip5 zip5 to format
   * @return zip5 formatted as String
   */
  public static String formatZip5(Short zip5) {
    return zip5 != null && zip5 != 0 ? StringUtils.leftPad(String.valueOf(zip5.intValue()), 5, '0')
        : null;
  }


}
