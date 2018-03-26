package gov.ca.cwds.test.support;

/**
 * @author CWDS TPT-2 Team
 */
public class JWTTokenProviderConf {

  private String identity;

  public JWTTokenProviderConf(String identity) {
    this.identity = identity;
  }

  public String getIdentity() {
    return identity;
  }
}
