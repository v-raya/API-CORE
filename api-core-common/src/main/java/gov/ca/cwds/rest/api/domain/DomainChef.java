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
   * Common timestamp format for legacy DB.
   */
  public static final String LEGACY_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

  /**
   * Strict timestamp format (yyyy-MM-dd'T'HH:mm:ss.SSSZZ).
   */
  public static final String TIMESTAMP_STRICT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";

  /**
   * ISO8601 timestamp format (yyyy-MM-dd'T'HH:mm:ss.SSS'Z').
   */
  public static final String TIMESTAMP_ISO8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  /**
   * Common time format for domain classes.
   */
  public static final String TIME_FORMAT = "HH:mm:ss";

  protected static final String ZIP_ALL_ZEROES = "00000";
  protected static final Pattern ZIPCODE_PATTERN = Pattern.compile("0*([0-9]*)");

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
   * Transform camel case key/name to JSON suitable key.
   * 
   * @param in incoming String to standardize
   * @return purified, JSON suitable key name
   */
  public static String camelCaseToLowerUnderscore(String in) {
    final String regex = "([A-Z])";
    final String replacement = "_$1";
    return in.replaceAll(regex, replacement).toLowerCase().replaceAll("_$", "");
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
   * @param date date to cook
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
   * @param date date to cook
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
   * 
   * @param timestamp Timestamp to convert into String.
   * @return Timestamp converted into strict format String
   *         {@link DomainChef#TIMESTAMP_STRICT_FORMAT}
   */
  public static String cookStrictTimestamp(Date timestamp) {
    if (timestamp != null) {
      DateFormat df = new SimpleDateFormat(TIMESTAMP_STRICT_FORMAT);
      return df.format(timestamp);
    }
    return null;
  }

  /**
   * @param date date to cook
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
   * @param date date to uncook
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
   * @param timestamp timestamp to uncook
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
   * @param timestamp timestamp to convert into Date
   * @return Date A Date object based on strict timestamot format
   *         {@link DomainChef#TIMESTAMP_STRICT_FORMAT}
   */
  public static Date uncookStrictTimestampString(String timestamp) {
    String trimTimestamp = StringUtils.trim(timestamp);
    if (StringUtils.isNotEmpty(trimTimestamp)) {
      try {
        DateFormat df = new SimpleDateFormat(TIMESTAMP_STRICT_FORMAT);
        return df.parse(trimTimestamp);
      } catch (Exception e) {
        throw new ApiException(e);
      }
    }
    return null;
  }

  /**
   * @param timestamp time to uncook
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
   * Extract Date object from ISO8601 formatted String
   * 
   * @param timestamp the string to extract from
   * @return Date
   */
  public static Date uncookISO8601Timestamp(String timestamp) {
    String trimTimestamp = StringUtils.trim(timestamp);
    if (StringUtils.isNotEmpty(trimTimestamp)) {
      try {
        DateFormat df = new SimpleDateFormat(TIMESTAMP_ISO8601_FORMAT);
        return df.parse(trimTimestamp);
      } catch (Exception e) {
        throw new ApiException(e);
      }
    }
    return null;
  }

  /**
   * @param date date to cook
   * @return String in TIME_FORMAT
   */
  public static String cookISO8601Timestamp(Date date) {
    if (date != null) {
      DateFormat df = new SimpleDateFormat(TIMESTAMP_ISO8601_FORMAT);
      return df.format(date);
    }
    return null;
  }

  /**
   * @param zipcodeNumber zip to cook
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
   * @param zipcode zip to uncook
   * @return Integer
   */
  public static Integer uncookZipcodeString(String zipcode) {
    if (StringUtils.isBlank(zipcode)) {
      return 0;
    }
    Matcher matcher = ZIPCODE_PATTERN.matcher(zipcode);
    if (matcher.matches()) {
      try {
        return Integer.valueOf(matcher.group(1));
      } catch (NumberFormatException e) {
        throw new ApiException(
            MessageFormat.format("Unable to convert zipcode to Integer '{0}' = {0}", zipcode), e);
      }
    } else {
      throw new ApiException(
          MessageFormat.format("Unable to uncook zipcode string '{0}' = {0}", zipcode));
    }
  }
}
