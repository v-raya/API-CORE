package gov.ca.cwds.test.support;

/**
 * @author CWDS TPT-2 Team
 */
public class JsonIdentityAuthParams implements AuthParams {

  private String identityJson;

  public JsonIdentityAuthParams(String identityJson) {
    this.identityJson = identityJson;
  }

  public String getIdentityJson() {
    return identityJson;
  }

  public void setIdentityJson(String identityJson) {
    this.identityJson = identityJson;
  }

}
