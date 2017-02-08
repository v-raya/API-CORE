package gov.ca.cwds.rest.api.domain;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.rest.api.ApiException;

/**
 * Utility class for CWDS API domain field data conversion.
 * 
 * <p>
 * Class Naming Convention
 * </p>
 * 
 * <ul>
 * <li>"Cook": convert String parameter to strong type</li>
 * <li>"Uncook": convert strong type parameter to String</li>
 * </ul>
 *
 * <p>
 * Some methods may throw {@link ApiException}, if data conversion is logically impossible, where
 * noted. Otherwise, conversion methods may return null.
 * </p>
 * 
 * @author CWDS API Team
 */
public class DomainChef {

  /**
   * Common date format for domain classes.
   */
  public static final String DATE_FORMAT = "yyyy-MM-dd";

  /**
   * Common timestamp format for domain classes.
   */
  public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd-HH.mm.ss.SSS";

  /**
   * Common time format for domain classes.
   */
  public static final String TIME_FORMAT = "HH:mm:ss";

  protected static final String ZIP_ALL_ZEROES = "00000";
  protected static final Pattern ZIPCODE_PATTERN = Pattern.compile("0*([1-9]*)");

  /**
   * Constructor
   * 
   * Private to prevent construction
   */
  private DomainChef() {}

  /**
   * @param uncookedBoolean true or false
   * @return String "Y" or "N"
   */
  public static String cookBoolean(Boolean uncookedBoolean) {
    if (uncookedBoolean != null) {
      return Boolean.TRUE.equals(uncookedBoolean) ? "Y" : "N";
    }
    return null;
  }

  /**
   * @param cookedBoolean "Y" or "N"
   * @return Boolean true, false, or null
   */
  public static Boolean uncookBooleanString(String cookedBoolean) {
    if ("N".equalsIgnoreCase(cookedBoolean)) {
      return Boolean.FALSE;
    } else if ("Y".equalsIgnoreCase(cookedBoolean)) {
      return Boolean.TRUE;
    } else if (StringUtils.trimToNull(cookedBoolean) == null) {
      return null;
    }
    throw new ApiException(new ParseException("Unable to generate boolean", 0));
  }

  /**
   * @param date
   * @return String in DATE_FORMAT
   */
  public static String cookDate(Date date) {
    if (date != null) {
      DateFormat df = new SimpleDateFormat(DATE_FORMAT);
      return df.format(date);
    }
    return null;
  }

  /**
   * @param date
   * @return String in TIMESTAMP_FORMAT
   */
  public static String cookTimestamp(Date date) {
    if (date != null) {
      DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);
      return df.format(date);
    }
    return null;
  }

  /**
   * @param date
   * @return String in TIME_FORMAT
   */
  public static String cookTime(Date date) {
    if (date != null) {
      DateFormat df = new SimpleDateFormat(TIME_FORMAT);
      return df.format(date);
    }
    return null;
  }

  /**
   * @param date
   * @return Date
   */
  public static Date uncookDateString(String date) {
    String trimDate = StringUtils.trim(date);
    if (StringUtils.isNotEmpty(trimDate)) {
      try {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.parse(trimDate);
      } catch (Exception e) {
        throw new ApiException(e);
      }
    }
    return null;
  }

  /**
   * @param timestamp
   * @return Date
   */
  public static Date uncookTimestampString(String timestamp) {
    String trimTimestamp = StringUtils.trim(timestamp);
    if (StringUtils.isNotEmpty(trimTimestamp)) {
      try {
        DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);
        return df.parse(trimTimestamp);
      } catch (Exception e) {
        throw new ApiException(e);
      }
    }
    return null;
  }

  /**
   * @param timestamp
   * @return Date
   */
  public static Date uncookTimeString(String timestamp) {
    String trimTimestamp = StringUtils.trim(timestamp);
    if (StringUtils.isNotEmpty(trimTimestamp)) {
      try {
        DateFormat df = new SimpleDateFormat(TIME_FORMAT);
        return df.parse(trimTimestamp);
      } catch (Exception e) {
        throw new ApiException(e);
      }
    }
    return null;
  }

  /**
   * @param zipcodeNumber
   * @return String
   */
  public static String cookZipcodeNumber(Integer zipcodeNumber) {
    String zipcode = "";
    if (zipcodeNumber != null && zipcodeNumber > 0) {
      String draft = ZIP_ALL_ZEROES + zipcodeNumber.toString();
      zipcode = draft.substring(draft.length() - 5, draft.length());
    }
    return zipcode;
  }

  /**
   * @param zipcode
   * @return Integer
   */
  public static Integer uncookZipcodeString(String zipcode) {
    if (StringUtils.isBlank(zipcode)) {
      return 0;
    }
    Matcher matcher = ZIPCODE_PATTERN.matcher(zipcode);
    if (matcher.matches()) {
      try {
        return Integer.parseInt(matcher.group(1));
      } catch (NumberFormatException e) {
        throw new ApiException(
            MessageFormat.format("Unable to convert zipcode to Integer - {1}", zipcode), e);
      }
    } else {
      throw new ApiException(MessageFormat.format("Unable to uncook zipcode string {1}", zipcode));
    }
  }
}
