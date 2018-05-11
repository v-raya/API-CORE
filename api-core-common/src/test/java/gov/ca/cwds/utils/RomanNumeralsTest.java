package gov.ca.cwds.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test functions for RomanNumerals
 * 
 * @author CWDS API Team
 */
public class RomanNumeralsTest {

  @Test
  public void testRomanConversion_50() {
    int num = 50;
    String expected = "L";
    String actual = RomanNumerals.toRoman(num);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testRomanConversion_49() {
    int num = 49;
    String expected = "XLIX";
    String actual = RomanNumerals.toRoman(num);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testRomanConversion_137() {
    int num = 137;
    String expected = "CXXXVII";
    String actual = RomanNumerals.toRoman(num);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testRomanConversion_5() {
    int num = 5;
    String expected = "V";
    String actual = RomanNumerals.toRoman(num);
    Assert.assertEquals(expected, actual);
  }

  @Test(expected = NumberFormatException.class)
  public void testRomanConversion_0() {
    int num = 0;
    RomanNumerals.toRoman(num);
  }

  @Test(expected = NumberFormatException.class)
  public void testRomanConversion_4000() {
    int num = 4000;
    RomanNumerals.toRoman(num);
  }

  @Test
  public void testRomanConversion_3999() {
    int num = 3999;
    String expected = "MMMCMXCIX";
    String actual = RomanNumerals.toRoman(num);
    Assert.assertEquals(expected, actual);
  }

}
