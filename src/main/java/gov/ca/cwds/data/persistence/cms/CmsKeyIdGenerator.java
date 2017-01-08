package gov.ca.cwds.data.persistence.cms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.resources.ResourceParamValidator;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Java port of gov.ca.cwds.rest.util.jni.KeyJNI and underlying shared library.
 * 
 * @author CWDS API Team
 */
public class CmsKeyIdGenerator {

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsKeyIdGenerator.class);

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

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }

  /**
   * Utility struct class stores details of CWDS key decomposition.
   */
  public static final class KeyDetail {
    public String key;
    public String staffId;
    public String UITimestamp;
    public String PTimestamp;
  }

  private static final int nSZ_POWVEC = 19;

  private static final int nMAX_BASE = 62;

  private static final int nDEFAULT_BASE = 62;

  private static final int nSZ_UIIDSTAFFID = 6; // for converting a key to a UI identifier

  private static final int nSZ_UIIDTIMESTAMP = 13;

  private static final float nSHIFT_HSECOND = 1.71798692E10f; // 34 bit shift (2 to the 34th power)

  private static final float nSHIFT_SECOND = 2.68435456E8f; // 28 bit shift (2 to the 28th power)

  private static final float nSHIFT_MINUTE = 4194304; // 22 bit shift (2 to the 22nd power)

  private static final float nSHIFT_HOUR = 131072; // 17 bit shift (2 to the 17th power)

  private static final float nSHIFT_DAY = 4096; // 12 bit shift (2 to the 12th power)

  private static final float nSHIFT_MONTH = 256; // 8 bit shift (2 to the 8th power)

  private static final float nSHIFT_YEAR = 1; // 0 bit shift (2 to the 0th power)

  private static final double anPowVec10[] = {1.000000000000000e+000f, 1.000000000000000e+001f,
      1.000000000000000e+002f, 1.000000000000000e+003f, 1.000000000000000e+004f,
      1.000000000000000e+005f, 1.000000000000000e+006f, 1.000000000000000e+007f,
      1.000000000000000e+008f, 1.000000000000000e+009f, 1.000000000000000e+010f,
      1.000000000000000e+011f, 1.000000000000000e+012f, 1.000000000000000e+013f,
      1.000000000000000e+014f, 1.000000000000000e+015f, 1.000000000000000e+016f,
      1.000000000000000e+017f, 1.000000000000000e+018f};

  private static final double anPowVec62[] = {1.000000000000000e+000, 6.200000000000000e+001,
      3.844000000000000e+003, 2.383280000000000e+005, 1.477633600000000e+007,
      9.161328320000000e+008, 5.680023558400000e+010, 3.521614606208000e+012,
      2.183401055848960e+014, 1.353708654626355e+016, 8.392993658683402e+017,
      5.203656068383710e+019, 3.226266762397900e+021, 2.000285392686698e+023,
      1.240176943465753e+025, 7.689097049487666e+026, 4.767240170682353e+028,
      2.955688905823059e+030, 1.832527121610297e+032};

  private static final char acConvTbl[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
      'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
      'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
      'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

  /**
   * Core method.
   * 
   * @param staffId 3-char, base-62 staff id
   * @return generated 10 char, base-62 key
   */
  public String generateKeyFromStaff(final String staffId) {
    return generateKeyFromStaff(new StringKey(staffId));
  }

  public String generateKeyFromStaff(final StringKey wrap) {
    try {
      ResourceParamValidator.<StringKey>validate(wrap);
      return createTimestampStr().trim() + wrap.getValue();
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  protected String createTimestampStr() {
    double nTimestamp = 0;
    double nPreviousTimestamp = 0; // previous value - used for UNIQUENESS!!!

    while (true) {
      nTimestamp = timestampToDouble(getCurrentDate());

      // If the timestamp value is the same as before, stay in the loop.
      // otherwise, break out since it is unique.
      if (nTimestamp == nPreviousTimestamp) {
        // Thread.yield();
        continue;
      } else {
        break;
      }
    }
    nPreviousTimestamp = nTimestamp; // save the current timestamp

    // Convert the timestamp number to a base-62 string.
    return doubleToStrN(7, nTimestamp, anPowVec62);
  }

  public double timestampToDouble(final Calendar localDateTime) {

    double nTimestamp = 0;
    nTimestamp += (double) localDateTime.get(Calendar.MILLISECOND) / 10 * nSHIFT_HSECOND;
    nTimestamp += (double) localDateTime.get(Calendar.SECOND) * nSHIFT_SECOND;
    nTimestamp += (double) localDateTime.get(Calendar.MINUTE) * nSHIFT_MINUTE;
    nTimestamp += (double) localDateTime.get(Calendar.HOUR) * nSHIFT_HOUR;
    nTimestamp += (double) localDateTime.get(Calendar.DAY_OF_WEEK) * nSHIFT_DAY;
    nTimestamp += (double) (localDateTime.get(Calendar.MONTH) - 1) * nSHIFT_MONTH;
    nTimestamp += (double) (localDateTime.get(Calendar.YEAR) - 1900) * nSHIFT_YEAR;

    return nTimestamp;
  }

  public String doubleToStrN(int nDstStrWidth, Double nSrcVal, final double[] pnPowVec) {
    int i = 0;
    int nPower = 0;
    double nInteger;
    char[] szDstStr = new char[8];

    // determine the largest power of the number
    for (i = 0; nSrcVal >= pnPowVec[i]; i++, nPower++);

    LOGGER.debug("nPower::" + nPower);
    // use the destination string width to left-pad the string.
    final int nPad = nDstStrWidth - nPower;
    LOGGER.debug("nPad::" + nPad);

    if (nPad < 0) {
      throw new ServiceException("received invalid nPad value......");
    } else {
      for (i = 0; i < nPad; i++) {
        szDstStr[i] = acConvTbl[0];
      }

      for (i = 0; i < nPower; i++) {
        nInteger = nSrcVal / pnPowVec[nPower - i - 1];
        float finalValue = (float) Math.floor(nInteger);
        szDstStr[i + nPad] = acConvTbl[(int) Math.abs(finalValue)];
        nSrcVal -= (finalValue * pnPowVec[nPower - i - 1]);
      }
    }

    return String.valueOf(szDstStr);
  }

  protected final Calendar getCurrentDate() {
    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
    Calendar cal = Calendar.getInstance();
    LOGGER.debug(dateFormat.format(cal.getTime())); // 2014/08/06 16:00:22
    return cal;
  }

  public static void main(String[] args) throws InterruptedException {
    CmsKeyIdGenerator rend = new CmsKeyIdGenerator();
    StringBuilder staffid = new StringBuilder("AB1");
    for (int i = 0; i <= 400000; i++) {
      LOGGER.debug("staffId" + staffid.toString());
      String key = rend.generateKeyFromStaff(staffid.toString());
      LOGGER.debug("generated key::" + key);
    }

    Thread.sleep(10);
  }
}
