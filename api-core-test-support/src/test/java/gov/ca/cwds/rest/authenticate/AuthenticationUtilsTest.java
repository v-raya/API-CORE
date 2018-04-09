package gov.ca.cwds.rest.authenticate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class AuthenticationUtilsTest {

  private AuthenticationUtils authenticationUtils;

  @Before
  public void init() {
    authenticationUtils = new AuthenticationUtils();
  }

  @Test
  @Ignore
  public void getToken() throws Exception {
    String token = authenticationUtils.getToken(UserGroup.COUNTY_SEALED);
    assertNotNull(token);
    token = authenticationUtils.getToken(UserGroup.COUNTY_SENSITIVE);
    assertNotNull(token);
    token = authenticationUtils.getToken(UserGroup.SOCIAL_WORKER);
    assertNotNull(token);
    token = authenticationUtils.getToken(UserGroup.STATE_SEALED);
    assertNotNull(token);
    token = authenticationUtils.getToken(UserGroup.STATE_SENSITIVE);
    assertNotNull(token);
  }
}