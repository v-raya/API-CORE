package gov.ca.cwds.test.support;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Form;
import org.apache.commons.lang3.StringUtils;

/**
 * @author CWDS TPT-2 Team
 */
public class PerryV2DevModeTokenProvider extends BasePerryV2TokenProvider<JsonIdentityAuthParams> {

  private static final String FORM_PARAM_USERNAME = "username";

  public PerryV2DevModeTokenProvider(Client client, String perryUrl, String loginFormTargetUrl) {
    super(client, perryUrl, loginFormTargetUrl);
  }


  @Override
  protected Form prepareLoginForm(JsonIdentityAuthParams params) {
    final Form form = new Form();
    form.param(FORM_PARAM_USERNAME, params.getIdentityJson().replaceAll("\n", StringUtils.EMPTY));
    return form;
  }

}
