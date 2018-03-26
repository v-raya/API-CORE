package gov.ca.cwds.test.support;

/**
 * @author CWDS TPT-2 Team
 */
public class PerryV2DevModeParams implements AuthParams {

  private String identityJson;

  public PerryV2DevModeParams(String identityJson) {
    this.identityJson = identityJson;
  }

  public String getIdentityJson() {
    return identityJson;
  }

  public void setIdentityJson(String identityJson) {
    this.identityJson = identityJson;
  }


}
