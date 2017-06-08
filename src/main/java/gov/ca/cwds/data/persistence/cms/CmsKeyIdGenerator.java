package gov.ca.cwds.data.persistence.cms;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.resources.ResourceParamValidator;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * <p>
 * Java port of gov.ca.cwds.rest.util.jni.KeyJNI and underlying shared library, cws_randgen.cpp.
 * </p>
 * 
 * <p>
 * To generateXYZ an identifier, the current date/timestamp is rearranged as shown below, placing
 * less-significant time units into more-significant fields. This convolution provides better
 * "hashing" into cache and the database.
 * </p>
 *
 * <blockquote>
 * 
 * <pre>
 *   !   ~8 bits     !   6 bits  !   6 bits  ! 5 bits  ! 5 bits  !4 bits !    8 bits     !
 *   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *   !  hundredths   !  seconds  !  minutes  !  hours  !   day   !month-1!  year - 1900  !
 *   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * bytes !       :       !       :       !       :       !       :       !       :       !
 * </pre>
 * 
 * </blockquote>
 *
 * As shown above, some bit values would produce unrealistic dates/times. For example, month values
 * of 0000-1011 represent January-December, but the field has room for unrealistic months 1100-1111.
 * Similarly unrealistic values could be placed into other fields. The first and last fields are of
 * special interest.
 *
 * <blockquote>
 * 
 * <pre>
 *   hundredths:  Although shown as an 8-bit field here, not all unrealistic values are usable,
 *                since the overall number must fit into seven base-62 characters. That limits
 *                the hundredths field to values from 0 to 204.
 *
 *   year:        This algorithm supports years from 1900 to 2155.
 * </pre>
 * 
 * </blockquote>
 *
 * <p>
 * Note that CWS/CMS has made use of <strong>unrealistic</strong> values on some occasions. For
 * example, the project may need to generateXYZ many identifiers during a minimum outage window. In
 * order to make all of those generated identifiers correspond to the date/hour range of the outage,
 * project-generated identifiers may coerce unrealistic values for minutes, seconds, and hundredths.
 * This explains why some identifiers may translate to timestamps with 7 digits (rather than the
 * usual 6) after the final decimal point -- It may be showing more than 99 "hundredths"! This also
 * means that the longest formatted date/timestamp output is 27 characters (including punctuation),
 * although it will usually fit in 26.
 * </p>
 *
 * <p>
 * Once packed into the bit arrangement shown above, the number is converted to seven base-62
 * characters, using first the digits 0-9, then uppercase letters, then lowercase letters.
 * </p>
 *
 * <p>
 * The final 3 characters of the identifier indicate the staffperson (or project process) which
 * created the row.
 * </p>
 *
 * <p>
 * For the User Interface, the identifier can also be converted into a formatted 19-digit decimal
 * number. In the 19-digit format, the first 13 decimal digits are a conversion of the first seven
 * base-62 characters, while the last 6 decimal digits are an independent conversion of the last 3
 * base-62 characters (ie, staffperson ID) from the identifier. The 19 digits are formatted into
 * three groups of four digits, followed by a final group of seven digits, so the full string length
 * is 22 characters with punctuation.
 * </p>
 *
 * <p>
 * In this source file, the 3 formats are referred to as:
 * </p>
 * 
 * <blockquote>
 * 
 * <pre>
 *   Key:            10 base-62 characters (0-9, A-Z, a-z):               tttttttppp
 *   UI Identifier:  19 decimal digits (22 characters with punctuation):  tttt-tttt-tttt-tpppppp
 *   Timestamp:      26 characters in DB2 format:                         YYYY-MM-DD-hh.mm.ss.xx0000
 *         or rarely 27 (as explained above):                             YYYY-MM-DD-hh.mm.ss.xxx0000
 * where:
 *   All timestamp fields include leading zeros.
 *   t... represents convolved time
 *   p... represents the staffperson
 *   YYYY represents the year
 *   MM   represents the month
 *   DD   represents the day of the month
 *   hh   represents the hour (00 to 23)
 *   mm   represents minutes
 *   ss   represents seconds
 *   x... represents hundredths of seconds
 * </pre>
 * 
 * </blockquote>
 * 
 * @author CWDS API Team
 */
public final class CmsKeyIdGenerator {

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsKeyIdGenerator.class);

  private static final String DEFAULT_USER_ID = "0X5";

  /**
   * javax.validation only works on real "bean" classes, not Java native classes like String or
   * Long. Therefore, we must wrap the incoming staff id in a small class, which follows the Java
   * Bean specification (i.e., getters and setters).
   * 
   * @author CWDS API Team
   */
  public static final class StringKey {

    @NotNull
    @Size(min = 3, max = 3)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String value;

    /**
     * Constructor.
     * 
     * @param value String to evaluate
     */
    public StringKey(String value) {
      this.value = value;
    }

    @SuppressWarnings("javadoc")
    public String getValue() {
      return value;
    }

    @SuppressWarnings("javadoc")
    public void setValue(String value) {
      this.value = value;
    }
  }

  /**
   * Utility struct class stores details of CWDS key decomposition.
   */
  @SuppressWarnings("javadoc")
  public static final class KeyDetail {
    public String key; // NOSONAR
    public String staffId; // NOSONAR
    public String UITimestamp; // NOSONAR
    public String PTimestamp; // NOSONAR
  }

  private static final int BASE_62_SIZE = 62;

  private static final int LEN_KEY = 10;
  private static final int LEN_KEYSTAFFID = 3;
  private static final int LEN_KEYTIMESTAMP = 7;
  private static final int nSZ_UISTAFFID = 6;
  private static final int nSZ_UITIMESTAMP = 26;
  private static final int nSZ_UIIDENTIFIER = 22;
  private static final int nSZ_PTIMESTAMP = 11;

  private static final int nSZ_USERID = 8;

  private static final int nSZ_POWVEC = 19;

  private static final int nSZ_UIIDSTAFFID = 6; // for converting a key to a UI identifier
  private static final int nSZ_UIIDTIMESTAMP = 13;

  private static final float nSHIFT_HSECOND = 1.71798692E10f; // NOSONAR 34 bit shift (2 to the 34th
  // power)

  private static final float nSHIFT_SECOND = 2.68435456E8f; // NOSONAR 28 bit shift (2 to the 28th
                                                            // power)

  private static final float nSHIFT_MINUTE = 4194304; // NOSONAR 22 bit shift (2 to the 22nd power)

  private static final float nSHIFT_HOUR = 131072; // NOSONAR 17 bit shift (2 to the 17th power)

  private static final float nSHIFT_DAY = 4096; // NOSONAR 12 bit shift (2 to the 12th power)

  private static final float nSHIFT_MONTH = 256; // NOSONAR 8 bit shift (2 to the 8th power)

  private static final float nSHIFT_YEAR = 1; // NOSONAR 0 bit shift (2 to the 0th power)

  private static final double[] POWER_BASE10 = {1.000000000000000e+000f, 1.000000000000000e+001f,
      1.000000000000000e+002f, 1.000000000000000e+003f, 1.000000000000000e+004f,
      1.000000000000000e+005f, 1.000000000000000e+006f, 1.000000000000000e+007f,
      1.000000000000000e+008f, 1.000000000000000e+009f, 1.000000000000000e+010f,
      1.000000000000000e+011f, 1.000000000000000e+012f, 1.000000000000000e+013f,
      1.000000000000000e+014f, 1.000000000000000e+015f, 1.000000000000000e+016f,
      1.000000000000000e+017f, 1.000000000000000e+018f};

  private static final double[] POWER_BASE62 = {1.000000000000000e+000, 6.200000000000000e+001,
      3.844000000000000e+003, 2.383280000000000e+005, 1.477633600000000e+007,
      9.161328320000000e+008, 5.680023558400000e+010, 3.521614606208000e+012,
      2.183401055848960e+014, 1.353708654626355e+016, 8.392993658683402e+017,
      5.203656068383710e+019, 3.226266762397900e+021, 2.000285392686698e+023,
      1.240176943465753e+025, 7.689097049487666e+026, 4.767240170682353e+028,
      2.955688905823059e+030, 1.832527121610297e+032};

  private static final char[] BASE64_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
      'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
      'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
      'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

  /**
   * Static class only, do not instantiate.
   */
  private CmsKeyIdGenerator() {
    // Static class only, do not instantiate.
  }

  /**
   * Format CMS timestamp String, the last 7 characters of the key.
   * 
   * @param ts seed timestamp
   * @return CMS formatted timestamp
   * @throws ParseException on parsing error
   */
  protected String createTimestampStr(final Date ts) throws ParseException {
    return ts == null ? createTimestampStr(null)
        : doubleToStrN(7, timestampToDouble(getTimestampSeed(ts)), POWER_BASE62);
  }

  /**
   * Format the CMS timestamp String, the last 7 characters of the key.
   * 
   * <p>
   * Code taken from the original C++ algorithm.
   * </p>
   * 
   * @return CMS formatted timestamp
   * @throws ParseException on parsing error
   */
  protected String createTimestampStr() throws ParseException {
    double nTimestamp = 0;
    double nPreviousTimestamp = 0; // previous value - used for UNIQUENESS!

    while (true) { // NOSONAR
      nTimestamp = timestampToDouble(getTimestampSeed(null));

      // If the timestamp value is the same as before, stay in the loop.
      // Otherwise, break out since it is unique.
      if (nTimestamp == nPreviousTimestamp) { // NOSONAR
        Thread.yield();
        continue;
      } else {
        break;
      }
    }

    // Convert the timestamp number to a base-62 string.
    return doubleToStrN(7, nTimestamp, POWER_BASE62);
  }

  /**
   * @param cal preferred timestamp
   * @return the timestamp in double
   */
  public double timestampToDouble(final Calendar cal) {
    double ret = 0;
    ret += (double) cal.get(Calendar.MILLISECOND) / 10 * nSHIFT_HSECOND;
    ret += (double) cal.get(Calendar.SECOND) * nSHIFT_SECOND;
    ret += (double) cal.get(Calendar.MINUTE) * nSHIFT_MINUTE;
    ret += (double) cal.get(Calendar.HOUR) * nSHIFT_HOUR;
    ret += (double) cal.get(Calendar.DATE) * nSHIFT_DAY;
    ret += (double) (cal.get(Calendar.MONTH)) * nSHIFT_MONTH;
    ret += (double) (cal.get(Calendar.YEAR) - 1900) * nSHIFT_YEAR;
    return ret;
  }

  /**
   * @param dstLen the string width
   * @param src source value
   * @param powers the power vector for the destination base
   * @return the double to string
   */
  public String doubleToStrN(int dstLen, double src, final double[] powers) {
    int i;
    int power = 0;
    double nInteger;
    char[] dest = new char[20];

    // Determine the largest power of the number.
    for (i = 0; src >= powers[i]; i++, power++) {
      // Just increment power.
    }

    // LOGGER.debug("power={}, i={}", power, i);
    // Use the destination string width to left-pad the string.
    final int pad = dstLen - power;
    // LOGGER.debug("pad={}", pad);

    if (pad < 0) {
      throw new ServiceException("invalid nPad value");
    } else {
      for (i = 0; i < pad; i++) {
        dest[i] = BASE64_CHARS[0];
      }

      for (i = 0; i < power; i++) {
        nInteger = src / powers[power - i - 1];
        double finalValue = Math.floor(nInteger);
        dest[i + pad] = BASE64_CHARS[(int) Math.abs(finalValue)];
        src -= (finalValue * powers[power - i - 1]); // NOSONAR
      }
    }

    return String.valueOf(dest);
  }

  /**
   * @param src source string
   * @param base base 10 or 62
   * @param powers powers values of this base
   * @return double representation of the string
   */
  protected double strToDouble(String src, int base, final double[] powers) {
    double ret = 0;
    final int nLen = src.length();
    int power;

    for (int i = 0; i < nLen; i++) {
      final char c = src.charAt(i);
      for (power = 0; power < base; power++) {

        // Find the character in the conversion table and add to the value.
        if (BASE64_CHARS[power] == c) {
          ret += (power * powers[nLen - i - 1]);
          break;
        }
      }

      if (power == base) {
        LOGGER.warn("Character too big base? {}");
        return -1;
      }
    }

    return ret;
  }

  /**
   * Get preferred timestamp seed, either provided or current date/time if null.
   * 
   * @param ts timestamp to use or null for current date/time
   * @return Calendar set to preferred timestamp
   * @throws ParseException on date/time parsing error
   */
  protected final Calendar getTimestampSeed(final Date ts) throws ParseException {
    Calendar cal = Calendar.getInstance();

    if (ts != null) {
      cal.setTimeInMillis(ts.getTime());
    }

    return cal;
  }

  /**
   * Overload. Generate ten character, base62 key from given staff id and timestamp.
   * 
   * @param staffId 3-char, base-62 staff id
   * @param ts timestamp to use or null for current date/time
   * @return generated ten character, base-62 key
   */
  protected String makeKey(final String staffId, final Date ts) {
    return makeKey(new StringKey(staffId), ts);
  }

  /**
   * Generate ten character, base62 key from given staff id and timestamp.
   * 
   * @param wrap the wrap
   * @param ts timestamp to use or null for current date/time
   * @return the key from staffID
   */
  protected String makeKey(final StringKey wrap, final Date ts) {
    try {
      ResourceParamValidator.<StringKey>validate(wrap);
      return createTimestampStr(ts).trim() + wrap.getValue();
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  /**
   * Simplified overload. Generate an identifier with the given staff id and current timestamp.
   * 
   * @param staffId the staffId
   * @return the unique key from staffId
   */
  public static String generate(String staffId) {
    return generate(staffId, new Date());
  }

  /**
   * Generate an identifier with the given staff id and current timestamp.
   * 
   * @param staffId three char staff id
   * @param ts timestamp to use
   * @return unique key from staff id and timestamp
   */
  public static String generate(String staffId, final Date ts) {
    final CmsKeyIdGenerator rend = new CmsKeyIdGenerator();
    return rend.makeKey(!StringUtils.isBlank(staffId) ? staffId : DEFAULT_USER_ID, ts);
  }

  //// -----------------------------------------------------------------------------
  //// Function: GetUIIdentifierFromKey
  ////
  //// Description: Obtains UI displayable identifier from the generated key.
  ////
  //// Inputs: szKey - the previously generated key.
  ////
  //// Outputs: szUIIdentifier - the identifier (in base 10)
  //// An empty string on an error.
  //// RETURNS - <none>
  //// -----------------------------------------------------------------------------
  // void WINAPI _export GetUIIdentifierFromKey(const char *szKey, char *szUIIdentifier) {
  // using namespace std;
  // char szTimestamp[nSZ_KEYTIMESTAMP + 1];
  // char szStaffId[nSZ_KEYSTAFFID + 1];
  // string sConvertKeyToUI;
  //
  // try {
  // AssertTrace(strlen(szKey) == nSZ_KEY, "'%s' has an invalid key string length.", szKey);
  // AssertTrace(AfxIsValidAddress(szUIIdentifier, nSZ_UIIDENTIFIER + 1), "Invalid address specified
  //// for szUIIdentifier.");
  //
  // StrCpyN(szTimestamp, szKey, nSZ_KEYTIMESTAMP);
  // StrCpyN(szStaffId, szKey + nSZ_KEYTIMESTAMP, nSZ_KEYSTAFFID);
  //
  // // Convert the entire key to a displayable string (base 10).
  // BaseConvert(szUIIdentifier, 10, nSZ_UIIDTIMESTAMP, szTimestamp, nDEFAULT_BASE);
  // BaseConvert(szUIIdentifier + nSZ_UIIDTIMESTAMP, 10, nSZ_UIIDSTAFFID, szStaffId, nDEFAULT_BASE);
  //
  // sConvertKeyToUI = szUIIdentifier; // convert to std::string
  // sConvertKeyToUI = sConvertKeyToUI.substr(0,4) + "-" + sConvertKeyToUI.substr(4, 4) + "-"
  // + sConvertKeyToUI.substr(8, 4) + "-" + sConvertKeyToUI.substr(12); // insert 3 dashes every 4th
  //// character
  // StrCpyN(szUIIdentifier, sConvertKeyToUI.c_str(), nSZ_UIIDENTIFIER);
  // } catch (std::exception e) {
  // cerr << "***** CAUGHT EXCEPTION! ***** : " << e.what() << endl;
  // szUIIdentifier[0] = '\0';
  // }
  // }

  protected String baseConvert(int dstBase, int dstLen, String src, int srcBase) {
    double d = 0;

    // Source string => double.
    if (srcBase == BASE_62_SIZE) {
      d = strToDouble(src, srcBase, POWER_BASE62);
    }

    // double => string of specified base.
    return doubleToStrN(dstLen, d, dstBase == 10 ? POWER_BASE10 : POWER_BASE62);
  }

  public String getUIIdentifierFromKey(String key) {
    String strTimestamp = key.substring(0, LEN_KEYTIMESTAMP);
    String strStaffId = key.substring(LEN_KEYTIMESTAMP, LEN_KEYTIMESTAMP + LEN_KEYSTAFFID);

    String first = baseConvert(10, nSZ_UIIDTIMESTAMP, strTimestamp, BASE_62_SIZE);
    String second = baseConvert(10, nSZ_UIIDSTAFFID, strStaffId, BASE_62_SIZE);

    String whole = first + second;
    LOGGER.debug("whole={}", whole);

    StringBuilder buf = new StringBuilder();
    buf.append(whole.substring(0, 4));

    return buf.toString();
  }

  /**
   * TODO: move to a test class.
   * 
   * @param args command line
   * @throws InterruptedException checks the exception
   */
  public static void main(String[] args) throws InterruptedException {
    CmsKeyIdGenerator rend = new CmsKeyIdGenerator();
    rend.getUIIdentifierFromKey("5Y3vKVs0X5");
  }

}
