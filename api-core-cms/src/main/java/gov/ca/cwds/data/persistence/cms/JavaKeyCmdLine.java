package gov.ca.cwds.data.persistence.cms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.stream.Stream;

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
 * {@code java -cp bin gov.ca.cwds.rest.util.jni.JavaKeyCmdLine 0x5 "2017-06-30T04:13:51.720Z"}.
 * </pre>
 * 
 * </blockquote>
 * 
 * @author CWDS API Team
 */
public final class JavaKeyCmdLine {

  private static final Logger LOGGER = LoggerFactory.getLogger(JavaKeyCmdLine.class);

  private void massTest(String staffId, String fileNm) throws IOException {
    final Path pathIn = Paths.get(fileNm);

    try (Stream<String> lines = Files.lines(pathIn)) {
      lines.forEach(l -> generateKey(staffId, l));
    } finally {
      // Close stream.
    }
  }

  private void generateKey(String staffId, Date ts) throws IOException {
    LOGGER.info("Java generateKey: staff: {}, timestamp: {}", staffId, ts);
    final String key = CmsKeyIdGenerator.generate(staffId, ts);
    LOGGER.info("Generated key=" + key);
  }

  private void generateKey(String staffId, String strTs) {
    final Date ts = DomainChef.uncookISO8601Timestamp(strTs);
    final String key = CmsKeyIdGenerator.generate(staffId, ts);
    LOGGER.info("gen: staff: {}, timestamp: {}, key: {}", staffId, ts, key);
  }

  /**
   * Main method. See class javadoc for details.
   * 
   * @param args command line
   */
  public static void main(String[] args) {
    JavaKeyCmdLine run = new JavaKeyCmdLine();
    try {
      final OptionParser parser = new OptionParser("f:s:t:T::");
      final OptionSet options = parser.parse(args);

      final String staffId = options.has("s") ? (String) options.valueOf("s") : "0x5";
      final Date ts = options.has("t")
          ? DomainChef.uncookISO8601Timestamp((String) options.valueOf("t")) : new Date();

      run.generateKey(staffId, ts);
    } catch (Exception e) {
      LOGGER.error("OOPS!", e);
    }
  }

}
