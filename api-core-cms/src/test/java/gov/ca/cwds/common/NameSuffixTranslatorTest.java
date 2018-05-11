package gov.ca.cwds.common;

import org.junit.Assert;
import org.junit.Test;

import gov.ca.cwds.common.NameSuffixTranslator.AlphaNameSuffix;

/**
 * Unit tests for NameSuffixTranslator.
 * 
 * @author CWDS API Team
 */
public class NameSuffixTranslatorTest {

  @Test
  public void testTranslation_2nd() {
    String source = "2nd  ";
    String expected = "II";
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_2() {
    String source = "2";
    String expected = "II";
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_49th() {
    String source = "49th";
    String expected = "XLIX";
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_49() {
    String source = "49";
    String expected = "XLIX";
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_50() {
    String source = "50";
    String expected = "L";
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_4000() {
    String source = "4000";
    String expected = "4000";
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_XYZ() {
    String source = "XYZ";
    String expected = "XYZ";
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_51() {
    String source = "51";
    String expected = "51";
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_0() {
    String source = "0";
    String expected = "0";
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_1() {
    String source = "1";
    String expected = "1";
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_null() {
    String source = null;
    String expected = null;
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_whitespaces() {
    String source = "   ";
    String expected = "   ";
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_esq() {
    String source = "esq";
    String expected = AlphaNameSuffix.ESQ.getTranslatedValue();
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_eq() {
    String source = "eq";
    String expected = AlphaNameSuffix.ESQ.getTranslatedValue();
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_esqu() {
    String source = "esqu";
    String expected = AlphaNameSuffix.ESQ.getTranslatedValue();
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_jr() {
    String source = "JR";
    String expected = AlphaNameSuffix.JR.getTranslatedValue();
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_junior() {
    String source = "JUNIOR";
    String expected = AlphaNameSuffix.JR.getTranslatedValue();
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_jnr() {
    String source = "Jnr";
    String expected = AlphaNameSuffix.JR.getTranslatedValue();
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_sr() {
    String source = "Sr";
    String expected = AlphaNameSuffix.SR.getTranslatedValue();
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_senior() {
    String source = "seNiOr";
    String expected = AlphaNameSuffix.SR.getTranslatedValue();
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_snr() {
    String source = "sNr";
    String expected = AlphaNameSuffix.SR.getTranslatedValue();
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_md() {
    String source = "md";
    String expected = AlphaNameSuffix.MD.getTranslatedValue();
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_phd() {
    String source = "pHD";
    String expected = AlphaNameSuffix.PHD.getTranslatedValue();
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testTranslation_jd() {
    String source = "jd";
    String expected = AlphaNameSuffix.JD.getTranslatedValue();
    String actual = NameSuffixTranslator.translate(source);
    Assert.assertEquals(expected, actual);
  }
}
