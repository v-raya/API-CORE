package gov.ca.cwds.logging;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.MDC;

/**
 * 
 * 
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

  @After
  public void after() throws Exception {
    MDC.clear();
  }

  @Test
  public void testBuildMDCSetsRemoteAddress() throws Exception {
    auditLoggerImpl.storeRemoteAddress("remoteAddress");

    assertThat(MDC.get(AuditLoggerImpl.REMOTE_ADDRESS), is("remoteAddress"));
  }

  @Test
  public void testBuildMDCSetsUserId() throws Exception {
    auditLoggerImpl.storeUserId("userid");

    assertThat(MDC.get(AuditLoggerImpl.USER_ID), is("userid"));
  }

  @Test
  public void testBuildMDCSetsSessionId() throws Exception {
    auditLoggerImpl.storeSessionId("sessionid");

    assertThat(MDC.get(AuditLoggerImpl.SESSION_ID), is("sessionid"));
  }

  @Test
  public void testBuildMDCSetsRequestId() throws Exception {
    auditLoggerImpl.storeRequestId("requestid");

    assertThat(MDC.get(AuditLoggerImpl.REQUEST_ID), is("requestid"));
  }

  @Test
  public void testTeardownMDCClearsRemoteAddress() throws Exception {
    auditLoggerImpl.storeRemoteAddress("remoteAddress");
    auditLoggerImpl.teardownMDC();
    assertThat(MDC.get(AuditLoggerImpl.REMOTE_ADDRESS), is(nullValue()));
  }

  @Test
  public void testTeardownMDCClearsUserId() throws Exception {
    auditLoggerImpl.storeUserId("userid");
    auditLoggerImpl.teardownMDC();
    assertThat(MDC.get(AuditLoggerImpl.USER_ID), is(nullValue()));
  }

  @Test
  public void testTeardownMDCClearsSessionId() throws Exception {
    auditLoggerImpl.storeSessionId("sessionid");
    auditLoggerImpl.teardownMDC();
    assertThat(MDC.get(AuditLoggerImpl.SESSION_ID), is(nullValue()));
  }

  @Test
  public void testTeardownMDCClearsRequestId() throws Exception {
    auditLoggerImpl.storeRequestId("requestid");
    auditLoggerImpl.teardownMDC();
    assertThat(MDC.get(AuditLoggerImpl.REQUEST_ID), is(nullValue()));
  }

  @Test
  public void uniqueIdStoresIdInMdc() throws Exception {
    String id = auditLoggerImpl.uniqueId();
    assertThat(MDC.get(AuditLoggerImpl.UNIQUE_ID), is(id));
  }
}
