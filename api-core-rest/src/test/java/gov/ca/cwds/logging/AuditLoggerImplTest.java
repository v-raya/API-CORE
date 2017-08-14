package gov.ca.cwds.logging;

import static org.mockito.Mockito.mock;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;

/**
 * @author CWDS API Team
 */
public class AuditLoggerImplTest {

  private static AuditLoggerImpl auditLoggerImpl;
  private static Logger logger;

  @BeforeClass
  public static void setup() throws Exception {
    logger = mock(Logger.class);
    auditLoggerImpl = new AuditLoggerImpl();
  }

  @Test
  public void audit_Args__String() throws Exception {
    AuditLoggerImpl target = new AuditLoggerImpl();
    String data = null;
    target.audit(data);
  }
}
