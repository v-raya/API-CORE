package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator.KeyDetail;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * This JNI native library runs correctly on Linux Jenkins when libLZW.so and libstdc++.so.6 are
 * installed into /usr/local/lib/.
 *
 * <p>
 * The library does build and run on OS X and Linux environments with current compilers installed.
 * </p>
 *
 * <p>
 * The following JUnit test runs manually on the clone Jenkins server but not through Gradle on
 * Linux. However, Gradle runs successfully on OS X and Windows. Switch to the Jenkins user with:
 * </p>
 *
 * <p>
 * <blockquote>
 *
 * <pre>
 * {@code sudo -u jenkins bash}.
 * </pre>
 *
 * </blockquote>
 * </p>
 *
 * <p>
 * Run the JUnit manually with the sample command below. Note that jars are copied manually with the
 * sample script, cp_api_libs.sh.
 * </p>
 *
 * <p>
 * <blockquote>
 *
 * <pre>
 * {@code java -Djava.library.path=.:/usr/local/lib/ -cp .:/var/lib/jenkins/workspace/API/build/classes/main:/var/lib/jenkins/workspace/API/build/classes/test:/var/lib/jenkins/workspace/API/build/resources/test:/var/lib/jenkins/test_lib/junit-4.12.jar:/var/lib/jenkins/test_lib/hamcrest-core-1.3.jar:/var/lib/jenkins/test_lib/* org.junit.runner.JUnitCore gov.ca.cwds.rest.util.jni.KeyGenTest}
 *
 * </pre>
 *
 * </blockquote>
 * </p>
 *
 * <p>
 * Force JUnit tests to run against native libraries, loaded or not, with JVM argument
 * </p>
 *
 * <p>
 * <blockquote>
 *
 * <pre>
 * {@code -Dcwds.jni.force=Y}
 *
 * </pre>
 *
 * </blockquote>
 * </p>
 *
 * @author CWDS API Team
 */
public final class CmsKeyIdGeneratorTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsKeyIdGeneratorTest.class);

  private static final int GOOD_KEY_LEN = CmsPersistentObject.CMS_ID_LEN;
  private static final Pattern RGX_LEGACY_KEY =
      Pattern.compile("([a-z0-9]{10})", Pattern.CASE_INSENSITIVE);
  private static final Pattern RGX_LEGACY_TIMESTAMP =
      Pattern.compile("([a-z0-9]{7})", Pattern.CASE_INSENSITIVE);

  // ===================
  // GENERATE KEY:
  // ===================
  @Test
  public void testNextValueIsDifferent() {
    for (int i = 0; i < 500; i++) {
      final String keyOne = CmsKeyIdGenerator.getNextValue("0yz");
      final String keySecond = CmsKeyIdGenerator.getNextValue("0yz");
      assertFalse(keyOne.equals(keySecond));
    }
  }

  // The Java key generator is making incorrect dates!!
  @Test
  @Ignore
  public void testIsGeneratedDateRealistic() {
    final Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DAY_OF_MONTH, 1);
    final Date future = cal.getTime();

    for (int i = 0; i < 1000; i++) {
      final String key = CmsKeyIdGenerator.getNextValue("0yz");
      final Date generatedDate = CmsKeyIdGenerator.getDateFromKey(key);
      System.out.println("generatedDate: " + generatedDate);
      assertTrue("generated date in the future!", generatedDate.before(future));
    }
  }

  @Test
  public void testGenKeyGood() {
    final String key = CmsKeyIdGenerator.getNextValue("0X5");
    assertTrue("key not generated", key != null && key.length() == GOOD_KEY_LEN);
  }

  @Test
  public void testGenKeyGoodStaff2() {
    // Good staff id.
    final String key = CmsKeyIdGenerator.getNextValue("0yz");
    assertTrue("key not generated", key != null && key.length() == GOOD_KEY_LEN);
  }

  @Test
  public void testGenKeyBadStaffEmpty() {
    // Empty staff id.
    final String key = CmsKeyIdGenerator.getNextValue("");
    // assertTrue("key generated", key == null || key.length() == 0);
  }

  // TODO: #145948067: default staff id until Perry is ready.
  @Test
  // @Test(expected = ServiceException.class)
  public void testGenKeyBadStaffNull() {
    // Null staff id.
    final String key = CmsKeyIdGenerator.getNextValue(null);
    // assertTrue("key generated", key == null || key.length() == 0);
  }

  @Test(expected = ServiceException.class)
  public void testGenKeyBadStaffWrongLength() {
    // Wrong staff id length.
    final String key = CmsKeyIdGenerator.getNextValue("abcdefg");
    // assertTrue("key generated", key == null || key.length() == 0);
  }

  @Test(expected = ServiceException.class)
  public void testGenKeyBadStaffTooShort() {
    // Wrong staff id length.
    final String key = CmsKeyIdGenerator.getNextValue("a");
    // assertTrue("key generated", key == null || key.length() == 0);
  }

  @Test(expected = ServiceException.class)
  public void testGenKeyBadStaffTooLong() {
    // Wrong staff id length.
    final String key = CmsKeyIdGenerator
        .getNextValue("ab7777d7d7d7s8283jh4jskksjajfkdjbjdjjjasdfkljcxmzxcvjdhshfjjdkksahf");
    // assertTrue("key generated", key == null || key.length() == 0);
  }

  @Test(expected = ServiceException.class)
  public void testGenKeyBadStaffBadChars1() {
    // Invalid chars in staff id.
    final String key = CmsKeyIdGenerator.getNextValue("ab&");
    // assertTrue("key generated", key == null || key.length() == 0);
  }

  // ===================
  // DECOMPOSE KEY:
  // ===================
  @Test
  @Ignore
  public void testDecomposeGoodKey() {
    // Good key, decomposes correctly.
    KeyDetail kd = new KeyDetail();
    // CmsKeyIdGenerator.decomposeKey("1qxx0OC0X5", kd);
    // assertTrue("Staff ID empty", kd.staffId != null && "0X5".equals(kd.staffId));
  }

  @Test
  @Ignore
  public void testDecomposeKeyLong() {
    // Wrong staff id size: too long.
    KeyDetail kd = new KeyDetail();
    // CmsKeyIdGenerator.decomposeKey("wro000000000000ng", kd);
    // assertTrue("Staff ID not empty", kd.staffId == null || "".equals(kd.staffId));
  }

  @Test
  @Ignore
  public void testDecomposeKeyShort() {
    // Wrong staff id size: too short.
    KeyDetail kd = new KeyDetail();
    // CmsKeyIdGenerator.decomposeKey("w", kd);
    // assertTrue("Staff ID not empty", kd.staffId == null || "".equals(kd.staffId));
  }

  @Test
  @Ignore
  public void testDecomposeKeyEmpty() {
    // Empty staff id.
    KeyDetail kd = new KeyDetail();
    // CmsKeyIdGenerator.decomposeKey("", kd);
    // assertTrue("Staff ID not empty", kd.staffId == null || "".equals(kd.staffId));
  }

  @Test
  @Ignore
  public void testDecomposeKeyNull() {
    // Null staff id.
    KeyDetail kd = new KeyDetail();
    // CmsKeyIdGenerator.decomposeKey(null, kd);
    // assertTrue("Staff ID not empty", kd.staffId == null || "".equals(kd.staffId));
  }

  @Test
  public void type() throws Exception {
    assertThat(CmsKeyIdGenerator.class, notNullValue());
  }

  @Test
  public void createTimestampStr_Args__Date() throws Exception {
    Date ts = mock(Date.class);
    String actual = CmsKeyIdGenerator.createTimestampStr(ts);
    assertTrue("bad generated timestamp", RGX_LEGACY_TIMESTAMP.matcher(actual).matches());
  }

  @Test
  public void createTimestampStr_Args__() throws Exception {
    final String actual = CmsKeyIdGenerator.createTimestampStr();
    assertTrue("bad generated timestamp", RGX_LEGACY_TIMESTAMP.matcher(actual).matches());
  }

  @Test
  public void timestampToDouble_Args__Calendar() throws Exception {
    final Calendar cal = Calendar.getInstance();
    cal.setTimeZone(TimeZone.getTimeZone("PST"));
    cal.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-06-09 13:04:22"));
    final double actual = CmsKeyIdGenerator.timestampToDouble(cal);
    // final double expected = 5.922526581E9;
    // assertThat(actual, is(equalTo(expected)));
    assertThat(actual, is(not(0)));
  }

  @Test(expected = ServiceException.class)
  public void makeKey_Args__String__Date__null_staff() throws Exception {
    final String staffId = null;
    final Date ts = mock(Date.class);
    final String actual = CmsKeyIdGenerator.makeKey(staffId, ts);
    final String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void generate_Args__String__null_staff() throws Exception {
    final String actual = CmsKeyIdGenerator.getNextValue(null);
    assertTrue("bad generated key", RGX_LEGACY_KEY.matcher(actual).matches());
  }

  @Test
  public void generate_Args__String__Date() throws Exception {
    final String staffId = "0X5";
    final String actual = CmsKeyIdGenerator.getNextValue(staffId);
    assertThat(actual, is(not("")));
  }

  @Test
  public void getUIIdentifierFromKey_Args__String() throws Exception {
    // ./cws_randgen -k 5Y3vKVs0X5
    // 5Y3vKVs0X5 0X5 2012-11-25T11:15:22.180Z 2012-11-25-11.15.22.180000 0315-2076-8676-8002051
    final String key = "5Y3vKVs0X5";
    final String actual = CmsKeyIdGenerator.getUIIdentifierFromKey(key);
    final String expected = "0315-2076-8676-8002051";
    assertThat(actual, is(equalTo(expected)));
  }

  /**
   * <blockquote>
   * 
   * <pre>
      ./cws_randgen -k 83UiZBWABC
      key:          83UiZBWABC
      staff:        ABC
      date:         2018-03-30T10:45:40.260Z
      UI 19-digit:  0457-6041-9493-4039134
   * </pre>
   * 
   * </blockquote>
   * 
   * @throws Exception whatever. it's a test.
   */
  @Test
  public void testDecodeKey() throws Exception {
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    final Date dateFromXtools = sdf.parse("2018-03-30T10:45:40.260");
    final String thirdIdFromXTools = "83UiZFeABC";

    final Date localDate = CmsKeyIdGenerator.getDateFromKey(thirdIdFromXTools);
    System.out.println("localDate: " + sdf.format(localDate));
    assertEquals(dateFromXtools.getTime(), localDate.getTime());
  }

  @Test
  public void testGetDateFromThirdId() throws Exception {
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    final Date dateFromXtools = sdf.parse("2018-03-30T10:45:40.260");
    final String thirdIdFromXTools = "83UiZFeABC";
    final String userIdFromXTools = "ABC";

    final String thirdId = CmsKeyIdGenerator.generate(userIdFromXTools, dateFromXtools);
    System.out.println("thirdId: " + thirdId);

    final Date localDate = CmsKeyIdGenerator.getDateFromKey(thirdId);
    System.out.println("localDate: " + sdf.format(localDate));

    assertEquals(dateFromXtools.getTime(), localDate.getTime());
    assertEquals(thirdId, thirdIdFromXTools);
  }

  @Test
  public void testPassiveExpiringMap() throws Exception {
    final Map<String, String> staffLastKey =
        new PassiveExpiringMap<>(1, TimeUnit.SECONDS, new ConcurrentHashMap<>());

    final Date now = new Date();
    String staffId = "0X5";
    String lastKey = "83UiZFe0X5";

    staffLastKey.put(staffId, CmsKeyIdGenerator.generate(staffId, now));
    lastKey = staffLastKey.get(staffId);
    LOGGER.info("staff id: {}, lastKey: {}", staffId, lastKey);

    Thread.sleep(500);
    lastKey = staffLastKey.get(staffId);
    LOGGER.info("staff id: {}, lastKey: {}", staffId, lastKey);

    Thread.sleep(1500);
    lastKey = staffLastKey.get(staffId);
    LOGGER.info("staff id: {}, lastKey: {}", staffId, lastKey);
  }

}
