package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import gov.ca.cwds.rest.api.domain.DomainChef;

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

  /**
   * Main method. See class javadoc for details.
   * 
   * @param args command line
   */
  public static void main(String[] args) {
    final String staffId = args[0];
    final Date ts = DomainChef.uncookISO8601Timestamp(args[1]);

    System.out.println("Java: Call JNI generateKey ... ");
    final String key = CmsKeyIdGenerator.generate(staffId, ts);
    System.out.println("Generated key=" + key);
  }

}
