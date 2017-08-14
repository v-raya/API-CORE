package gov.ca.cwds.logging;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.MDC;

import gov.ca.cwds.logging.LoggingContext.LogParameter;

public class MDCLoggerContextTest {

  private static LoggingContext loggingContext;

  @BeforeClass
  public static void setup() throws Exception {
    loggingContext = new MDCLoggingContext();
  }

  @After
  public void after() throws Exception {
    MDC.clear();
  }

  @Test
  public void testBuildMDCSetsRemoteAddress() throws Exception {
    loggingContext.setLogParameter(LogParameter.REMOTE_ADDRESS, "remoteAddress");
    assertThat(MDC.get(LogParameter.REMOTE_ADDRESS.name()), is("remoteAddress"));
  }

  @Test
  public void testBuildMDCSetsUserId() throws Exception {
    loggingContext.setLogParameter(LogParameter.USER_ID, "userId");
    assertThat(MDC.get(LogParameter.USER_ID.name()), is("userId"));
  }

  @Test
  public void testBuildMDCSetsSessionId() throws Exception {
    loggingContext.setLogParameter(LogParameter.SESSION_ID, "sessionId");
    assertThat(MDC.get(LogParameter.SESSION_ID.name()), is("sessionId"));
  }

  @Test
  public void testBuildMDCSetsRequestId() throws Exception {
    loggingContext.setLogParameter(LogParameter.REQUEST_ID, "requestId");
    assertThat(MDC.get(LogParameter.REQUEST_ID.name()), is("requestId"));
  }

  @Test
  public void testTeardownMDCClearsRemoteAddress() throws Exception {
    loggingContext.setLogParameter(LogParameter.REMOTE_ADDRESS, "remoteAddress");
    loggingContext.close();;
    assertThat(MDC.get(LogParameter.REMOTE_ADDRESS.name()), is(nullValue()));
  }

  @Test
  public void testTeardownMDCClearsUserId() throws Exception {
    loggingContext.setLogParameter(LogParameter.USER_ID, "userId");
    loggingContext.close();;
    assertThat(MDC.get(LogParameter.USER_ID.name()), is(nullValue()));
  }

  @Test
  public void testTeardownMDCClearsSessionId() throws Exception {
    loggingContext.setLogParameter(LogParameter.SESSION_ID, "sessionId");
    loggingContext.close();;
    assertThat(MDC.get(LogParameter.SESSION_ID.name()), is(nullValue()));
  }

  @Test
  public void testTeardownMDCClearsRequestId() throws Exception {
    loggingContext.setLogParameter(LogParameter.REQUEST_ID, "requestId");
    loggingContext.close();;
    assertThat(MDC.get(LogParameter.REQUEST_ID.name()), is(nullValue()));
  }

  @Test
  public void uniqueIdStoresIdInMdc() throws Exception {
    String id = loggingContext.initialize();
    assertThat(MDC.get(LogParameter.UNIQUE_ID.name()), is(id));
  }
}
