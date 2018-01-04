package gov.ca.cwds.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestUtils {

  public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public static LocalDate localDate(String dateStr) {
    return LocalDate.parse(dateStr, DATE_FORMATTER);
  }

}
