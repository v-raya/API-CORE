package gov.ca.cwds.data.persistence.cms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.domain.DomainChef;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * Command line tool to call the <strong>Java</strong> CWDS key generation class,
 * {@link CmsKeyIdGenerator}.
 * 
 * <h3>Command line arguments:</h3>
 * <h4>Compose/generate a key:</h4> <blockquote>
 * 
 * <pre>
 * {@code java -cp bin gov.ca.cwds.rest.util.jni.JavaKeyCmdLine -s 0x5 -t "2017-06-30T04:13:51.720Z"}.
 * </pre>
 * 
 * </blockquote>
 * 
 * <p>
 * See test file, /api-core-cms/src/test/resources/java_key_gen.txt.
 * </p>
 * 
 * @author CWDS API Team
 */
public final class JavaKeyCmdLine {

  static final Logger LOGGER = LoggerFactory.getLogger(JavaKeyCmdLine.class);

  private void massTest(String fileNm) throws IOException {
    final Path pathIn = Paths.get(fileNm);

    try (Stream<String> lines = Files.lines(pathIn)) {
      lines.map(RipCKey::new).forEach(RipCKey::validate);
    } finally {
      // Close stream.
    }
  }

  protected static String generateKey(String staffId) {
    return CmsKeyIdGenerator.getNextValue(staffId);
  }

  protected static String generateKey(String staffId, String strTs) {
    return CmsKeyIdGenerator.generate(staffId, DomainChef.uncookISO8601Timestamp(strTs));
  }

  protected static String generateKey(String staffId, Date date) {
    return CmsKeyIdGenerator.generate(staffId, date);
  }

  /**
   * Main method. See class javadoc for details.
   * 
   * @param args command line
   */
  public static void main(String[] args) {
    final JavaKeyCmdLine run = new JavaKeyCmdLine();
    try {
      final OptionParser parser = new OptionParser("f:s:t:T::i::");
      final OptionSet options = parser.parse(args);

      final String staffId = options.has("s") ? (String) options.valueOf("s") : "0x5";
      final String numKeysToMake = options.has("i") ? (String) options.valueOf("i") : "1";
      final Date ts =
          options.has("t") ? DomainChef.uncookISO8601Timestamp((String) options.valueOf("t"))
              : new Date();
      final String fileNm = options.has("f") ? (String) options.valueOf("f") : null;

      if (StringUtils.isNotBlank(fileNm)) {
        run.massTest(fileNm);
      } else if ("1".equals(numKeysToMake)) {
        final String key = JavaKeyCmdLine.generateKey(staffId, ts);
        LOGGER.info("gen: staff: {}, timestamp: {}, key: {}", staffId, ts, key);
      } else {
        final int iNumKeysToMake = Integer.parseInt(numKeysToMake);
        LOGGER.info("Generate multiple keys. Ignore timestamp param. total keys: {}",
            iNumKeysToMake);
        for (int i = 0; i < iNumKeysToMake; i++) {
          final String key = JavaKeyCmdLine.generateKey(staffId);
          LOGGER.info("gen: staff: {}, key: {}", staffId, key);
        }
      }
    } catch (Exception e) {
      LOGGER.error("OOPS!", e);
    }
  }

}
