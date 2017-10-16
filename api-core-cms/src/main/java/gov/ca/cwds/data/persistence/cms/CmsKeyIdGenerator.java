package gov.ca.cwds.data.persistence.cms;

import java.math.BigDecimal;
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
   * Self-validating bean class for staff id.
   * 
   * <p>
   * javax.validation only works on real "bean" classes, not Java native classes like String or
   * Long. Therefore, wrap the incoming staff id in a small class, which follows the Java Bean
   * specification (i.e., getters and setters).
   * </p>
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

  private static final int LEN_UIIDSTAFFID = 6; // for converting a key to a UI identifier
  private static final int LEN_UIIDTIMESTAMP = 13;

  private static final float nSHIFT_HSECOND = 1.71798692E10f; // NOSONAR 34 bit shift (2 ^ 34)

  private static final float nSHIFT_SECOND = 2.68435456E8f; // NOSONAR 28 bit shift (2 ^ 28)

  private static final float nSHIFT_MINUTE = 4194304; // NOSONAR 22 bit shift (2 ^ 22)

  private static final float nSHIFT_HOUR = 131072; // NOSONAR 17 bit shift (2 ^ 17)

  private static final float nSHIFT_DAY = 4096; // NOSONAR 12 bit shift (2 ^ 12)

  private static final float nSHIFT_MONTH = 256; // NOSONAR 8 bit shift (2 ^ 8)

  private static final float nSHIFT_YEAR = 1; // NOSONAR 0 bit shift (2 ^ 0)

  private static final BigDecimal[] POWER_BASE10 = {BigDecimal.valueOf(1.000000000000000e+000f),
      BigDecimal.valueOf(1.000000000000000e+001f), BigDecimal.valueOf(1.000000000000000e+002f),
      BigDecimal.valueOf(1.000000000000000e+003f), BigDecimal.valueOf(1.000000000000000e+004f),
      BigDecimal.valueOf(1.000000000000000e+005f), BigDecimal.valueOf(1.000000000000000e+006f),
      BigDecimal.valueOf(1.000000000000000e+007f), BigDecimal.valueOf(1.000000000000000e+008f),
      BigDecimal.valueOf(1.000000000000000e+009f), BigDecimal.valueOf(1.000000000000000e+010f),
      BigDecimal.valueOf(1.000000000000000e+011f), BigDecimal.valueOf(1.000000000000000e+012f),
      BigDecimal.valueOf(1.000000000000000e+013f), BigDecimal.valueOf(1.000000000000000e+014f),
      BigDecimal.valueOf(1.000000000000000e+015f), BigDecimal.valueOf(1.000000000000000e+016f),
      BigDecimal.valueOf(1.000000000000000e+017f), BigDecimal.valueOf(1.000000000000000e+018f)};

  private static final BigDecimal[] POWER_BASE62 = {BigDecimal.valueOf(1.000000000000000e+000),
      BigDecimal.valueOf(6.200000000000000e+001), BigDecimal.valueOf(3.844000000000000e+003),
      BigDecimal.valueOf(2.383280000000000e+005), BigDecimal.valueOf(1.477633600000000e+007),
      BigDecimal.valueOf(9.161328320000000e+008), BigDecimal.valueOf(5.680023558400000e+010),
      BigDecimal.valueOf(3.521614606208000e+012), BigDecimal.valueOf(2.183401055848960e+014),
      BigDecimal.valueOf(1.353708654626355e+016), BigDecimal.valueOf(8.392993658683402e+017),
      BigDecimal.valueOf(5.203656068383710e+019), BigDecimal.valueOf(3.226266762397900e+021),
      BigDecimal.valueOf(2.000285392686698e+023), BigDecimal.valueOf(1.240176943465753e+025),
      BigDecimal.valueOf(7.689097049487666e+026), BigDecimal.valueOf(4.767240170682353e+028),
      BigDecimal.valueOf(2.955688905823059e+030), BigDecimal.valueOf(1.832527121610297e+032)};

  private static final char[] ALPHABET = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
      'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
      'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
      'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

  /**
   * Static class only, do not instantiate.
   */
  CmsKeyIdGenerator() {
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
    return ts == null ? createTimestampStr()
        : doubleToStrN(7, timestampToDouble(getTimestampSeed(ts)), POWER_BASE62);
  }

  /**
   * Format the CMS timestamp String, the last 7 characters of the key.
   * 
   * <p>
   * Code taken from the original C++ algorithm, designed for a fat client Visual Basic application.
   * In that world of dial-up modems, the inefficiency of waiting on hundredths of a second for a
   * single user was acceptable. Obviously, this approach makes little sense today in the age of web
   * servers and pervasive, wireless internet connections.
   * </p>
   * 
   * @return CMS formatted timestamp
   * @throws ParseException on parsing error
   */
  protected String createTimestampStr() throws ParseException {
    double nTimestamp = 0;
    double nPreviousTimestamp = 0; // previous value - used for UNIQUENESS!

    // TODO: #145948067: make previous timestamp thread-safe.
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
    ret += (double) cal.get(Calendar.HOUR_OF_DAY) * nSHIFT_HOUR;
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
  public String doubleToStrN(int dstLen, double src, final BigDecimal[] powers) {
    int i;
    int p = 0;
    double integral;
    double raw;
    final char[] dest = new char[20];

    final BigDecimal bdSrc = BigDecimal.valueOf(src);

    // Determine the largest power of the number.
    for (i = 0; bdSrc.doubleValue() >= powers[i].doubleValue(); i++, p++); // NOSONAR

    // Left-pad the string with the destination string width.
    final int pad = dstLen - p;

    if (pad < 0) {
      throw new ServiceException("invalid pad value");
    } else {
      for (i = 0; i < pad; i++) {
        dest[i] = ALPHABET[0];
      }

      for (i = 0; i < p; i++) {
        raw = src / powers[p - i - 1].doubleValue();

        // Break down the number and convert the integer portion to a character.
        integral = (int) raw;
        dest[i + pad] = ALPHABET[(int) Math.abs(integral)];
        src -= (integral * powers[p - i - 1].doubleValue()); // NOSONAR
      }
    }

    final StringBuilder buf = new StringBuilder();
    for (final char c : dest) {
      if (c > 0) {
        buf.append(c);
      }
    }

    return buf.toString();
  }

  /**
   * @param src source string
   * @param base base 10 or 62
   * @param powers powers values of this base
   * @return double representation of the string
   */
  protected double strToDouble(String src, int base, final BigDecimal[] powers) {
    double ret = 0;
    final int nLen = src.length();
    int power;

    for (int i = 0; i < nLen; i++) {
      final char c = src.charAt(i);
      for (power = 0; power < base; power++) {

        // Find the character in the conversion table and add to the value.
        if (ALPHABET[power] == c) {
          ret += (power * powers[nLen - i - 1].doubleValue());
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
   * Overload. Generate 10 character, base-62 key from given staff id and timestamp.
   * 
   * @param staffId 3-char, base-62 staff id
   * @param ts timestamp to use or null for current date/time
   * @return generated 10 character, base-62 key
   */
  protected String makeKey(final String staffId, final Date ts) {
    return makeKey(new StringKey(staffId), ts);
  }

  /**
   * Generate 10 character, base-62 key from given staff id and timestamp.
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

  /**
   * Convert a 10 character, base 62 legacy key to base 10 in format 0000-0000-0000-0000000. Legacy
   * code refers to this as a UI identifier.
   * 
   * @param key 10 character, base-62 legacy key
   * @return UI identifier in format 0000-0000-0000-0000000. If provided key is null or enpty, then
   *         null is returned.
   */
  public static String getUIIdentifierFromKey(String key) {
    if (StringUtils.isBlank(key)) {
      return null;
    }

    final String tsB62 = key.substring(0, LEN_KEYTIMESTAMP);
    final String staffB62 = key.substring(LEN_KEYTIMESTAMP);
    LOGGER.trace("strTimestamp={}, strStaffId={}", tsB62, staffB62);

    final String tsB10 =
        StringUtils.leftPad(String.valueOf(Base62.toBase10(tsB62)), LEN_UIIDTIMESTAMP, '0');
    final String staffB10 =
        StringUtils.leftPad(String.valueOf(Base62.toBase10(staffB62)), LEN_UIIDSTAFFID, '0');

    LOGGER.trace("tsB10={}, staffB10={}", tsB10, staffB10);

    final StringBuilder buf = new StringBuilder();
    buf.append(tsB10.substring(0, 4)).append('-').append(tsB10.substring(4, 8)).append('-')
        .append(tsB10.substring(8, 12)).append('-').append(tsB10.substring(12))
        .append(staffB10.substring(0));

    return buf.toString();
  }

}
