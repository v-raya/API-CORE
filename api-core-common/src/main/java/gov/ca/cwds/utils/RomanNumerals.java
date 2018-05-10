package gov.ca.cwds.utils;

import java.util.Collections;

/**
 * Roman numeral utility functions.
 * 
 * @author CWDS API Team
 */
public class RomanNumerals {

  /**
   * Minimum numeric (number) represented in Roman
   */
  public static final int MIN_ROMAN_NUMBER = 1;

  /**
   * Maximum numeric (number) represented in Roman
   */
  public static final int MAX_ROMAN_NUMBER = 3999;


  /**
   * Private no-op constructor
   */
  private RomanNumerals() {
    // No-op
  }

  /**
   * Convert given number to Roman equivalent
   * 
   * @param number The number to convert, must be positive number less than 3999.
   * @return Roman numeral representing given number
   */
  public static String toRoman(int number) {
    if (number < MIN_ROMAN_NUMBER || number > MAX_ROMAN_NUMBER) {
      throw new NumberFormatException("Can not convert given number to Roman: " + number);
    }

    return String.join("", Collections.nCopies(number, "I")).replace("IIIII", "V")
        .replace("IIII", "IV").replace("VV", "X").replace("VIV", "IX").replace("XXXXX", "L")
        .replace("XXXX", "XL").replace("LL", "C").replace("LXL", "XC").replace("CCCCC", "D")
        .replace("CCCC", "CD").replace("DD", "M").replace("DCD", "CM");
  }
}
